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
            // 连接空闲的最小时间, 达到此值后空闲连接将会被移除. 负值(-1)表示不移除
//            config.setMinEvictableIdleTimeMillis(60000);
            // "空闲链接"检测线程, 检测的周期, 毫秒数. 如果为负值, 表示不运行“检测线程”. 默认为-1
//            config.setTimeBetweenEvictionRunsMillis(30000);
            System.out.println("密码："+property.getPassword()+property.getPassword().length());
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
     * jedis.close直接放在finally里，用完了自动关闭
     */
    public Jedis getJedis(){
        if (jedisPool == null) {
            System.out.println("连接池为空重新建立");
            poolInit();
        }
        Jedis jedis = null;
        try {
            if (jedisPool != null) {
//                jedis.auth(property.getPassword());
                jedis = jedisPool.getResource();
                logger.info("使用连接池"+jedisPool+"得到连接"+jedis);
            }
            // 将连接池放入到连接中, 这里这么做的目的其实就是为关闭连接的时候作准备的
//            jedis.setDataSource(this);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Could Get jedis error : "+e);
        }
        finally{
            jedis.close();
        }
        // 虽然是释放连接的方法，但是没有立即释放，好像用完才释放的

        // 上面的方法是设置redis将会关闭超时超过30秒的空闲连接。而不是设置读取数据的超时时间。
        // 是否是配置文件那个配置
        jedis.configSet("timeout", "30");
        System.out.println("密码是"+property.getPassword());
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
        status.put("NumActive",jedisPool.getNumActive());
        status.put("NumIdle",jedisPool.getNumIdle());
        status.put("NumWaiters",jedisPool.getNumWaiters());
        return status;
    }

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

