package assemble.key;

import com.redis.SpringInit;
import com.redis.assemble.key.RedisKey;
import com.redis.common.exception.TypeErrorException;
import com.redis.config.PoolManagement;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;

/**
 * Created by https://github.com/kuangcp on 17-6-13  下午10:35
 */
public class KeyTest{

    private ApplicationContext context;
    private PoolManagement management;
    private RedisKey redisKey;

    @Before
    public void init(){
        context = new AnnotationConfigApplicationContext(SpringInit.class);
        management = (PoolManagement)context.getBean("poolManagement");
        management.initPool("1025");
        redisKey = (RedisKey)context.getBean("redisKey");
//        Commands commands = (Commands)context.getBean("commands");
    }

    // 测试连接可用 
    @Test
    public void run(){
        redisKey.set("name2","34");
        System.out.println("设置："+redisKey.get("name2"));
        System.out.println("删除数量："+redisKey.deleteKey("name2"));
        System.out.println("删除后"+redisKey.get("name2"));

    }
    @Test
    public void getHash(){
        String re = redisKey.type("n");
        System.out.println(re);
        redisKey.get("n3");

    }


    @Test
    public void testDump(){
        byte[]s = redisKey.dump("name");
        System.out.println(s.length);
        for(byte sr:s){
            System.out.println(s.toString()+"\n");
        }

    }

    @Test
    public void testExpire(){
        redisKey.setDb(2);
        long re;
//        redisKey.set("name","myths");
         re = redisKey.getJedis().expire("name",3);
//         re = redisKey.getJedis().persist("name");
         re = redisKey.expire("name",6);
        System.out.println("结果："+re);
//        redisKey.setExpire("name223",9,"erer");
    }
    @Test
    public void testEncoding(){
        String encode = redisKey.getEncoding("name");
        System.out.println(encode);

    }

    @Test
    public void testMergeGet(){
        List<String> lists =  redisKey.getMultiKeys("1","5");
        for(String key:lists){
            System.out.println(key);
        }
        String res = redisKey.setMultiKey("fg","ty");
        System.out.println(res);

    }
    @Test
    public void testIncrease(){
        long result = 0;
        try {
            result = redisKey.increaseKey("1");
            System.out.println(result);
            result = redisKey.decreaseKey("1");
            System.out.println(result);
        } catch (TypeErrorException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void testAppend(){
        long re = redisKey.append("name232323","23");
        System.out.println(re);
    }

    @Test
    public void testFlush(){
        String result = redisKey.flushAll();
        System.out.println(result);
        String result2 = redisKey.flushDB(2);
        System.out.println(result2);
    }

    // 测试Spring的装载
//    @Test
//    public void  testSpring() throws Exception {
//        ApplicationContext context = new AnnotationConfigApplicationContext(SpringInit.class);
//        ActionCore action = (ActionCore) context.getBean("actionCore");
//        System.out.println(action.Redis());
//    }
}
