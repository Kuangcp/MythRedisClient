package com.redis.assemble.key;

import com.redis.common.Commands;
import com.redis.common.exception.ExceptionInfo;
import com.redis.common.exception.TypeErrorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;

import java.util.List;
import java.util.Set;

/**
 * Created by https://github.com/kuangcp on 17-6-12  上午10:45
 * 不需要对当前数据库id进行时刻的监控，前端操作切换了数据库就把db换掉即可，共享db
 */
public class RedisKey extends Commands{
    private static Logger logger = LoggerFactory.getLogger(RedisKey.class);
    private static RedisKey redisKey;
    private RedisKey(){}
    public synchronized static RedisKey getInstance(){
        if(redisKey ==null){
            synchronized (RedisKey.class){
                if(redisKey == null){
                    redisKey = new RedisKey();
                }
            }
        }
        return redisKey;
    }

    /**
     *
     * @param key 键
     * @return 有就返回，没有就返回null 或者不是String类型的
     */
    public String get(String key){
        Jedis jedis = getJedis();
        if("string".equals(jedis.type(key))){
            return jedis.get(key);
        }else{
            logger.error(ExceptionInfo.KEY_TYPE_NOT_STRING+" and type is "+jedis.type(key));
            return null;
        }
    }

    public String set (String key,String value){
        return getJedis().set(key,value);
    }

    //序列化给定 key ，并返回被序列化的值，使用 RESTORE 命令可以将这个值反序列化为 Redis 键。
    public byte[] dump(String key){
        return getJedis().dump(key);
    }

    /**
     * 新建一个带存活时间的值,其他的数据类型没有这种操作，只能先新建然后设置存活时间，只好自己自定义一个了
     * @param key 键
     * @param second 存活时间 大于0
     * @param value 值
     * @return 1/0 成功/失败
     */
    public Long setExpire(String key, int second, String value){
        Jedis jedis = getJedis();
        jedis.set(key,value);
        return expire(key,second);
    }

    /**
     *
     * @param key key
     * @param second 毫秒
     * @param value 值
     * @return OK 成功/失败
     */
    public String setExpireMs(String key,long second,String value){
        return getJedis().psetex(key,second,value);
    }

    /**
     * 使用了异常来作为流程控制，虽然说不符合正统的思想，但是很方便，而且在控制范围内
     * @param key 键
     * @return 返回增加后的值
     * @throws TypeErrorException 如果值不是数值就抛异常
     */
    public long increaseKey(String key) throws TypeErrorException {
        checkIntValue(key);
        return getJedis().incr(key);
    }
    public long decreaseKey(String key) throws TypeErrorException {
        checkIntValue(key);
        return getJedis().decr(key);
    }
    // 试试能不能强转
    private void checkIntValue(String key) throws TypeErrorException {
        try {
            Integer.parseInt(getJedis().get(key));
        }catch (Exception e){
            throw new TypeErrorException(ExceptionInfo.VALUE_NOT_INT,e,RedisKey.class);
        }
    }

    /**
     *
     * @param key 如果键存在就追加值，不存在就新建
     * @param value 追加的内容
     * @return 返回追加后值的长度
     */
    public long append(String key,String value){
        return getJedis().append(key,value);
    }
    /**
     * 获取多个key
     * @param keys 多个key
     * @return list
     */
    public List<String> getMultiKeys(String... keys){
        return getJedis().mget(keys);
    }

    /**
     * set多个key
     * @param keys [key-value] 顺序，偶数个参数
     * @return Status code reply Basically +OK as MSET can't fail
     */
    public String setMultiKey(String... keys){
        return getJedis().mset(keys);
    }

    /**
     *
     * @param key 键
     * @return 整数 int，字符串 embstr， 浮点数 当成字符串
     */
    public String getEncoding(String key){
        return getJedis().objectEncoding(key);
    }
    //列出当前数据库所有key
//    public Set<Elements> listKeys(){
//        Set<Elements> elementsSet = new TreeSet<>();
//        Set<String> keys = getJedis().keys("*");
//        for (String key : keys) {
//            ElementsType nodeType = getValueType(key);
//            Elements node = new Elements(getDb(), getCurrentId(), key, nodeType);
//            elementsSet.add(node);
//        }
//        return elementsSet;
//    }

    /**
     * 取得所有key 当前数据库
     * @return Set 集合
     */
    public Set<String> listAllKeys(int db){
        logger.debug("RedisKey Management:"+this.getPools());
        setDb(db);
        return getJedis().keys("*");
    }

}
