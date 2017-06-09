package com.redis.config;

import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Created by https://github.com/kuangcp on 17-6-9  上午9:56
 * 一个连接redis的连接池实例
 */
@Getter
@Setter
public class RedisPools {

    private static Logger logger = LoggerFactory.getLogger(RedisPools.class);
    private static JedisPool jedisPool = null;

//     * redis过期时间,以秒为单位
//    public final static int EXPIRE_HOUR = 60*60;          //一小时
//    public final static int EXPIRE_DAY = 60*60*24;        //一天
//    public final static int EXPIRE_MONTH = 60*60*24*30;   //一个月

    RedisPoolProperty property=null;
//    private int maxActive=400;
//    private int maxIdle=300;
//    private int maxWaitMills=1000000;
//    private boolean testOnBorrow=true;
//    private int port=6379;
//    private int timeout=60;
//    private String host="127.0.0.1";
//    private String name;
//    private String poolId;


    /**
     * 初始化Redis连接池
     */
    public void initialPool(){
        try {
            JedisPoolConfig config = new JedisPoolConfig();
            config.setMaxTotal(property.getMaxActive());
            config.setMaxIdle(property.getMaxIdle());
            config.setMaxWaitMillis(property.getMaxWaitMills());
            config.setTestOnBorrow(property.isTestOnBorrow());
            jedisPool = new JedisPool(config, property.getHost(), property.getPort(), property.getTimeout());
        } catch (Exception e) {
            logger.error("create JedisPool error : "+e);
        }
    }

    /**
     * 在多线程环境同步初始化
     * @TODO 关于这个加锁的问题要考虑
     */
    public synchronized void poolInit() {
        if (jedisPool == null) {
            initialPool();
        }
    }


    /**
     * 同步获取Jedis实例
     * @return Jedis
     * @TODO finally块的代码有待确认
     */
    public Jedis getJedis() throws Exception {
        if (jedisPool == null) {
            poolInit();
        }
        Jedis jedis = null;
        try {
            if (jedisPool != null) {
                jedis = jedisPool.getResource();
            }
        } catch (Exception e) {
            logger.error("Get jedis error : "+e);
            throw e;
        }
        finally{
            returnResource(jedis);
        }
        // 虽然是释放连接的方法，但是没有立即释放，好像用完才释放的

        return jedis;
    }


    /**
     * 释放jedis资源
     * @param jedis
     */
    public void returnResource(Jedis jedis) {
//        if (jedis != null && jedisPool !=null) {
//            jedisPool.returnResource(jedis);
//
//        }
        jedis.close();
    }


//    /**
//     * 设置 String
//     * @param key
//     * @param value
//     */
//    public void setString(String key ,String value){
//        try {
//            value = value==null ? "" : value;
//            getJedis().set(key,value);
//        } catch (Exception e) {
//            logger.error("Set key error : "+e);
//        }
//    }
//
//    /**
//     * 设置 过期时间
//     * @param key
//     * @param seconds 以秒为单位
//     * @param value
//     */
//    public void setString(String key ,int seconds,String value){
//        try {
//            value = value==null ? "" : value;
//            getJedis().setex(key, seconds, value);
//        } catch (Exception e) {
//            logger.error("Set keyex error : "+e);
//        }
//    }

//    /**
//     * 获取String值
//     * @param key
//     * @return value
//     */
//    public String getString(String key) throws Exception{
//        if(getJedis() == null || !getJedis().exists(key)){
//            return null;
//        }
//        return getJedis().get(key);
//    }

}
