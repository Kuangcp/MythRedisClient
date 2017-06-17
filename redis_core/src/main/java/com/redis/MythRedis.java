package com.redis;

import com.redis.config.PoolManagement;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Created by https://github.com/kuangcp on 17-6-17  下午7:56
 * 主线程
 */
public class MythRedis implements Runnable{
    private boolean runFlag = true;

    public static void init(String id){
        ApplicationContext context;
        PoolManagement management;
        context = new AnnotationConfigApplicationContext(SpringInit.class);
        management = (PoolManagement)context.getBean("poolManagement");
        management.initPool(id);
        Thread thread = new Thread(new MythRedis());
        thread.start();
    }

    public static void main(String[]s){
        init("1024");
    }

    @Override
    public void run() {
        while (runFlag){

        }
    }
}
