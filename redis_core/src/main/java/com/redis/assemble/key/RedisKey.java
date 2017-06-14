package com.redis.assemble.key;

import com.redis.common.Commands;
import org.springframework.stereotype.Component;

/**
 * Created by https://github.com/kuangcp on 17-6-12  上午10:45
 */
@Component
public class RedisKey extends Commands{
    public String add (String key,String value){
        return jedis.set(key,value);
    }


    public long deleteKey(String key){
        if(connectionStatus){
            return jedis.del(key);
        }else{
            return 0;
        }
    }
    //转化成utf码？
    public byte[] dump(String key){
        byte[] result =  jedis.dump(key);
        return result;
    }
}
