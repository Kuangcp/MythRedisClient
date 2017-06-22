package com.redis.client;

import com.redis.SpringInit;
import com.redis.config.PoolManagement;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Created by https://github.com/kuangcp on 17-6-18  下午5:35
 */
public class MainTest {
    public static void main(String[]s){
//        MythRedis.main(s);
        ApplicationContext context;
        PoolManagement management;
        context = new AnnotationConfigApplicationContext(SpringInit.class);
        management = (PoolManagement)context.getBean("poolManagement");
        management.setCurrentPoolId("1025");
    }
}
