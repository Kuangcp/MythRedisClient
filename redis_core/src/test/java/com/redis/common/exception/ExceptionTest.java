package com.redis.common.exception;

import org.junit.Test;

/**
 * Created by https://github.com/kuangcp on 17-6-13  下午8:36
 * 测试Exception 日志记录
 */
public class ExceptionTest {

    // 测试自定义异常类的机制
    @Test
    public void run(){
        System.out.println("");
        Exception e = new RedisConnectionException(ExceptionInfo.OPEN_CONFIG_FAILED,ExceptionTest.class);
        try {
            throw new ReadConfigException(ExceptionInfo.GET_POOL_BY_ID_FAILED,e,ExceptionTest.class);
        } catch (ReadConfigException e1) {
            e1.printStackTrace();
        }
    }
}
