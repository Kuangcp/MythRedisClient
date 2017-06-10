package com.redis.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Created by https://github.com/kuangcp on 17-6-9  上午10:30
 * 管理所有的连接池，创建，删除，切换等
 */
@Component
public class PoolManagement {
    // 所有的连接池,不知道为什么，明明集合里有，却还是要新建，新建出来的还是同一个内存地址？
    public ConcurrentMap<String,RedisPools> pools = new ConcurrentHashMap<>();
    private static Logger logger= LoggerFactory.getLogger(PoolManagement.class);
    String propertyFile = Configs.propertyFile;
    MythProperties configFile = null;
    private String currentPoolId;
    // 获取指定id的连接池，断言id是存在的 TODO 这个得到的Bean会不会影响到依赖于他的bean

    // 为了方便操作方引用连接池
    public RedisPools getRedisPool() throws Exception {
        if(currentPoolId!=null) {
            System.out.println("直接返回当前连接池");
            return getRedisPool(currentPoolId);
        }else{
            String error = "当前没有活跃的连接池，请指定id进行连接";
            logger.error(error);
            throw  new Exception(error);
        }
    }
//    @Bean
    public RedisPools getRedisPool(String poolId)throws Exception{
        currentPoolId = poolId;
        RedisPools pool = null;
        System.out.println("获取的id"+poolId);
        for (String name: pools.keySet()) {
            System.out.println(name);
        }
        // 如果内存中有就直接返回
        if(pools.containsKey(poolId)){
            logger.info("内存中已经存在了，直接返回"+poolId);
            return pools.get(poolId);
        }

        try {
            configFile = PropertyFile.getProperties(propertyFile);
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("打开"+propertyFile+"配置文件失败",e);
           throw e;
        }

        String pre = poolId+ Configs.SEPARATE;//前缀
        RedisPoolProperty poolProperty;
        // 如果没有这个id的存在,就新建一个
        if(configFile.getString(pre+ Configs.NAME)==null){
//                pool = createRedisPool(propertyFile);
            String error = "连接配置不存在，请刷新重试，或者新建连接";
            logger.error(error);
            throw new Exception(error);
            //@TODO 处理这种连接不存在的情况
            // 一般，连接的显示是从配置文件中加载的，是不会出现面板上有连接，而配置文件中没有的情况
            // 除非是在客户端，删除了连接，没有刷新客户端缓存而导致
        }else {
            pool = new RedisPools();//新建连接池对象
            poolProperty = new RedisPoolProperty();//新建连接池所有配置属性对象
            poolProperty.initByIdFromFile(poolId);
            pool.setProperty(poolProperty);
//            System.out.println(poolProperty);

        }
        pool.initialPool();
        logger.info("添加连接池配置"+poolId+":"+pool+"-->"+poolProperty.toString());
        pools.put(poolId,pool);
        return pool;
    }

    // 创建RedisPool并连接使用，是否可以不用
    public RedisPools createRedisPoolAndConnection(RedisPoolProperty property)throws Exception{
        String id = createRedisPool(property);
        return getRedisPool(id);
    }

    /**
     * 创建RedisPool配置文件 返回id
     * @param property 配置对象
     * @return id
     * @throws Exception
     */
    public String  createRedisPool(RedisPoolProperty property) throws Exception{
        int maxId = PropertyFile.getMaxId(propertyFile);
        maxId++;
        property.setPoolId(maxId+"");
        Map<String,?> map = property.getPropertyValueMap();
        for (String key:map.keySet()) {
            PropertyFile.save(propertyFile, maxId+Configs.SEPARATE+key,map.get(key)+"");
        }
        PropertyFile.delete(propertyFile,"maxId");
        PropertyFile.save(propertyFile,"maxId",maxId+"");
        logger.info("创建新的连接池配置:"+maxId);
        return maxId+"";
    }

    /**
     * 删除配置文件 成功返回id,失败(不存在或异常)返回 null
     * @param poolId
     * @return id 或 null
     */
    public String deleteRedisPool(String poolId)throws Exception{
        try {
            configFile = PropertyFile.getProperties(propertyFile);
            String exist = configFile.getString(poolId + Configs.SEPARATE + Configs.POOL_ID);
            if (exist == null) {
                return null;
            }
            for (String key : RedisPoolProperty.getPropertyList()) {
                PropertyFile.delete(propertyFile, poolId + Configs.SEPARATE + key);
            }
        }catch (Exception e){
            logger.error("删除连接池配置错误",e);
            return null;
        }
        return poolId;
    }
    /**
     * 删除所有配置
     * @return
     */
    public boolean clearAllPools(){
        try {
            int maxId = PropertyFile.getMaxId(propertyFile);
            for(int i=Configs.START_ID;i<=maxId;i++){
                String result = deleteRedisPool(i+"");
                if(result!=null){
                    logger.info("删除连接"+i+"成功");
                }else{
                    logger.info("删除配置"+i+"失败");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    // TODO 销毁连接池,似乎没有起作用
    public boolean destroyRedisPool(String poolId) throws Exception {
        if(pools.containsKey(poolId)) {
            boolean flag = pools.get(poolId).destroyPool();
            pools.remove(poolId);
            return flag;
        }else {
            return false;
        }
    }


}
