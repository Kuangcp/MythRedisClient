package com.redis.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Created by https://github.com/kuangcp on 17-6-9  上午10:30
 * 管理所有的连接池，创建，删除，切换等
 */
@Component
public class PoolManagement {
    // 所有的连接池
    ConcurrentMap pools = new ConcurrentHashMap<String,RedisPools>();
    private static Logger logger= LoggerFactory.getLogger(PoolManagement.class);

    // 获取指定id的连接池，断言id是存在的 TODO 这个得到的Bean会不会影响到依赖于他的bean
    @Bean
    public RedisPools getRedisPool(String poolId)throws Exception{
        String propertyFile = System.getProperty("user.home") + File.separatorChar +".MythRedisClient.properties";
        MythProperties configFile = null;
        try {
            configFile = PropertyFile.getProperties(propertyFile);
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("打开"+propertyFile+"配置文件失败",e);
           throw e;
        }
        RedisPools pool = null;

        String pre = poolId+ConfigFile.SEPARATE;//前缀
        // 如果没有这个id的存在,就新建一个
        if(configFile.getString(pre+ConfigFile.NAME)==null){
//                pool = createRedisPool(propertyFile);
            logger.error("没有这个连接的配置，请新增");
            throw new Exception("连接配置不存在，请刷新重试，或者新建连接");
            //@TODO 处理这种连接不存在的情况
            // 一般，连接的显示是从配置文件中加载的，是不会出现面板上有连接，而配置文件中没有的情况
            // 除非是在客户端，删除了连接，没有刷新客户端缓存而导致
        }else {
            pool = new RedisPools();//新建连接池对象
            RedisPoolProperty poolProperty = new RedisPoolProperty();//新建连接池所有配置属性对象
            poolProperty.setMaxActive(configFile.getInt(pre + ConfigFile.MAX_ACTIVE));
            poolProperty.setMaxIdle(configFile.getInt(pre + ConfigFile.MAX_IDLE));
            poolProperty.setMaxWaitMills(configFile.getInt(pre + ConfigFile.MAX_WAIT_MILLIS));
            poolProperty.setTimeout(configFile.getInt(pre + ConfigFile.TIMEOUT));
            poolProperty.setTestOnBorrow(configFile.getBoolean(pre + ConfigFile.TEST_ON_BORROW));
            poolProperty.setPoolId(poolId);
            poolProperty.setName(configFile.getString(pre + ConfigFile.NAME));
            poolProperty.setHost(configFile + ConfigFile.HOST);
            poolProperty.setPort(configFile.getInt(pre + ConfigFile.PORT));
            pool.setProperty(poolProperty);
        }

        pool.initialPool();
        pools.put(poolId,pool);
        return pool;
    }

    // 创建RedisPool并连接使用，是否可以不用
    public RedisPools createRedisPoolAndConnection(String propertyFile,RedisPoolProperty property)throws Exception{
        String id = saveRedisPool(propertyFile,property);
        return getRedisPool(id);
    }
    // 创建RedisPool配置文件 返回id
    public String  saveRedisPool(String propertyFile, RedisPoolProperty property){
        // TODO 完成创建
        return null;
    }
    //删除配置文件 返回id
    public String deleteRedisPool(String propertyFile, RedisPoolProperty property,String poolId){
        // TODO 完成删除
        return null;
    }
    // TODO 销毁连接池
    public boolean destroyRedisPool(RedisPools pool){
        return true;
    }
}
