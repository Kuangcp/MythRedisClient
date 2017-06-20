package com.redis.assemble.key;

import com.redis.SpringInit;
import com.redis.common.domain.Elements;
import com.redis.common.domain.ElementsType;
import com.redis.common.domain.Order;
import com.redis.config.PoolManagement;
import com.redis.config.RedisPools;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import redis.clients.jedis.Jedis;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by https://github.com/kuangcp on 17-6-20  下午8:02
 */
public class RedisKeyTest {
    @Mock //用@Mocked标注的对象，不需要赋值，jmockit自动mock
    Logger logger;
    @Mock
    PoolManagement management;
    @Mock
    RedisPools pools;
    @Mock
    Jedis jedis;
    @InjectMocks
    RedisKey redisKey;

    @Before
    public void setUp() {
        // TODO 理解JMockit 原理，这里写这个和自己写的测试类都没差了，测试花费的时间是个问题
        MockitoAnnotations.initMocks(this);
        ApplicationContext context = new AnnotationConfigApplicationContext(SpringInit.class);
        PoolManagement management = (PoolManagement) context.getBean("poolManagement");
        management.initPool("1025");
        redisKey = (RedisKey) context.getBean("redisKey");
    }

    @Test
    public void testGet() throws Exception {
        System.out.println(redisKey);
        String result = redisKey.get("key");
        Assert.assertEquals("d", result);
    }

    @Test
    public void testSet() throws Exception {
        String result = redisKey.set("key", "value");
        Assert.assertEquals("OK", result);
    }

    @Test
    public void testDeleteKey() throws Exception {
        long result = redisKey.deleteKey("key");
        Assert.assertEquals(0L, result);
    }

    @Test
    public void testDump() throws Exception {
        byte[] result = redisKey.dump("key");
        Assert.assertArrayEquals(new byte[]{(byte) 0}, result);
    }

    @Test
    public void testSetExpire() throws Exception {
        String result = redisKey.setExpire("key", 0, "value");
        Assert.assertEquals("replaceMeWithExpectedResult", result);
    }

    @Test
    public void testSetExpireMs() throws Exception {
        String result = redisKey.setExpireMs("key", 0L, "value");
        Assert.assertEquals("replaceMeWithExpectedResult", result);
    }

    @Test
    public void testIncreaseKey() throws Exception {
        long result = redisKey.increaseKey("key");
        Assert.assertEquals(0L, result);
    }

    @Test
    public void testDecreaseKey() throws Exception {
        long result = redisKey.decreaseKey("key");
        Assert.assertEquals(0L, result);
    }

    @Test
    public void testAppend() throws Exception {
        long result = redisKey.append("key", "value");
        Assert.assertEquals(0L, result);
    }

    @Test
    public void testGetMultiKeys() throws Exception {
        List<String> result = redisKey.getMultiKeys("keys");
        Assert.assertEquals(Arrays.<String>asList("String"), result);
    }

    @Test
    public void testSetMultiKey() throws Exception {
        String result = redisKey.setMultiKey("keys");
        Assert.assertEquals("replaceMeWithExpectedResult", result);
    }

    @Test
    public void testGetEncoding() throws Exception {
        String result = redisKey.getEncoding("key");
        Assert.assertEquals("replaceMeWithExpectedResult", result);
    }

    @Test
    public void testListKeys() throws Exception {
        Set<Elements> result = redisKey.listKeys();
        Assert.assertEquals(new HashSet<Elements>(Arrays.asList(new Elements(0, "id", "key", ElementsType.ROOT, Order.Ascend))), result);
    }

    @Test
    public void testType() throws Exception {
        String result = redisKey.type("key");
        Assert.assertEquals("replaceMeWithExpectedResult", result);
    }

    @Test
    public void testExpire() throws Exception {
        long result = redisKey.expire("key", 0);
        Assert.assertEquals(0L, result);
    }

    @Test
    public void testFlushAll() throws Exception {
        String result = redisKey.flushAll();
        Assert.assertEquals("replaceMeWithExpectedResult", result);
    }

    @Test
    public void testFlushDB() throws Exception {
        String result = redisKey.flushDB(0);
        Assert.assertEquals("replaceMeWithExpectedResult", result);
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme