package com.redis.assemble.list;

import com.redis.common.Commands;
import com.redis.common.exception.ActionErrorException;
import com.redis.common.exception.ExceptionInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.BinaryClient;

import java.util.List;


/**
 * Created by https://github.com/kuangcp on 17-6-18  上午10:29
 */
public class RedisList extends Commands{

    private static Logger logger = LoggerFactory.getLogger(RedisList.class);
    private static RedisList redisList;
    private RedisList(){}
    public synchronized static RedisList getInstance(){
        if(redisList == null){
            synchronized (RedisList.class){
                if(redisList == null){
                    redisList = new RedisList();
                }
            }
        }
        return redisList;
    }

    /**
     * 右/尾部插入，如果不存在就新建然后插入
     * @param key key
     * @param values 多个value
     * @return 插入后的长度
     */
    public long rPush(String key, String... values){
        return getJedis().rpush(key,values);
    }

    /**
     * 头，左
     * @param key key
     * @param values 多个值，插入后是逆序的
     * @return 插入操作后长度
     */
    public long lPush(String key, String... values){
        return getJedis().lpush(key,values);
    }

    /**
     * 区别在于，如果不存在key就不进行任何操作
     * @param key key
     * @param value 多个value
     * @return 更改后的长度
     */
    public long lPushX(String key, String... value){
        return getJedis().lpushx(key,value);
    }
    public long rPushX(String key, String... value){
        return getJedis().rpushx(key,value);
    }

    /**
     * 右/尾 弹出，如果为空就不操作
     * @param key key
     * @return 弹出的那个值
     */
    public String rPop(String key){
        return getJedis().rpop(key);
    }
    public String lPop(String key){
        return getJedis().lpop(key);
    }
    /**
     * 队列的状态变化
     * @param one 将这个list的末尾 移动到
     * @param other 这个list的头部
     * @return String 成功是1 失败就是null(键不存在，list没有元素了)
     */
    public String rPopLPush(String one, String other){
        return getJedis().rpoplpush(one,other);
    }

    public long length(String key){
       return getJedis().llen(key);
    }

    /**
     * 在指定index插入值，其他的后移
     * @param key 键
     * @param index 下标
     * @param value 值
     * @return 状态值成功就OK
     */
    public String setByIndex(String key, long index, String value) throws ActionErrorException {
        String result;
        try{
            result = getJedis().lset(key,index,value);
            return result;
        }catch (Exception e){
            throw new ActionErrorException(ExceptionInfo.KEY_NOT_EXIST,e,RedisList.class);
        }
    }

    /**
     * @param key list键
     * @param pivot 关键的元素,有重复就只看第一个
     * @param value 在关键元素之后插入
     * @return list长度
     */
    public long insertAfter(String key, String pivot,String value){
        return getJedis().linsert(key, BinaryClient.LIST_POSITION.AFTER,pivot,value);
    }

    /**
     * @param key list键
     * @param pivot 关键的元素,只匹配到第一个
     * @param value 之前插入
     * @return list 长度
     */
    public long insertBefore(String key,String pivot,String value){
        return getJedis().linsert(key, BinaryClient.LIST_POSITION.BEFORE,pivot,value);
    }
    /**
     * 截取指定区间的list，特点是区间不合理的时候是返回空集合，不会抛异常
     * @param key list键
     * @param start start 如果start超出了长度，或者end小于start 没有异常，但是没有数据返回
     * @param end end
     * @return 值集合，有问题就返回空集合
     */
    public List<String> range(String key, long start, long end){
        logger.debug("Get range: ["+key+"] form "+start+" to "+end);
        return getJedis().lrange(key,start,end);
    }
    public List<String> allElements(String key){
        return getJedis().lrange(key,0,-1);
    }

    /**
     * 正 左至右 负 右至左
     * @param key list键
     * @param count 大于0头到尾按顺序移除值为value的list元素 小于0相反 等于0是list中全部
     * @param value 移除的value
     * @return 移除的个数
     */
    public long remove(String key, long count, String value){
        return getJedis().lrem(key,count,value);
    }

    /**
     * 截取，只保留list[start:end]
     * @param key key
     * @param start start
     * @param end end 如果end超出了list长度就是默认end为list最大下标
     * @return 执行状态值成功就是OK
     */
    public String trim(String key, long start, long end){
        return getJedis().ltrim(key,start,end);
    }

    /**
     *
     * @param key 键
     * @param index 长整型 数组下标0
     * @return 值
     */
    public String index(String key, long index){
        return getJedis().lindex(key,index);
    }
}
