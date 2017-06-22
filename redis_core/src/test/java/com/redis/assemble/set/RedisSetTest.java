package com.redis.assemble.set;

import com.redis.SpringInit;
import com.redis.config.PoolManagement;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by https://github.com/kuangcp on 17-6-22  下午4:27
 */
public class RedisSetTest {
//    @Mock
//    Logger logger;
//    @Mock
//    PoolManagement management;
//    @Mock
//    RedisPools pools;
//    @Mock
//    Jedis jedis;
//    @InjectMocks
    RedisSet redisSet;
    String key = "testSet";

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        // 装载Spring环境，获取bean
        ApplicationContext context = new AnnotationConfigApplicationContext(SpringInit.class);
        PoolManagement management = (PoolManagement) context.getBean("poolManagement");
        management.setCurrentPoolId("1025");
        redisSet = (RedisSet) context.getBean("redisSet");
    }

    // 采用的头插法
    @Test
    public void testAdd() throws Exception {
        deleteKey();
        Long result = redisSet.add(key, "member","12","12");
        Assert.assertEquals((Long)2L, result);
        showKey();
        deleteKey();
    }

    @Test
    public void testRemove() throws Exception {
        Long result = redisSet.remove(key, "members");
        Assert.assertEquals((Long)1L, result);
    }

    @Test
    public void testGetMembersSet() throws Exception {
        Set<String> result = redisSet.getMembersSet(key);
        Assert.assertEquals(new HashSet<String>(Arrays.asList("String")), result);
    }

    @Test
    public void testPop() throws Exception {
        redisSet.pop(key);
    }

    @Test
    public void testPop2() throws Exception {
        redisSet.pop(key, 0L);
    }

    @Test
    public void testLength() throws Exception {
        Long result = redisSet.length(key);
        Assert.assertEquals((Long)1L, result);
    }

    @Test
    public void testContain() throws Exception {
        boolean result = redisSet.contain(key, "member");
        Assert.assertEquals(true, result);
    }

    @Test
    public void testRandomMember() throws Exception {
        String result = redisSet.randomMember(key);
        Assert.assertEquals("replaceMeWithExpectedResult", result);
    }

    @Test
    public void testRandomMember2() throws Exception {
        List<String> result = redisSet.randomMember(key, 0);
        Assert.assertEquals(Arrays.<String>asList("String"), result);
    }

    @Test
    public void testInter() throws Exception {
        Set<String> result = redisSet.inter("keys");
        Assert.assertEquals(new HashSet<String>(Arrays.asList("String")), result);
    }

    @Test
    public void testInterStore() throws Exception {
        redisSet.interStore();
    }

    @Test
    public void testUnion() throws Exception {
        Set<String> result = redisSet.union("keys");
        Assert.assertEquals(new HashSet<String>(Arrays.asList("String")), result);
    }

    @Test
    public void testUnionStore() throws Exception {
        redisSet.unionStore();
    }

    @Test
    public void testDiff() throws Exception {
        Set<String> result = redisSet.diff("keys");
        Assert.assertEquals(new HashSet<String>(Arrays.asList("String")), result);
    }

    @Test
    public void testDiffStore() throws Exception {
        redisSet.diffStore();
    }

    @Test
    public void testMoveMember() throws Exception {
        redisSet.moveMember("fromKey", "member", "toKey");
    }

    @Test
    public void testType() throws Exception {
        String result = redisSet.type(key);
        Assert.assertEquals("replaceMeWithExpectedResult", result);
    }

    @Test
    public void testExpire() throws Exception {
        Long result = redisSet.expire(key, 0);
        Assert.assertEquals((Long)1L, result);
    }

    @Test
    public void testFlushAll() throws Exception {
        String result = redisSet.flushAll();
        Assert.assertEquals("replaceMeWithExpectedResult", result);
    }

    @Test
    public void testFlushDB() throws Exception {
        String result = redisSet.flushDB(0);
        Assert.assertEquals("replaceMeWithExpectedResult", result);
    }

    @Test
    public void testDeleteKey() throws Exception {
        long result = redisSet.deleteKey(key);
        Assert.assertEquals(1L, result);
    }
    private void deleteKey(){
        redisSet.deleteKey(key);
    }
    private void showKey(){
        Set<String> sets= redisSet.getMembersSet(key);
        for (String re:sets){
            System.out.println("==>"+re);
        }
    }
}
