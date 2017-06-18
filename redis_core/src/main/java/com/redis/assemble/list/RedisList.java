package com.redis.assemble.list;

import com.redis.common.Commands;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by https://github.com/kuangcp on 17-6-18  上午10:29
 */
@Component
public class RedisList extends Commands{

    // TODO 类型的判断
    // 插入，如果不存在就新建然后插入
    public long rPush(String key, String... values){
        return getJedis().rpush(key,values);
    }
    public long lPush(String key, String... values){
        return getJedis().lpush(key,values);
    }
    // 区别在于，如果不存在key就不进行插入
    public long lPushX(String key, String... value){
        return getJedis().lpushx(key,value);
    }
    public long rPushX(String key, String... value){
        return getJedis().rpushx(key,value);
    }
    public String rPop(String key){
        return getJedis().rpop(key);
    }
    public String lPop(String key){
        return getJedis().lpop(key);
    }
    public long length(String key){
       return getJedis().llen(key);
    }
    public List<String> range(String key, int start, int end){
        return getJedis().lrange(key,start,end);
    }

    /**
     *
     * @param key 键
     * @param index 长整型
     * @return 值
     */
    public String index(String key, long index){
        return getJedis().lindex(key,index);
    }
}
