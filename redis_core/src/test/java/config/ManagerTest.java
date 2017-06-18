package config;

import com.redis.SpringInit;
import com.redis.config.PoolManagement;
import com.redis.config.RedisPoolProperty;
import com.redis.config.RedisPools;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import redis.clients.jedis.Jedis;

/**
 * Created by https://github.com/kuangcp on 17-6-14  下午9:41
 * 使用Spring后的测试类
 */
public class ManagerTest {
    private ApplicationContext context;
    private PoolManagement management;
    @Before
    public void init(){
        context = new AnnotationConfigApplicationContext(SpringInit.class);
        management = (PoolManagement) context.getBean("poolManagement");
    }
    @Test
    public void getPool(){
        try {
            RedisPools pools  = management.getRedisPool("1022");
            Jedis jedis = pools.getJedis();
            jedis.set("name","myth");
            String name = jedis.get("name");
            System.out.println("结果："+name);
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

        RedisPools pool = null;
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
}
