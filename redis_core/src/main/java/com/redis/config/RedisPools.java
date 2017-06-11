package com.redis.config;

import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by https://github.com/kuangcp on 17-6-9  上午9:56
 * 一个连接redis的连接池实例
 */
@Getter
@Setter
public class RedisPools{

    private static Logger logger = LoggerFactory.getLogger(RedisPools.class);
    // 是否加static,静态就只有一个了。会被覆盖
    private JedisPool jedisPool = null;
    RedisPoolProperty property=null;
//     * redis过期时间,以秒为单位
//    public final static int EXPIRE_HOUR = 60*60;          //一小时
//    public final static int EXPIRE_DAY = 60*60*24;        //一天
//    public final static int EXPIRE_MONTH = 60*60*24*30;   //一个月



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
//            System.out.println("密码："+property.getPassword()+property.getPassword().length());
            if(property.getPassword() != null && property.getPassword().length() > 0){
                jedisPool = new JedisPool(config, property.getHost(), property.getPort(), property.getTimeout(),property.getPassword());
            }else{
                jedisPool = new JedisPool(config, property.getHost(), property.getPort(), property.getTimeout());
            }
        } catch (Exception e) {
            logger.error("create JedisPool error : "+e);
        }
    }

    /**
     * 在多线程环境同步初始化
     * @TODO 关于这个加锁的问题要考虑，目前还没有合适的场景
     */
    public synchronized void poolInit() {
        if (jedisPool == null) {
            initialPool();
        }
    }

    /**
     * 同步获取Jedis实例
     * @return Jedis
     * jedis.close直接放在finally里，用完了自动关闭
     */
    public Jedis getJedis(){
        if (jedisPool == null) {
            logger.info("连接池为空重新建立");
            poolInit();
        }
        Jedis jedis = null;
        try{
            if (jedisPool != null) {
                jedis = jedisPool.getResource();
                logger.info("使用连接池"+jedisPool+"得到连接"+jedis);
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.error("Could Get jedis error : "+e);
        }finally{
            jedis.close();
        }
        // 上面的方法是设置redis将会关闭超时超过30秒的空闲连接。而不是设置读取数据的超时时间。
        jedis.configSet("timeout", "30");
//        System.out.println("密码是"+property.getPassword());
        return jedis;
    }
    public boolean destroyPool(){
        System.out.println("销毁连接池："+jedisPool);
        jedisPool.destroy();
        return true;
    }
    // 获取连接池状态信息
    public Map<String,Integer> getStatus(){
        Map<String,Integer> status = new HashMap<>();
        status.put("NumActive",jedisPool.getNumActive());//活跃的连接数
        status.put("NumIdle",jedisPool.getNumIdle());//闲置的连接数
        status.put("NumWaiters",jedisPool.getNumWaiters());//等待的连接数
        return status;
    }
    /**
     * 设置 过期时间
     * @param key
     * @param seconds 以秒为单位
     * @param value
     */
    public void setString(String key ,int seconds,String value){
        try {
            value = value==null ? "" : value;
            getJedis().setex(key, seconds, value);
        } catch (Exception e) {
            logger.error("Set keyex error : "+e);
        }
    }
}