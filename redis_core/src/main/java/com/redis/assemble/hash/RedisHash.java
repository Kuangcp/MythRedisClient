package com.redis.assemble.hash;

import com.redis.common.Commands;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by https://github.com/kuangcp on 17-6-23  上午9:24
 */
public class RedisHash extends Commands {

    public Long save(String key, String member, String value){
        return getJedis().hset(key, member, value);
    }
    public String save(String key, Map<String,String> map){
        return getJedis().hmset(key,map);
    }
    public Long saveWhenNotExist(String key, String member, String value){
        return getJedis().hsetnx(key, member, value);
    }
    public String get(String key, String member){
        return getJedis().hget(key, member);
    }
    public List<String> get(String key, String...member){
        return getJedis().hmget(key,member);
    }
    public Set<String> getMembers(String key){
        return getJedis().hkeys(key);
    }
    public List<String> getValues(String key){
        return getJedis().hvals(key);
    }
    public Boolean exist(String key){
        return getJedis().exists(key);
    }
    public Long increase(String key, String member, long value){
        return getJedis().hincrBy(key, member, value);
    }
    public Double increaseByFloat(String key, String member, double value){
        return getJedis().hincrByFloat(key, member, value);
    }
    public Long decrease(String key, long value){
        return getJedis().decrBy(key,value);
    }
    public Long length(String key){
        return getJedis().hlen(key);
    }
    public Long remove(String key, String... values){
        return getJedis().hdel(key,values);
    }

}
