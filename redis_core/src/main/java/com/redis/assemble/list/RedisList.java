package com.redis.assemble.list;

import com.redis.common.Commands;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import redis.clients.jedis.BinaryClient;

import java.util.List;


/**
 * Created by https://github.com/kuangcp on 17-6-18  上午10:29
 */
@Component
public class RedisList extends Commands{

    private static Logger logger = LoggerFactory.getLogger(RedisList.class);

    // TODO 类型的判断 交给中间层

    //

    /**
     * 右/尾部插入，如果不存在就新建然后插入
     * @param key key
     * @param values 多个value
     * @return 插入后的长度
     */
    public long rPush(String key, String... values){
        return getJedis().rpush(key,values);
    }
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
    public String setByIndex(String key, long index, String value){
        return getJedis().lset(key,index,value);
    }

    /**
     * @param key list键
     * @param pivot 关键的元素,有重复就只看第一个
     * @param value 在关键元素之后插入
     * @return list长度
     */
    public Long insertAfter(String key, String pivot,String value){
        return getJedis().linsert(key, BinaryClient.LIST_POSITION.AFTER,pivot,value);
    }

    /**
     * @param key list键
     * @param pivot 关键的元素
     * @param value 之前插入
     * @return list 长度
     */
    public Long insertBefore(String key,String pivot,String value){
        return getJedis().linsert(key, BinaryClient.LIST_POSITION.BEFORE,pivot,value);
    }
// TODO rpoplpush
    /**
     *
     * @param key list键
     * @param start start
     * @param end end
     * @return 值集合
     */
    public List<String> range(String key, long start, long end){
        return getJedis().lrange(key,start,end);
    }

    /**
     *
     * @param key list键
     * @param count 大于0头到尾按顺序移除值为value的list元素 小于0相反 等于0是list中全部
     * @param value 移除的value
     * @return 个数
     */
    public Long rem(String key, long count, String value){
        return getJedis().lrem(key,count,value);
    }

    /**
     * 对list截取
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
