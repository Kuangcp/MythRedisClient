package assemble.key;

import com.redis.SpringInit;
import com.redis.assemble.key.RedisKey;
import com.redis.common.Commands;
import com.redis.config.PoolManagement;
import com.redis.test.ActionCore;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Created by https://github.com/kuangcp on 17-6-13  下午10:35
 */
public class KeyTest{
    ApplicationContext context;
    @Before
    public void init(){
        context = new AnnotationConfigApplicationContext(SpringInit.class);
        PoolManagement management = (PoolManagement)context.getBean("poolManagement");
        management.initPool("1022");
        Commands commands = (Commands)context.getBean("commands");
        commands.initPools();
    }

    @Test
    public void run(){
        RedisKey redisKey = (RedisKey)context.getBean("redisKey");
        System.out.println(redisKey.deleteKey("name"));

    }
    @Test
    public void testDump(){
//        RedisKey redisKey = RedisKey.getInstance();
        RedisKey redisKey = (RedisKey)context.getBean("redisKey");
        byte[]s = redisKey.dump("name");
        System.out.println(s.length);
        for(byte sr:s){
            System.out.println(s.toString());
        }
    }
    //    由于依赖无法搞定 只好不用Spring commons-logging和slf4j的冲突 gradle无法排除
    @Test
    public void  testSpring() throws Exception {
        ApplicationContext context = new AnnotationConfigApplicationContext(SpringInit.class);
        ActionCore action = (ActionCore) context.getBean("actionCore");
        System.out.println(action.Redis());
    }
}
