package com.redis.assemble.set;

import com.redis.common.Commands;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Set;


/**
 * Created by https://github.com/kuangcp on 17-6-18  下午9:59
 * Redis 中的无序集合 set 特点：无序，不允许重复，set元素最大可以包含2的32次方-1个元素。
 * 利用set集合类型，我们可以快速取出n个key之间的并集、交集、差集等，从而轻松解决mysql等数据库不容易实现这种运算的缺陷。
 */
public class RedisSet extends Commands{
    private static Logger logger = LoggerFactory.getLogger(RedisSet.class);
    private static RedisSet redisSet;
    private RedisSet(){}
    public synchronized static RedisSet getInstance(){
        if (redisSet == null){
            synchronized (RedisSet.class){
                if(redisSet == null){
                    redisSet = new RedisSet();
                }
            }
        }
        return redisSet;
    }

    /**
     * 往set追加成员，如果set不存在就新建，如果set已经存在，且追加的成员有重复，重复的忽略操作，头插法
     * @param key 键
     * @param member 元素
     * @return set长度
     */
    public Long save(String key, String... member){
        return getJedis().sadd(key,member);
    }

    /**
     *
     * @param key 键
     * @param members 要删除的成员
     * @return set剩余长度
     */
    public Long remove(String key, String... members){
        return getJedis().srem(key,members);
    }
    public Set<String> getMembersSet(String key){
        return getJedis().smembers(key);
    }

    /**
     * 随机删除一个元素
     * @param key 键
     * @return 删除的那个元素
     */
    public String pop(String key){
        return getJedis().spop(key);
    }

    /**
     *
     * @param key 键
     * @param count 删除个数
     * @return 删除的那些元素集合
     */
    public Set<String> pop(String key, long count){
        return getJedis().spop(key,count);
    }

    /**
     *
     * @param key 键
     * @return 返回set大小，键不存在就返回 0
     */
    public Long size(String key){
        return getJedis().scard(key);
    }
    public boolean contain(String key,String member){
        return getJedis().sismember(key,member);
    }

    /**
     * 随机返回一个元素，不会删除
     * @param key 键
     * @return 元素，键为空就返回null
     */
    public String randomMember(String key){
        return getJedis().srandmember(key);
    }

    /**
     * 随机返回多个元素
     * @param key 键
     * @param count 个数，当个数大于set的大小就只是返回set所有元素
     * @return 随机返回的集合
     */
    public List<String> randomMember(String key, int count){
        return getJedis().srandmember(key,count);
    }

    /**
     * 交集合运算
     * @param keys 多个set的键
     * @return 交集的集合
     */
    public Set<String> inter(String... keys){
        return getJedis().sinter(keys);
    }

    /**
     * 做交集运算并把结果保存到一个set里
     * @param key 要保存的set（无就新建，有就覆盖）
     * @param keys 多个set键
     * @return 1/0 成功/失败
     */
    public Long interStore(String key, String... keys){
        return getJedis().sinterstore(key,keys);
    }

    /**
     * 并集
     * @param keys 并集操作的键
     * @return 返回并集的集合
     */
    public Set<String> union(String... keys){
        return getJedis().sunion(keys);
    }
    public Long unionStore(String key, String... keys){
        return getJedis().sunionstore(key,keys);
    }

    /**
     * 差集（A-B-C = A减去 ABC三个集合的并集部分）
     * @param keys 多个集合的键：第一个减去后面所有的集合
     * @return 返回结果集合
     */
    public Set<String> diff(String... keys){
        return getJedis().sdiff(keys);
    }

    /**
     *
     * @param key 存放的键
     * @param keys 运算的键，第一个键减去后面所有的键
     * @return 1/0 成功/失败
     */
    public Long diffStore(String key, String... keys){
        return getJedis().sdiffstore(key,keys);
    }

    /**
     *
     * @param fromKey 源 键
     * @param toKey 目标 键
     * @param member 元素
     * @return 1/0
     */
    public Long moveMember(String fromKey, String toKey, String member){
        return getJedis().smove(fromKey,toKey,member);
    }

}
