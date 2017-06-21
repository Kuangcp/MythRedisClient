package com.redis.config;

import com.redis.SpringInit;
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

import java.util.concurrent.ConcurrentMap;

/**
 * Created by https://github.com/kuangcp on 17-6-21  下午4:00
 */
public class PoolManagementTest {
    @Mock
    ConcurrentMap<String, RedisPools> poolMap;
    @Mock
    Logger logger;
    @Mock
    MythProperties configFile;
    @InjectMocks
    PoolManagement poolManagement;
    RedisPoolProperty property;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        // 初始化Spring环境
        ApplicationContext context = new AnnotationConfigApplicationContext(SpringInit.class);
        poolManagement = (PoolManagement) context.getBean("poolManagement");
        poolManagement.setCurrentPoolId("1025");

        property = new RedisPoolProperty();
        property.setHost("120.25.203.47");
        property.setMaxActive(400);
        property.setMaxIdle(100);
        property.setMaxWaitMills(10000);//等待连接超时时间
        property.setTestOnBorrow(false);// 如果设置密码就必须是false
        property.setName("myth");
        property.setPort(6381);
        property.setPassword("myth");
        property.setTimeout(600);//读取超时时间
    }

    // 测试得到可用的连接池
    @Test
    public void testGetRedisPool() throws Exception {
        RedisPools result = poolManagement.getRedisPool();
        Jedis jedis = result.getJedis();
        jedis.set("names","testGetRedisPool");
        Assert.assertEquals("testGetRedisPool",jedis.get("names"));
    }

    // 使用属性对象立即创建并使用，设计为了 面板上的那个，测试 连接 按钮
    @Test
    public void testCreateRedisPoolAndConnection() throws Exception {
        RedisPools result = poolManagement.createRedisPoolAndConnection(property);
        Jedis jedis = result.getJedis();
        jedis.set("names","testCreateRedisPoolAndConnection");
        Assert.assertEquals("testCreateRedisPoolAndConnection",jedis.get("names"));
    }

    @Test
    public void testCreateRedisPool() throws Exception {
        // 根据上面的配置创建配置文件
        String result = poolManagement.createRedisPool(property);
        RedisPoolProperty getObject = RedisPoolProperty.initByIdFromConfig(result);
        Assert.assertEquals(getObject.toString(), property.toString());
    }

    // 进行切换连接池
    @Test
    public void testSwitchPool() throws Exception {
        boolean result = poolManagement.switchPool("PoolId");
        Assert.assertEquals(true, result);
    }

    // 测试删除，按初始环境的测试来说，这里应该是空的，完工后，还要改代码
    @Test
    public void testDeleteRedisPool() throws Exception {
        String id = null;
        String result = poolManagement.deleteRedisPool(id);
        Assert.assertEquals(id, result);
    }

    @Test
    public void testClearAllPools() throws Exception {
        boolean result = poolManagement.clearAllPools();
        Assert.assertEquals(true, result);
    }

    // 测试删除，没有异常即可
    @Test
    public void testDestroyRedisPool() throws Exception {
        boolean result = poolManagement.destroyRedisPool("1292");
        Assert.assertEquals(false, result);
    }
}
