package com.redis.assemble.key;

import com.redis.common.Commands;
import org.springframework.stereotype.Component;

/**
 * Created by https://github.com/kuangcp on 17-6-12  上午10:45
 */
@Component
public class RedisKey extends Commands{

    public String get(String key){
        return getJedis().get(key);
    }

    public String set (String key,String value){
        return getJedis().set(key,value);
    }

    // 返回 1成功0失败
    public long deleteKey(String key){
        return getJedis().del(key);
    }
    //转化成utf 码？
    public byte[] dump(String key){
        return getJedis().dump(key);
    }
    public long expire(String key,int second){
        if(second != -1)
            return getJedis().expire(key, second);
        else
            return getJedis().persist(key);
    }
}
