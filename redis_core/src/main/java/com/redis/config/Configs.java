package com.redis.config;

import java.io.File;

/**
 * Created by https://github.com/kuangcp on 17-6-9  下午7:44
 * 配置文件的一般配置属性
 */
public class Configs {

    // 配置文件目录
    public static final String propertyFile = System.getProperty("user.home") + File.separatorChar +".MythRedisClient.properties";
    // 起始id
    public static final int START_ID=1000;
    public static final String MAX_ACTIVE = "maxActive";
    public static final String MAX_IDLE = "maxIdle";
    public static final String MAX_WAIT_MILLIS = "maxWaitMills";
    public static final String TEST_ON_BORROW = "testOnBorrow";
    public static final String PASSWORD = "password";
    public static final String TIMEOUT = "timeout";
    public static final String PORT = "port";
    public static final String HOST = "host";
    public static final String NAME = "name";

    public static final String SEPARATE=".";//分隔id和属性
    public static final String POOL_ID = "poolId";//记录id
    public static final String MAX_POOL_ID = "maxPoolId";//记录存放的最大id，为了新增需要

}
