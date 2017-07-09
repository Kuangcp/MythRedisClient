package com.redis.assemble.list;

import com.redis.common.exception.ReadConfigException;
import com.redis.config.PoolManagement;
import com.redis.config.PropertyFile;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;
import redis.clients.jedis.Jedis;

import java.util.Arrays;
import java.util.List;

/**
 * Created by https://github.com/kuangcp on 17-6-21  上午11:17
 * 这时候就需要理解Mock注解的效用了，是创建一个隔离的虚拟环境，然后运行，然后释放环境？
 * 可是实际使用的时候很奇怪的是 环境具有了，对象都存在，但是没有数据，方法测试全部通过，
 * 但是却不是想要的结果，每个方法的执行都是互不干扰偶的感觉，很迷，
 * 所以还是使用自己的方法，把Spring环境装载进来，但是和我直接写测试方法相比运行时间要花上好多
 *
 * 当加入Spring的环境之后才是我想要的结果
 * 测试全部通过，
 */
public class RedisListTest {
//    @Mock
//    Logger logger;
//    @Mock
//    PoolManagement management;
//    @Mock
//    RedisPools pools;
//    @Mock
//    Jedis jedis;
//    @InjectMocks
    RedisList redisList;
    String testKey="List_key";
    // TODO 完成List Keys Set的完整测试代码

    @Before
    public void setUp() throws ReadConfigException {
        MockitoAnnotations.initMocks(this);
        // 装载Spring环境，获取bean
        PoolManagement management = PoolManagement.getInstance();
        management.setCurrentPoolId(PropertyFile.getMaxId()+"");
        redisList = RedisList.getInstance();
    }

    @Test
    public void testRPush() throws Exception {
        redisList.deleteKey(testKey);
        redisList.rPush(testKey, "values");
        redisList.rPush(testKey, "values");
        redisList.rPush(testKey, "values");
        redisList.rPush(testKey, "values");
        redisList.rPush(testKey, "values");
        long result = redisList.rPush(testKey, "values");
        Assert.assertEquals(6L, result);
    }

    @Test
    public void testLPush() throws Exception {
        redisList.deleteKey(testKey);
        long result = redisList.lPush(testKey, "values");
        Assert.assertEquals(1L, result);
    }

    @Test
    public void testLPushX() throws Exception {
        redisList.deleteKey(testKey);
        long result = redisList.lPushX(testKey, "value");
        Assert.assertEquals(0L, result);
    }

    @Test
    public void testRPushX() throws Exception {
        deleteKeyForTest();
        long result = redisList.rPushX(testKey, "value");
        Assert.assertEquals(0L, result);
    }

    @Test
    public void testRPop() throws Exception {
        deleteKeyForTest();
        redisList.rPush(testKey,"1","2");
        String result = redisList.rPop(testKey);
        Assert.assertEquals("2", result);
        deleteKeyForTest();
    }

    @Test
    public void testLPop() throws Exception {
        redisList.deleteKey(testKey);
        String result = redisList.lPop(testKey);
        Assert.assertEquals(null, result);
    }


    @Test
    public void testRPopLPush() throws Exception {
        redisList.deleteKey("one");
        redisList.deleteKey("other");
        redisList.rPush("one","1");
        redisList.rPush("other","2");
        Assert.assertEquals("1",redisList.rPopLPush("one", "other"));
        Assert.assertEquals(null,redisList.rPopLPush("one", "other"));
        redisList.deleteKey("one");
        redisList.deleteKey("other");
    }

    @Test
    public void testLength() throws Exception {
        deleteKeyForTest();
        redisList.rPush(testKey,"1");
        redisList.lPush(testKey,"2");
        long result = redisList.length(testKey);
        Assert.assertEquals(2L, result);
        deleteKeyForTest();
    }

    @Test
    public void testSetByIndex() throws Exception {
        redisList.lPush(testKey,"12");
        String result = redisList.setByIndex(testKey, 0L, "value");
        Assert.assertEquals("OK", result);
        deleteKeyForTest();
    }

    @Test
    public void testInsertAfter() throws Exception {
        deleteKeyForTest();
        redisList.lPush(testKey,"12");
        long result = redisList.insertAfter(testKey, "12", "value");
        Assert.assertEquals(2L, result);
        deleteKeyForTest();
    }

    @Test
    public void testInsertBefore() throws Exception {
        deleteKeyForTest();
        redisList.lPush(testKey,"12");
        long result = redisList.insertBefore(testKey, "12", "value");
        Assert.assertEquals(2L, result);
        deleteKeyForTest();
    }

    @Test
    public void testRange() throws Exception {
        deleteKeyForTest();
        redisList.rPush(testKey,"23","12");
        List<String> result = redisList.range(testKey, 0L, -1L);
        Assert.assertEquals(Arrays.<String>asList("23","12"), result);
        deleteKeyForTest();
    }

    @Test
    public void testRemove() throws Exception {
        deleteKeyForTest();
        redisList.lPush(testKey,"23","123","23");
        redisList.remove(testKey, 1L, "23");
        Assert.assertEquals(Arrays.<String>asList("123","23"), redisList.allElements(testKey));
        redisList.lPush(testKey,"23");
        redisList.remove(testKey, -1L, "23");
        Assert.assertEquals(Arrays.<String>asList("23","123"), redisList.allElements(testKey));
        deleteKeyForTest();
    }

    @Test
    public void testTrim() throws Exception {
        deleteKeyForTest();
        redisList.rPush(testKey,"1","2","3","4");
        String result = redisList.trim(testKey, 0L, 1L);
        Assert.assertEquals(Arrays.<String>asList("1","2"), redisList.allElements(testKey));
        Assert.assertEquals("OK",result);
    }

    @Test
    public void testIndex() throws Exception {
        deleteKeyForTest();
        redisList.rPush(testKey,"tests");
        String result = redisList.index(testKey, 0L);
        Assert.assertEquals("tests", result);
        deleteKeyForTest();
    }

    @Test
    public void testType() throws Exception {
        deleteKeyForTest();
        redisList.rPush(testKey,"er");
        String result = redisList.type(testKey);
        Assert.assertEquals("list", result);
        deleteKeyForTest();
    }

//    @Test
//    public void testExpire() throws Exception {
//        long result = redisList.expire(testKey, 0);
//        Assert.assertEquals(0L, result);
//    }

//    @Test
//    public void testFlushAll() throws Exception {
//        String result = redisList.flushAll();
//        Assert.assertEquals("OK", result);
//    }
//
//    @Test
//    public void testFlushDB() throws Exception {
//        String result = redisList.flushDB(0);
//        Assert.assertEquals("OK", result);
//    }
    private void deleteKeyForTest(){
        redisList.deleteKey(testKey);
    }
    public void testDeleteKeyindex(){
        Jedis jedis = redisList.getJedis();
//        jedis.l
    }
}
