package com.redis.assemble.key;

import com.redis.common.Commands;
import org.springframework.stereotype.Component;

/**
 * Created by https://github.com/kuangcp on 17-6-12  上午10:45
 * 操作最简单的key,不能使用Spring
 * 就是使用单例模式
 */
@Component
public class RedisKey extends Commands{

//    public String add (){
//        jedis.set()
//    }
    public void deleteKey(){

    }
    public byte[] dump(String key){
        byte[] result =  jedis.dump(key);
        return result;
    }
}
