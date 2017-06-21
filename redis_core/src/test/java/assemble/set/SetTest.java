package assemble.set;

import com.redis.SpringInit;
import com.redis.assemble.set.RedisSet;
import com.redis.config.PoolManagement;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Created by https://github.com/kuangcp on 17-6-20  上午10:48
 */
public class SetTest {
    RedisSet redisSet;
    String key = "sets";
    @Before
    public void init(){
        ApplicationContext context = new AnnotationConfigApplicationContext(SpringInit.class);
        PoolManagement management = (PoolManagement) context.getBean("poolManagement");
        management.setCurrentPoolId("1025");
        redisSet = (RedisSet) context.getBean("redisSet");
    }
    @Test
    public void addTest(){

        long d = redisSet.add(key,"1","3");
        System.out.println("return : "+d);
    }
}
