package com.redis.assemble.set;

import com.redis.common.Commands;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;


/**
 * Created by https://github.com/kuangcp on 17-6-18  下午9:59
 */
@Component
public class RedisSet extends Commands{
    private static Logger logger = LoggerFactory.getLogger(RedisSet.class);

    // TODO 测试完所有的方法
    /**
     * 往set追加成员，如果set不存在就新建，如果set已经存在，且追加的成员有重复，重复的忽略操作，头插法
     * @param key 键
     * @param member 元素
     * @return set长度
     */
    public Long add(String key, String... member){
        return getJedis().sadd(key,member);
    }
    public Long remove(String key, String... members){
        return getJedis().srem(key,members);
    }
    public Set<String> getMembersSet(String key){
        return getJedis().smembers(key);
    }

    public void pop(String key){

    }
    public void pop(String key,long count){

    }
    public Long length(String key){
        return getJedis().scard(key);
    }
    public boolean contain(String key,String member){
        return getJedis().sismember(key,member);
    }
    public String randomMember(String key){
        return getJedis().srandmember(key);
    }
    public List<String> randomMember(String key, int count){
        return getJedis().srandmember(key,count);
    }
    public Set<String> inter(String... keys){
        return getJedis().sinter(keys);
    }
    public void interStore(){

    }
    public Set<String> union(String... keys){
        return getJedis().sunion(keys);
    }
    public void unionStore(){

    }
    public Set<String> diff(String... keys){
        return getJedis().sdiff(keys);
    }
    public void diffStore(){

    }
    public void moveMember(String fromKey,String member,String toKey){

    }

}
