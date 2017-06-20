package com.redis.config;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by https://github.com/kuangcp on 17-6-20  下午4:49
 * 在使用断言的时候，对于对象的判断，使用toString方法更稳妥,就不必担心空指针了，有空指针正好说明方法有问题
 */
public class RedisPoolPropertyTest {
    private RedisPoolProperty redisPoolProperty = new RedisPoolProperty(400, 100, 10000, false,
            6381, 600, "120.25.203.47", "myth", "1025", "myth");

//    @Test
//    public void testInitByIdFromFile() throws Exception {
//        RedisPoolProperty result = redisPoolProperty.initByIdFromFile("1025");
//        Assert.assertEquals(new RedisPoolProperty(400, 100, 10000, false,
//                6381, 600, "120.25.203.47", "myth", "1025", "myth").toString(), result.toString());
//    }

    @Test
    public void testInitByIdFromConfig() throws Exception {
        RedisPoolProperty result = RedisPoolProperty.initByIdFromConfig("1025");
        Assert.assertEquals(new RedisPoolProperty(400, 100, 10000, false,
                6381, 600, "120.25.203.47", "myth", "1025", "myth").toString(), result.toString());
    }

}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme