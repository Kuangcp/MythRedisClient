package com.redis.assemble.list;

import com.redis.SpringInit;
import com.redis.config.PoolManagement;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

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
    @InjectMocks
    RedisList redisList;
    // TODO 完成List Keys Set的完整测试代码

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        // 装载Spring环境，获取bean
        ApplicationContext context = new AnnotationConfigApplicationContext(SpringInit.class);
        PoolManagement management = (PoolManagement) context.getBean("poolManagement");
        management.setCurrentPoolId("1025");
        redisList = (RedisList) context.getBean("redisList");
    }

    @Test
    public void testRPush() throws Exception {
        redisList.deleteKey("key");
        redisList.rPush("key", "values");
        redisList.rPush("key", "values");
        redisList.rPush("key", "values");
        redisList.rPush("key", "values");
        redisList.rPush("key", "values");
        long result = redisList.rPush("key", "values");
        Assert.assertEquals(6L, result);
    }

    @Test
    public void testLPush() throws Exception {
        redisList.deleteKey("key");
        long result = redisList.lPush("key", "values");
        Assert.assertEquals(1L, result);
    }

    @Test
    public void testLPushX() throws Exception {
        redisList.deleteKey("key");
        long result = redisList.lPushX("key", "value");
        Assert.assertEquals(0L, result);
    }

    @Test
    public void testRPushX() throws Exception {
        redisList.deleteKey("key");
        long result = redisList.rPushX("key", "value");
        Assert.assertEquals(0L, result);
    }

    @Test
    public void testRPop() throws Exception {
        redisList.deleteKey("key");
        String result = redisList.rPop("key");
        Assert.assertEquals(null, result);
    }

    @Test
    public void testLPop() throws Exception {
        redisList.deleteKey("key");
        String result = redisList.lPop("key");
        Assert.assertEquals(null, result);
    }

    @Test
    public void testRPopLPush() throws Exception {
        redisList.deleteKey("one");
        redisList.deleteKey("other");
        redisList.rPush("one","1");
        redisList.rPush("other","2");
        Assert.assertEquals(redisList.rPopLPush("one", "other"),"1");
    }

    @Test
    public void testLength() throws Exception {
        long result = redisList.length("key");
        Assert.assertEquals(0L, result);
    }

    @Test
    public void testSetByIndex() throws Exception {
        String result = redisList.setByIndex("key", 0L, "value");
        Assert.assertEquals(null, result);
    }

    @Test
    public void testInsertAfter() throws Exception {
        long result = redisList.insertAfter("key", "pivot", "value");
        Assert.assertEquals(0L, result);
    }

    @Test
    public void testInsertBefore() throws Exception {
        long result = redisList.insertBefore("key", "pivot", "value");
        Assert.assertEquals(0L, result);
    }

    @Test
    public void testRange() throws Exception {
        redisList.rPush("key","32323");
        List<String> result = redisList.range("key", 0L, -1L);
        Assert.assertEquals(Arrays.<String>asList(), result);
        for(String k:result){
            System.out.println(k);
        }
    }

    @Test
    public void testRemove() throws Exception {
        long result = redisList.remove("key", 0L, "value");
        Assert.assertEquals(0L, result);
    }

    @Test
    public void testTrim() throws Exception {
        String result = redisList.trim("key", 0L, 0L);
        Assert.assertEquals(null, result);
    }

    @Test
    public void testIndex() throws Exception {
        String result = redisList.index("key", 0L);
        Assert.assertEquals(null, result);
    }

    @Test
    public void testType() throws Exception {
        String result = redisList.type("key");
        Assert.assertEquals(null, result);
    }

    @Test
    public void testExpire() throws Exception {
        long result = redisList.expire("key", 0);
        Assert.assertEquals(0L, result);
    }

    @Test
    public void testFlushAll() throws Exception {
        String result = redisList.flushAll();
        Assert.assertEquals(null, result);
    }

    @Test
    public void testFlushDB() throws Exception {
        String result = redisList.flushDB(0);
        Assert.assertEquals(null, result);
    }
}
