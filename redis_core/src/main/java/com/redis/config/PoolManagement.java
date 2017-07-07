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
import redis.clients.jedis.Jedis;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;
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
    private  String propertyFile = Configs.PROPERTY_FILE;
    private  MythProperties configFile = null;
    private  String currentPoolId;
    // 获取指定id的连接池，断言id是存在的 TODO 这个得到的Bean会不会影响到依赖于他的bean

    /**
     * 获取连接池,得到currentId指定的连接池，可以通过更改currentId切换连接池
     * @return 连接池实例，异常发生就返回空
     */
    public  RedisPools getRedisPool(){
        // 如果当前id是空的，说明初始化有问题
        if(currentPoolId==null){
            logger.error(ExceptionInfo.NOT_EXIST_CURRENT_POOL);
            return null;
        }
//        logger.info(NoticeInfo.CONFIG_CONTAIN_POOL +currentPoolId);
        try {
            return getRedisPool(currentPoolId);
        } catch (Exception e) {
//            e.printStackTrace();
            logger.error(e.getMessage());
            logger.debug(NoticeInfo.ERROR_INFO,e);
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
        RedisPools pool;
//        System.out.println("获取的id"+poolId);
//        for (String name: poolMap.keySet()) {
//            System.out.println(name);
//        }
        // 如果内存中有就直接返回
        if(poolMap.containsKey(poolId)){
            logger.info(NoticeInfo.MAP_CONTAIN_POOL +poolId+" 内存中连接池数量: "+poolMap.size());
            return poolMap.get(poolId);
        }
        configFile = PropertyFile.getProperties(propertyFile);
        if(Objects.equals(configFile.getString(poolId+ Configs.SEPARATE + Configs.NAME), RunStatus.PROPERTY_IS_NULL)){
            throw new RedisConnectionException(ExceptionInfo.POOL_NOT_EXIST_CONFIG,PoolManagement.class);
            //@TODO 处理这种连接不存在的情况
            // 一般，连接的显示是从配置文件中加载的，是不会出现面板上有连接，而配置文件中没有的情况 除非是在客户端，删除了连接，没有刷新客户端缓存而导致
        }
        // 如果内存中没有这个id的存在,就新建一个连接对象
        pool = new RedisPools();
        RedisPoolProperty poolProperty = RedisPoolProperty.initByIdFromConfig(poolId);
        pool.initialPool(poolProperty);
        logger.info("实例化连接池 "+poolId+" - "+pool.toString());
        logger.debug("实例化的配置："+poolProperty.toString());
        if(pool.available()) {
            poolMap.put(poolId, pool);
            return pool;
        }else{
            // 如果不可用就销毁掉
            pool.destroyPool();
            throw new RedisConnectionException(ExceptionInfo.POOL_NOT_AVAILABLE,PoolManagement.class);
        }
    }

    /**
     * 创建RedisPool并连接使用
     * @param property 配置的属性
     * @return 连接池实例
     * @throws Exception 配置文件出错连不上
     */
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
        PropertyFile.delete(Configs.MAX_POOL_ID);
        PropertyFile.save(Configs.MAX_POOL_ID,maxId+"");
        logger.info(NoticeInfo.CRETE_POOL+maxId);
        return maxId+"";
    }

    /**
     * 测试对应Property的连接是否能连接上
     * @param property 连接池属性
     * @return 测试成功与否
     */
    public boolean checkConnection(RedisPoolProperty property){
        if(!validate(property)){
            return false;
        }
        RedisPools pools = new RedisPools();
        pools.setProperty(property);
        Jedis jedis = pools.getJedis();
        jedis.set("testConnection","90");
        if("90".equals(jedis.get("testConnection"))){
            jedis.del("testConnection");
            pools.destroyPool();
            return true;
        }else{
            return false;
        }
    }
    private boolean validate(RedisPoolProperty property){
        boolean flag;
        flag = property.getHost().matches("((?:(?:25[0-5]|2[0-4]\\d|((1\\d{2})|([1-9]?\\d)))\\.){3}(?:25[0-5]|2[0-4]\\d|((1\\d{2})|([1-9]?\\d))))");
        if(property.getPort()>65535 || property.getPort()<0) flag = false;
        return flag;
    }

    /**
     * 切换数据连接池
     * @param PoolId 目标id
     * @return true 切换成功,id在配置文件中不存在就返回false
     */
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
     * @param poolId id
     * @return id 或 null
     */
    public  String deleteRedisPool(String poolId)throws IOException{
        try {
            configFile = PropertyFile.getProperties(propertyFile);
            String exist = configFile.getString(poolId + Configs.SEPARATE + Configs.POOL_ID);
            if (exist == null) {
                logger.error(ExceptionInfo.DELETE_POOL_NOT_EXIST+poolId);
                return null;
            }
            for (String key : MythReflect.getFieldByClass(RedisPoolProperty.class)) {
                PropertyFile.delete( poolId + Configs.SEPARATE + key);
            }
        }catch (Exception e){
            logger.error(ExceptionInfo.OPEN_CONFIG_FAILED,e);
            return null;
        }
        logger.info(NoticeInfo.DELETE_POOL_SUCCESS+poolId,PoolManagement.class);
        return poolId;
    }
    /**
     * 删除所有配置，文件中
     * @return 删除结果
     * @throws Exception 配置文件不可用的异常
     */
    public  boolean clearAllPools() throws Exception{
        int maxId = PropertyFile.getMaxId();
        for(int i=Configs.START_ID;i<=maxId;i++){
            String result = deleteRedisPool(i+"");
            if(result!=null){
                logger.info(NoticeInfo.DELETE_POOL_SUCCESS+i);
            }else{
                logger.info(NoticeInfo.DELETE_POOL_FAILED+i);
            }
        }
        return true;
    }
    // map集合中删除,断开连接的时候调用
    // TODO 销毁指定id的连接池,似乎没有起作用，没看到内存的下降
    /**
     * 销毁 配置文件中存在，并加载到了内存Map中的连接池实例
     * @param poolId 连接池配置文件的id
     * @return 销毁结果 true false
     */
    public  boolean destroyRedisPool(String poolId){
        if(poolMap.containsKey(poolId)) {
            boolean flag = poolMap.get(poolId).destroyPool();
            poolMap.remove(poolId);
            return flag;
        }else {
            return false;
        }
    }
    // 销毁一个创建的对象，为了测试连接
    public boolean destroyRedisPool(RedisPools redisPools){
        return redisPools.destroyPool();
    }
    public ConcurrentMap<String, RedisPools> getPoolMap() {
        return poolMap;
    }


}
