package com.redis.common;

import com.redis.common.exception.ExceptionInfo;
import com.redis.config.PoolManagement;
import com.redis.config.RedisPools;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

/**
 * Created by https://github.com/kuangcp on 17-6-11  下午10:57
 * 声明公共的一些方法
 * 如果使用切面的话，对于日志记录功能的添加会比较方便，但是Spring框架还是没有到非用不可的地步
 * 如果使用Spring，是否是考虑注入Manager那个类
 */
@Component
public class Commands {
    @Autowired
    private PoolManagement management;
    // 当前所有的操作都是共享这个参数的： 当前连接池，当前的数据库
//    protected boolean connectionStatus=false;
    private int db = 0;
    private RedisPools pools;
    private Jedis jedis;
    private static Logger logger = LoggerFactory.getLogger(Commands.class);


    // 在所有操作前，检查连接状态
//    public void checkStatus() throws RedisConnectionException {
//        if(jedis!=null){
//            connectionStatus = true;
//        }
//        if(!connectionStatus){
//            throw new RedisConnectionException("获取jedis连接失败，操作执行中断",Commands.class);
//        }
//    }
    public void select(int db){
        this.db = db;
        getJedis().select(db);
    }

    // 子类使用该方法得到连接
    public Jedis getJedis(){
        if(jedis==null){
            pools = management.getRedisPool();
            jedis = pools.getJedis();
        }
//        System.out.println(jedis);
        return jedis;
    }

    // 切换到指定的id
    public void setPools(String id){
        try {
            this.pools = management.getRedisPool(id);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(ExceptionInfo.GET_POOL_BY_ID_FAILED);
        }
    }
}
