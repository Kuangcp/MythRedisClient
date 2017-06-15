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
    }

    // 测试连接可用 
    @Test
    public void run(){
        RedisKey redisKey = (RedisKey)context.getBean("redisKey");
        redisKey.set("name2","34");
        System.out.println("设置："+redisKey.get("name2"));
        System.out.println("删除："+redisKey.deleteKey("name"));
        System.out.println("删除后"+redisKey.get("name2"));

    }


    @Test
    public void testDump(){
        RedisKey redisKey = (RedisKey)context.getBean("redisKey");
        byte[]s = redisKey.dump("name");
        System.out.println(s.length);
        for(byte sr:s){
            System.out.println(s.toString()+"8989898\n");
        }
    }
    @Test
    public void  testSpring() throws Exception {
        ApplicationContext context = new AnnotationConfigApplicationContext(SpringInit.class);
        ActionCore action = (ActionCore) context.getBean("actionCore");
        System.out.println(action.Redis());
    }
}
