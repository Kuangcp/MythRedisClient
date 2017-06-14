package config;

import com.redis.SpringInit;
import com.redis.config.PoolManagement;
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
public class ManagermentTest {
    ApplicationContext context;
    @Before
    public void init(){
        context = new AnnotationConfigApplicationContext(SpringInit.class);
    }
    @Test
    public void getPool(){
        PoolManagement management = (PoolManagement) context.getBean("poolManagement");
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
}
