package com.redis.common;

import com.redis.common.domain.ElementsType;
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
    private int db = 0;
    private RedisPools pools;
    private Jedis jedis;
    private static Logger logger = LoggerFactory.getLogger(Commands.class);

    public void select(int db){
        this.db = db;
        getJedis().select(db);
    }

    /**
     *
     * @return 得到当前数据库的连接
     */
    public Jedis getJedis(){
        if(jedis==null){
            pools = management.getRedisPool();
            jedis = pools.getJedis();
        }
        return jedis;
    }
    /**
     *
     * @param db 数据库下标0开始
     * @return 获取指定了数据库的jedis连接
     */
    public Jedis getJedisByDb(int db){
        Jedis jedis = getJedis();
        jedis.select(db);
        return jedis;
    }

    public String type(String key){
        return getJedis().type(key);
    }
    // 切换到指定的id的配置下的连接池
    public void setPools(String id){
        try {
            this.pools = management.getRedisPool(id);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(ExceptionInfo.GET_POOL_BY_ID_FAILED);
        }
    }

    protected ElementsType getValueType(String key) {
        String type = jedis.type(key);
        ElementsType nodeType = null;
        if (type.equals("string"))
            nodeType = ElementsType.STRING;
        else if (type.equals("hash"))
            nodeType = ElementsType.HASH;
        else if (type.equals("list"))
            nodeType = ElementsType.LIST;
        else if (type.equals("set"))
            nodeType = ElementsType.SET;
        else
            nodeType = ElementsType.SORTED_SET;
        return nodeType;
    }
    public String getCurrentId(){
        if (management!=null){
            return management.getCurrentPoolId();
        }else{
            return null;
        }
    }
    public int getDb() {
        return db;
    }

    public void setDb(int db) {
        this.db = db;
    }
    public String flushAll(){
        return getJedis().flushAll();
    }
    public String flushDB(int db){
        return getJedisByDb(db).flushDB();
    }
}
// 开启事务
//Transaction e = jedis.multi();
// e.set("","");
//e.exec();