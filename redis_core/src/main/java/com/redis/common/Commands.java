package com.redis.common;

import com.redis.common.exception.RedisConnectionException;
import com.redis.config.PoolManagement;
import redis.clients.jedis.Jedis;

/**
 * Created by https://github.com/kuangcp on 17-6-11  下午10:57
 * 声明公共的一些方法
 * 如果使用切面的话，对于日志记录功能的添加会比较方便，但是Spring框架还是没有到非用不可的地步
 */
public class Commands {
    // 当前所有的操作都是共享这个参数的： 当前连接池，当前的数据库
    private boolean connectionStatus=false;
    private int db = 0;
    public Jedis jedis = PoolManagement.getRedisPool().getJedis();

    // 在所有操作前，检查连接状态
    public void checkStatus() throws RedisConnectionException {
        if(jedis!=null){
            connectionStatus = true;
        }
        if(!connectionStatus){
            throw new RedisConnectionException("获取jedis连接失败，操作执行中断",Commands.class);
        }
    }
    public void select(int db){
        this.db = db;
        jedis.select(db);
    }

}
