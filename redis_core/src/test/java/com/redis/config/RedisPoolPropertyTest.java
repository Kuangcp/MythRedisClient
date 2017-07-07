package com.redis.config;

import org.junit.Test;

/**
 * Created by https://github.com/kuangcp on 17-6-20  下午4:49
 * 在使用断言的时候，对于对象的判断，使用toString方法更稳妥,就不必担心空指针了，有空指针正好说明方法有问题
 * 全部测试通过
 */
public class RedisPoolPropertyTest {
    private RedisPoolProperty redisPoolProperty = new RedisPoolProperty(400, 100, 10000, false,
            6381, 600, "120.25.203.47", "myth", "1010", "myth");

//    @Test
//    public void testInitByIdFromFile() throws Exception {
//        RedisPoolProperty result = redisPoolProperty.initByIdFromFile("1025");
//        Assert.assertEquals(new RedisPoolProperty(400, 100, 10000, false,
//                6381, 600, "120.25.203.47", "myth", "1025", "myth").toString(), result.toString());
//    }

    // 测试通过，为了不影响构建就注释掉
    @Test
    public void testInitByIdFromConfig() throws Exception {
//        RedisPoolProperty result = RedisPoolProperty.initByIdFromConfig("1002");
//        Assert.assertEquals(redisPoolProperty.toString(), result.toString());
    }
    // 测试成功  为了构建注释
    @Test
    public void testUpdate() throws Exception{
//        RedisPoolProperty property = RedisPoolProperty.initByIdFromConfig("1010");
//        System.out.println(property.toString());
//        property.setHost("127.0.0.1");
//        property.setName("本地 6379");
//        property.setPort(6379);
//        System.out.println(property.toString());
//        RedisPoolProperty.updateConfigFile(property);
//        property = RedisPoolProperty.initByIdFromConfig("1010");
//        System.out.println(property.toString());
    }

}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme