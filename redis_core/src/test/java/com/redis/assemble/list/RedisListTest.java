package com.redis.assemble.list;

import com.redis.config.PoolManagement;
import com.redis.config.RedisPools;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import redis.clients.jedis.Jedis;

import java.util.Arrays;
import java.util.List;

/**
 * Created by https://github.com/kuangcp on 17-6-21  上午11:17
 */
public class RedisListTest {
    @Mock
    Logger logger;
    @Mock
    PoolManagement management;
    @Mock
    RedisPools pools;
    @Mock
    Jedis jedis;
    @InjectMocks
    RedisList redisList;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testRPush() throws Exception {
        long result = redisList.rPush("key", "values");
        Assert.assertEquals(0L, result);
    }

    @Test
    public void testLPush() throws Exception {
        long result = redisList.lPush("key", "values");
        Assert.assertEquals(0L, result);
    }

    @Test
    public void testLPushX() throws Exception {
        long result = redisList.lPushX("key", "value");
        Assert.assertEquals(0L, result);
    }

    @Test
    public void testRPushX() throws Exception {
        long result = redisList.rPushX("key", "value");
        Assert.assertEquals(0L, result);
    }

    @Test
    public void testRPop() throws Exception {
        String result = redisList.rPop("key");
        Assert.assertEquals("replaceMeWithExpectedResult", result);
    }

    @Test
    public void testLPop() throws Exception {
        String result = redisList.lPop("key");
        Assert.assertEquals("replaceMeWithExpectedResult", result);
    }

    @Test
    public void testRPopLPush() throws Exception {
        redisList.rPopLPush("one", "other");
    }

    @Test
    public void testLength() throws Exception {
        long result = redisList.length("key");
        Assert.assertEquals(0L, result);
    }

    @Test
    public void testSetByIndex() throws Exception {
        String result = redisList.setByIndex("key", 0L, "value");
        Assert.assertEquals("replaceMeWithExpectedResult", result);
    }

    @Test
    public void testInsertAfter() throws Exception {
        long result = redisList.insertAfter("key", "pivot", "value");
        Assert.assertEquals(1L, result);
    }

    @Test
    public void testInsertBefore() throws Exception {
        long result = redisList.insertBefore("key", "pivot", "value");
        Assert.assertEquals(1L, result);
    }

    @Test
    public void testRange() throws Exception {
        List<String> result = redisList.range("key", 0L, 0L);
        Assert.assertEquals(Arrays.<String>asList("String"), result);
    }

    @Test
    public void testRemove() throws Exception {
        long result = redisList.remove("key", 0L, "value");
        Assert.assertEquals(1L, result);
    }

    @Test
    public void testTrim() throws Exception {
        String result = redisList.trim("key", 0L, 0L);
        Assert.assertEquals("replaceMeWithExpectedResult", result);
    }

    @Test
    public void testIndex() throws Exception {
        String result = redisList.index("key", 0L);
        Assert.assertEquals("replaceMeWithExpectedResult", result);
    }

    @Test
    public void testType() throws Exception {
        String result = redisList.type("key");
        Assert.assertEquals("replaceMeWithExpectedResult", result);
    }

    @Test
    public void testExpire() throws Exception {
        long result = redisList.expire("key", 0);
        Assert.assertEquals(0L, result);
    }

    @Test
    public void testFlushAll() throws Exception {
        String result = redisList.flushAll();
        Assert.assertEquals("replaceMeWithExpectedResult", result);
    }

    @Test
    public void testFlushDB() throws Exception {
        String result = redisList.flushDB(0);
        Assert.assertEquals("replaceMeWithExpectedResult", result);
    }
}
