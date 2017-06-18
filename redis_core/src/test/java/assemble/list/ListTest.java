package assemble.list;

import com.redis.SpringInit;
import com.redis.assemble.list.RedisList;
import com.redis.config.PoolManagement;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;

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

    public void  showList(String key){
        List<String> lists = redisList.range(key,0,-1);
        for(String s:lists){
            System.out.println(s);
        }
    }
    @Test
    public void testPush(){
        String key = "lists";
        long result = redisList.rPush(key,"23","123");
        System.out.println("尾插长度："+result);
        result = redisList.lPush(key,"1");
        System.out.println("头插长度："+result);
        showList(key);
    }
    @Test
    public void testPushX(){
        String key = "pushx";
        long result = redisList.rPushX(key,"2323");
        System.out.println("尾插长度："+result);
        result = redisList.lPushX(key,"1");
        System.out.println("头插长度："+result);
        showList(key);
    }
    @Test
    public void testPop(){
        redisList.lPop("");
    }
}
