package assemble.list;

import com.redis.SpringInit;
import com.redis.assemble.list.RedisList;
import com.redis.config.PoolManagement;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Created by https://github.com/kuangcp on 17-6-18  上午10:30
 */
public class ListTest {
    ApplicationContext context;
    PoolManagement management;
    RedisList redisList;

    @Before
    public void init(){
        context = new AnnotationConfigApplicationContext(SpringInit.class);
        management = (PoolManagement)context.getBean("poolManagement");
        management.initPool("1025");
        redisList = (RedisList)context.getBean("redisList");
//        Commands commands = (Commands)context.getBean("commands");
    }
    @Test
    public void  run(){

    }
}
