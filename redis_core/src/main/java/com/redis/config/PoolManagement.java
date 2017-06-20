package com.redis.config;

import com.redis.common.exception.ExceptionInfo;
import com.redis.common.exception.NoticeInfo;
import com.redis.common.exception.ReadConfigException;
import com.redis.common.exception.RedisConnectionException;
import com.redis.utils.MythReflect;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Created by https://github.com/kuangcp on 17-6-9  上午10:30
 * 管理所有的连接池，创建，删除，切换等， 完全操作RedisPools
 * 就应该写成静态的方法的，这样就直接调用了，获取连接池也要测试可用性，再加入map中
 *
 * 因为其他工具类使用的是直接获取当前id然后得到池，，，一般会先使用id得到Pool放在内存中，然后其他工具类就可以用来
 * 测试类 自己加上一个方法
 */
@Component
@Setter
@Getter
public class PoolManagement {
    // 所有的连接池,不知道为什么，明明集合里有，却还是要新建，新建出来的还是同一个内存地址？
    private  ConcurrentMap<String,RedisPools> poolMap = new ConcurrentHashMap<>();
    private  Logger logger= LoggerFactory.getLogger(PoolManagement.class);
    private  String propertyFile = Configs.propertyFile;
    private  MythProperties configFile = null;
    private  String currentPoolId;
    // 获取指定id的连接池，断言id是存在的 TODO 这个得到的Bean会不会影响到依赖于他的bean

    // 为了测试类方便，
    public  void initPool(String poolId){
        currentPoolId = poolId;
    }
    // 为了方便操作方引用连接池,有异常就返回空
    public  RedisPools getRedisPool(){
        if(currentPoolId!=null) {
            System.out.println("直接返回当前连接池");
            try {
                return getRedisPool(currentPoolId);
            } catch (Exception e) {
                e.printStackTrace();
                logger.error(ExceptionInfo.GET_POOL_BY_ID_FAILED,e);
                return null;
            }
        }else{
            logger.error(ExceptionInfo.NOT_EXIST_CURRENT_POOL);
            return null;
        }
    }
    /**
     *
     * @param poolId 根据指定id获取一个连接池
     * @return RedisPools对象
     * @throws Exception 加载异常
     */
    public  RedisPools getRedisPool(String poolId)throws Exception{
        currentPoolId = poolId;
        RedisPools pool = null;
//        System.out.println("获取的id"+poolId);
//        for (String name: poolMap.keySet()) {
//            System.out.println(name);
//        }
        // 如果内存中有就直接返回
        if(poolMap.containsKey(poolId)){
            logger.info(NoticeInfo.ALREADY_EXIST_POOL+poolId);
            return poolMap.get(poolId);
        }

        try {
            configFile = PropertyFile.getProperties(propertyFile);
        } catch (IOException e) {
            e.printStackTrace();
           throw new ReadConfigException(ExceptionInfo.OPEN_CONFIG_FAILED+propertyFile,e,PoolManagement.class);
        }

        String pre = poolId+ Configs.SEPARATE;//前缀
        RedisPoolProperty poolProperty;
        // 如果没有这个id的存在,就新建一个
        if(configFile.getString(pre+ Configs.NAME)==null){
            throw new RedisConnectionException(ExceptionInfo.POOL_NOT_EXIST_CONFIG,PoolManagement.class);
            //@TODO 处理这种连接不存在的情况
            // 一般，连接的显示是从配置文件中加载的，是不会出现面板上有连接，而配置文件中没有的情况 除非是在客户端，删除了连接，没有刷新客户端缓存而导致
        }else {
            pool = new RedisPools();//新建连接池对象
            poolProperty = RedisPoolProperty.initByIdFromConfig(poolId);
            pool.setProperty(poolProperty);
        }
        pool.initialPool();
        logger.info("实例化连接池："+poolId+":"+pool+"-->"+poolProperty.toString());
        if(pool.available()) {
            poolMap.put(poolId, pool);
            return pool;
        }else{
            throw new RedisConnectionException(ExceptionInfo.POOL_NOT_AVAILABLE,PoolManagement.class);
        }
    }

    // 创建RedisPool并连接使用，是否可以不用
    public  RedisPools createRedisPoolAndConnection(RedisPoolProperty property)throws Exception{
        return getRedisPool(createRedisPool(property));
    }

    /**
     * 创建RedisPool配置文件 返回id
     * @param property 配置对象
     * @return id
     * @throws Exception 抛出可能创建失败的异常
     */
    public  String  createRedisPool(RedisPoolProperty property) throws Exception{
        int maxId = PropertyFile.getMaxId();
        maxId++;
        property.setPoolId(maxId+"");
        Map<String,?> map = MythReflect.getFieldsValue(property);
        // 将所有属性都转换成String类型，特别注意bool类型的没有toString方法，就只能靠拼接
        for (String key:map.keySet()) {
            PropertyFile.save( maxId+Configs.SEPARATE+key,map.get(key)+"");
        }
        PropertyFile.delete("maxId");
        PropertyFile.save("maxId",maxId+"");
        logger.info(NoticeInfo.CRETE_POOL+maxId);
        return maxId+"";
    }

    // 切换到另一个连接池
    public  boolean switchPool(String PoolId){
        try {
            if(PropertyFile.getAllPoolConfig().containsKey(PoolId)) {
                currentPoolId = PoolId;
                return true;
            }else{
                return false;
            }
        } catch (ReadConfigException e) {
            e.printStackTrace();
            return false;
        }
    }
    /**
     * 删除配置文件 成功返回id,失败(不存在或异常)返回 null
     * @param poolId
     * @return id 或 null
     */
    public  String deleteRedisPool(String poolId)throws IOException{
        try {
            configFile = PropertyFile.getProperties(propertyFile);
            String exist = configFile.getString(poolId + Configs.SEPARATE + Configs.POOL_ID);
            if (exist == null) {
                logger.info(ExceptionInfo.DELETE_POOL_NOT_EXIST+poolId);
                return null;
            }
            for (String key : MythReflect.getFieldByClass(RedisPoolProperty.class)) {
                PropertyFile.delete( poolId + Configs.SEPARATE + key);
            }
        }catch (IOException e){
            logger.error(ExceptionInfo.OPEN_CONFIG_FAILED,e);
            return null;
        }
        return poolId;
    }
    /**
     * 删除所有配置，文件中
     * @return 删除结果
     */
    public  boolean clearAllPools(){
        try {
            int maxId = PropertyFile.getMaxId();
            for(int i=Configs.START_ID;i<=maxId;i++){
                String result = deleteRedisPool(i+"");
                if(result!=null){
                    logger.info(NoticeInfo.DELETE_POOL_SUCCESS+i);
                }else{
                    logger.info(NoticeInfo.DELETE_POOL_FAILED+i);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }
    // map集合中删除,断开连接的时候调用
    // TODO 销毁指定id的连接池,似乎没有起作用，有就销毁，返回结果，没有就返回false
    public  boolean destroyRedisPool(String poolId) throws Exception {
        if(poolMap.containsKey(poolId)) {
            boolean flag = poolMap.get(poolId).destroyPool();
            poolMap.remove(poolId);
            return flag;
        }else {
            return false;
        }
    }
    public ConcurrentMap<String, RedisPools> getPoolMap() {
        return poolMap;
    }


}
