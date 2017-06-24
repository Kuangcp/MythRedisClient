package com.redis.assemble.set;

import com.redis.SpringInit;
import com.redis.common.exception.ReadConfigException;
import com.redis.config.PoolManagement;
import com.redis.config.PropertyFile;
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
 * 测试完成 2017-06-23 21:42:54
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
    public void setUp() throws ReadConfigException {
        MockitoAnnotations.initMocks(this);

        // 装载Spring环境，获取bean
        ApplicationContext context = new AnnotationConfigApplicationContext(SpringInit.class);
        PoolManagement management = (PoolManagement) context.getBean("poolManagement");
        management.setCurrentPoolId(PropertyFile.getMaxId()+"");
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
        redisSet.add(key,"2","23","3434","343223");
        Long result = redisSet.remove(key, "2","23");
        Assert.assertEquals((Long)2L, result);
        deleteKey();
    }

    // 获取所有
    @Test
    public void testGetMembersSet() throws Exception {
        redisSet.add(key,"2","23");
        Set<String> result = redisSet.getMembersSet(key);
        Assert.assertEquals(new HashSet<String>(Arrays.asList("2","23")), result);
        deleteKey();
    }
    // 随机删除，因为set的无序性
    @Test
    public void testPop() throws Exception {
        redisSet.add(key,"1","2","3");
        String result = redisSet.pop(key);
        System.out.println("随机删除："+result);
        Long results = redisSet.size(key);
        Assert.assertEquals((Long)2L,results);
        deleteKey();
    }

    @Test
    public void testPop2() throws Exception {

        redisSet.add(key,"1","2","3","4");
        Set<String> result = redisSet.pop(key, 2L);
        System.out.println("随机删除："+result);
        Long results = redisSet.size(key);
        Assert.assertEquals((Long)2L,results);
        deleteKey();
    }


    @Test
    public void testContain() throws Exception {
        redisSet.add(key,"member");
        boolean result = redisSet.contain(key, "member");
        Assert.assertEquals(true, result);
        deleteKey();
    }

    @Test
    public void testRandomMember() throws Exception {
        redisSet.add(key,"sa");
        String result = redisSet.randomMember(key);
        assert result!=null;
        deleteKey();
    }

    @Test
    public void testRandomMember2() throws Exception {
        redisSet.add(key,"sa");
        List<String> result = redisSet.randomMember(key, 2);
        Assert.assertEquals(Arrays.<String>asList("sa"), result);
    }

    @Test
    public void testInter() throws Exception {
        redisSet.add(key,"1","2","3");
        redisSet.add("1","2","4");

        Set<String> result = redisSet.inter(key,"1");
        Assert.assertEquals(new HashSet<String>(Arrays.asList("2")), result);
        redisSet.deleteKey(key,"1");
    }

    @Test
    public void testInterStore() throws Exception {
        redisSet.add("store","1","23","45");
        System.out.println(redisSet.getMembersSet("store"));
        redisSet.add(key,"1","2","3");
        redisSet.add("1","2","4");

        Long f = redisSet.interStore("store",key,"1");
        System.out.println(f+" : "+redisSet.getMembersSet("store").toString());
        Set<String> result = redisSet.getMembersSet("store");
        Assert.assertEquals(new HashSet<String>(Arrays.asList("2")),result);
        redisSet.deleteKey(key,"1","store");

    }

    @Test
    public void testUnion() throws Exception {
        redisSet.add("1","1","2","3");
        redisSet.add("2","2","3","4");
        Set<String> result = redisSet.union("1","2");
        Assert.assertEquals(new HashSet<String>(Arrays.asList("1","2","3","4")), result);
        redisSet.deleteKey("1","2");
    }

    @Test
    public void testUnionStore() throws Exception {
        redisSet.add("1","1","2","3");
        redisSet.add("2","2","3","4");
        Long num = redisSet.unionStore("store","1","2");
        System.out.println(num);
        Set<String> result = redisSet.getMembersSet("store");

        Assert.assertEquals(new HashSet<String>(Arrays.asList("1","2","3","4")), result);
        redisSet.deleteKey("1","2","store");
    }

    @Test
    public void testDiff() throws Exception {
        redisSet.add("1","1","2","3","5");
        redisSet.add("2","2","3","4","6");
        redisSet.add("3","1","4");
        Set<String> result = redisSet.diff("1","2","3");
        Assert.assertEquals(new HashSet<String>(Arrays.asList("5")), result);
        redisSet.deleteKey("1","2");
    }

    @Test
    public void testDiffStore() throws Exception {
        redisSet.add("1","1","2","3","5");
        redisSet.add("2","2","3","4","6");
        redisSet.add("3","1","4");
         Long num = redisSet.diffStore("store","1","2","3");
        System.out.println(num);
        Set<String> result = redisSet.getMembersSet("store");
        Assert.assertEquals(new HashSet<String>(Arrays.asList("5")), result);
        redisSet.deleteKey("store","1","2","3");
    }

    @Test
    public void testMoveMember() throws Exception {
        redisSet.add("1","2","3");
        redisSet.add("2","2");

        Long re = redisSet.moveMember("1", "2", "3");
        System.out.println(re);
        Set<String> results = redisSet.getMembersSet("2");
        Assert.assertEquals(new HashSet<String>(Arrays.asList("2","3")),results);
        redisSet.deleteKey("1","2");

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
