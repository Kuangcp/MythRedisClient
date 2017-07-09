package com.redis.assemble.hash;

import com.redis.common.exception.ReadConfigException;
import com.redis.config.PoolManagement;
import com.redis.config.PropertyFile;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import java.util.*;

/**
 * Created by https://github.com/kuangcp on 17-6-29  下午4:09
 * 测试完成
 * @author Kuangcp
 */
public class RedisHashTest {
//    @Mock
//    PoolManagement management;
//    @Mock
//    RedisPools pools;
//    @Mock
//    Jedis jedis;
//    @Mock
//    Logger logger;
//    @InjectMocks
    RedisHash redisHash;
    String key = "hash";

    @Before
    public void setUp() throws ReadConfigException {
        MockitoAnnotations.initMocks(this);

        PoolManagement management = PoolManagement.getInstance();
        management.setCurrentPoolId(PropertyFile.getMaxId()+"");
        redisHash = RedisHash.getInstance();
    }

    @Test
    public void testSave() throws Exception {
        redisHash.save(key,"member","value");
        Long result = redisHash.save(key, "member", "value2");
        show();
        Assert.assertEquals((Long)0L, result);
    }

    @Test
    public void testSave2() throws Exception {
        redisHash.save(key,"String","value");
        String result = redisHash.save(key, new HashMap<String, String>() {{
            put("String", "String");
            put("String1","12");
        }});
        show();
        Assert.assertEquals("OK", result);
    }

    @Test
    public void testSaveWhenNotExist() throws Exception {
        redisHash.save(key,"member","value");
        Long result = redisHash.saveWhenNotExist(key, "member", "value2");
        show();
        Assert.assertEquals((Long)0L, result);
    }

    @Test
    public void testGet() throws Exception {
        redisHash.save(key,"member","value");
        String result = redisHash.get(key, "member");
        show();
        Assert.assertEquals("value", result);
    }

    @Test
    public void testGet2() throws Exception {
        redisHash.save(key,"member","value");
        redisHash.save(key,"s","value");
        List<String> result = redisHash.get(key, "member", "s");
        show();
        Assert.assertEquals(Arrays.<String>asList("value","value"), result);
    }

    @Test
    public void testGetMembers() throws Exception {
        redisHash.save(key,"member","value");
        redisHash.save(key,"member2","value");
        Set<String> result = redisHash.getMembers(key);
        show();
        Assert.assertEquals(new HashSet(Arrays.asList("member","member2")), result);
    }

    @Test
    public void testGetValues() throws Exception {
        redisHash.save(key,"member","value");
        redisHash.save(key,"member2","value");
        List<String> result = redisHash.getValues(key);
        show();
        Assert.assertEquals(Arrays.asList("value","value"), result);
    }

    @Test
    public void testIncrease() throws Exception {
        redisHash.save(key,"member","34");
        Long result = redisHash.increase(key, "member", 56L);
        show();
        Assert.assertEquals((Long)90L, result);
    }

    @Test
    public void testIncreaseByFloat() throws Exception {
        redisHash.save(key,"member","1");
        Double result = redisHash.increaseByFloat(key, "member", 22d);
        Assert.assertEquals((Double) 23.0, result);
    }

    @Test
    public void testLength() throws Exception {
        redisHash.save(key,"member","23.2");
        redisHash.save(key,"member1","23.2");
        redisHash.save(key,"member2","23.2");
        Long result = redisHash.length(key);
        show();
        Assert.assertEquals((Long)3L, result);
    }

    @Test
    public void testRemove() throws Exception {
        redisHash.save(key,"member","23.2");
        Long result = redisHash.remove(key, "member");
        show();
        Assert.assertEquals((Long)1L, result);
    }

    public void show() throws Exception {
        Map<String,String> sets = redisHash.getAllMap(key);
        for(String s:sets.keySet()){
            System.out.println(s+" -- "+sets.get(s));
        }
        redisHash.deleteKey(key);

    }

}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme