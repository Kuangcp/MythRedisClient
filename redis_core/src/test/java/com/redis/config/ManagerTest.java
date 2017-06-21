package com.redis.config;

import com.redis.SpringInit;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import redis.clients.jedis.Jedis;

import java.io.IOException;

/**
 * Created by https://github.com/kuangcp on 17-6-14  下午9:41
 * 使用Spring后的测试类 测试通过
 */
public class ManagerTest {
    private PoolManagement management;
    @Before
    public void init(){
        ApplicationContext context = new AnnotationConfigApplicationContext(SpringInit.class);
        management = (PoolManagement) context.getBean("poolManagement");
    }
    @Test
    public void getPool(){
        try {
            RedisPools pools  = management.getRedisPool("1025");
            Jedis jedis = pools.getJedis();
            jedis.set("name","myth");
            String name = jedis.get("name");
            assert("myth".equals(name));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Test
    public void createPool(){
        // 设置8个属性，id自动生成递增
        RedisPoolProperty property = new RedisPoolProperty();
        property.setHost("120.25.203.47");
        property.setMaxActive(400);
        property.setMaxIdle(100);
        property.setMaxWaitMills(10000);//等待连接超时时间
        property.setTestOnBorrow(false);// 如果设置密码就必须是false
        property.setName("myth");
        property.setPort(6381);
        property.setPassword("myth");
        property.setTimeout(600);//读取超时时间

        RedisPools pool;
        try {
            pool = management.getRedisPool(management.createRedisPool(property));
            System.out.println("连接池实例"+pool);
            Jedis jedis = pool.getJedis();
            jedis.set("name","myth");
            String name = jedis.get("name");
            assert(name!=null);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    @Test
    public void deleteTest(){
        String key = "1028";
        try {
            Assert.assertEquals(key,management.deleteRedisPool(key));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Assert.assertEquals(true,management.clearAllPools());
    }
}
