package com.redis.assemble.set.sort;

import com.redis.common.exception.ReadConfigException;
import com.redis.config.PoolManagement;
import com.redis.config.PropertyFile;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;
import redis.clients.jedis.Tuple;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by https://github.com/kuangcp on 17-6-28  下午2:59
 * 全部测试完成
 * @auther Kuangcp
 */
public class RedisSortSetTest {
//    @Mock
//    PoolManagement management;
//    @Mock
//    RedisPools pools;
//    @Mock
//    Jedis jedis;
//    @Mock
//    Logger logger;
//    @InjectMocks
    RedisSortSet redisSortSet;
    String key = "zset";

    @Before
    public void setUp() throws ReadConfigException {
        MockitoAnnotations.initMocks(this);

        // 装载Spring环境
        PoolManagement management = PoolManagement.getInstance();
        management.setCurrentPoolId(PropertyFile.getMaxId()+"");
        redisSortSet = RedisSortSet.getInstance();

    }

    @Test
    public void testAdd() throws Exception {
        redisSortSet.save(key,3.3,"one");
        redisSortSet.save(key,5.3,"two");
        Long result = redisSortSet.save(key, 2.12, "member");
        show();
        Assert.assertEquals((Long)1L, result);

    }

    @Test
    public void testIndex() throws Exception {
        // 证明了 排序是按分数来的，分数相同时，先插入的在前
        redisSortSet.save(key, 2.12, "members");
        redisSortSet.save(key, 2.52, "member");
        redisSortSet.save(key, 2.02, "member");
        Long result = redisSortSet.index(key, "member");
        show();
        Assert.assertEquals((Long)0L, result);

    }

    @Test
    public void testScore() throws Exception {
        redisSortSet.save(key, 2.52, "member");
        Double result = redisSortSet.score(key, "member");
        show();
        Assert.assertEquals((Double)2.52, result);
    }

    @Test
    public void testRank() throws Exception {
        redisSortSet.save(key, 2.52, "member");
        redisSortSet.save(key, 2.52, "members");
        Long result = redisSortSet.rank(key, "member");
        Assert.assertEquals((Long)1L, result);
    }

    @Test
    public void testRangeByIndex() throws Exception {
        redisSortSet.save(key,2.3,"a");
        redisSortSet.save(key,2.3,"b");
        redisSortSet.save(key,2.1,"c");
        Set<String> result = redisSortSet.rangeByIndex(key, 0L, 1L);
        for(String s:result){
            System.out.println(s);
        }
        show();
        Assert.assertEquals(new HashSet<String>(Arrays.asList("b","a")), result);
    }

    @Test
    public void testRangeByScore() throws Exception {
        redisSortSet.save(key,2.3,"a");
        redisSortSet.save(key,2.3,"b");
        redisSortSet.save(key,2.1,"c");
        Set<String> result = redisSortSet.rangeByScore(key, 0d, 2.2d);
        for(String s:result){
            System.out.println(s);
        }
        show();
        Assert.assertEquals(new HashSet<String>(Arrays.asList("b","a","c")), result);
    }

    @Test
    public void testInterStore() throws Exception {
        redisSortSet.save(key,1.1,"a");
        redisSortSet.save(key,1.1,"b");
        redisSortSet.save("key1",1.1,"b");
        redisSortSet.save("key1",1.1,"a");
        redisSortSet.save("key2",1.1,"a");
        redisSortSet.save(key,1.1,"t");

        Long result = redisSortSet.interStore("desKey", key,"key1");
        show(key,"key1","desKey","key2");
        Assert.assertEquals((Long)2L, result);
    }

    @Test
    public void testIncrease() throws Exception {
        redisSortSet.save(key,1.1,"member");
        Double result = redisSortSet.increase(key, 1.1, "member");
        show();
        Assert.assertEquals((Double)2.2d, result);
    }

    @Test
    public void testRemove() throws Exception {
        redisSortSet.save(key,1.1,"value");

        Long result = redisSortSet.remove(key, "value");
        show();
        Assert.assertEquals((Long)1L, result);
    }

    @Test
    public void testRemoveByRank() throws Exception {
        redisSortSet.save(key,2.3,"a");
        redisSortSet.save(key,2.3,"b");
        redisSortSet.save(key,2.1,"c");
        redisSortSet.save(key,2.3,"d");
        redisSortSet.save(key,2.3,"r");
        redisSortSet.save(key,2.1,"u");
        Long result = redisSortSet.removeByRank(key, 0L, 1L);
        show();
        Assert.assertEquals((Long)2L, result);
    }

    @Test
    public void testRemoveByScore() throws Exception {
        redisSortSet.save(key,2.3,"a");
        redisSortSet.save(key,2.3,"b");
        redisSortSet.save(key,2.1,"c");
        redisSortSet.save(key,1.9,"d");
        redisSortSet.save(key,2.0,"r");
        redisSortSet.save(key,2.1,"u");
        Long result = redisSortSet.removeByScore(key, 1.9d, 2.1d);
        Assert.assertEquals((Long)4L, result);
    }

    @Test
    public void testSize() throws Exception {
        redisSortSet.save(key,2.3,"a");
        redisSortSet.save(key,2.3,"b");
        redisSortSet.save(key,2.1,"c");
        Long result = redisSortSet.size(key);
        show();
        Assert.assertEquals((Long)3L, result);
    }

    @Test
    public void testCount() throws Exception {
        redisSortSet.save(key,2.3,"a");
        redisSortSet.save(key,2.3,"b");
        redisSortSet.save(key,2.1,"c");
        Long result = redisSortSet.count(key, 2.1,2.2);
        show();
        Assert.assertEquals((Long)1L, result);
    }

    private void show(String... key){
        for(String k:key) {
            this.key = k;
            show();
        }
    }
    private void show(){
        Set<Tuple> sets = redisSortSet.getMemberSetWithScore(key);
        for(Tuple k:sets){
            System.out.println(k.getElement()+" : "+k.getScore());
        }
        redisSortSet.deleteKey(key);
    }
    @Test
    public void test(){
        redisSortSet.save(key,2.33,"ad");
        redisSortSet.save(key,434.2,"ads");
        redisSortSet.save(key,43.1,"a");
        Set<String> sets = redisSortSet.rangeByLex(key,"+","-");
        for(String l:sets){
            System.out.println(l);
        }
        show();
    }

}
