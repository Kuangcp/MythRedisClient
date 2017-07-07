package com.redis.config;

import java.io.File;

/**
 * Created by https://github.com/kuangcp on 17-6-9  下午7:44
 * 配置文件的一般配置属性,声明为接口的话，就会默认加上puvlic static final的修饰
 */
public interface Configs {

    // 配置文件目录
    String PROPERTY_FILE = System.getProperty("user.home") + File.separatorChar +".MythRedisClient.properties";

    // 配置文件的基本属性
    int START_ID=1000;// 起始id
    String MAX_ACTIVE = "maxActive";
    String MAX_IDLE = "maxIdle";
    String MAX_WAIT_MILLIS = "maxWaitMills";
    String TEST_ON_BORROW = "testOnBorrow";
    String PASSWORD = "password";
    String TIMEOUT = "timeout";
    String PORT = "port";
    String HOST = "host";
    String NAME = "name";
    String SEPARATE=".";//分隔id和属性
    String POOL_ID = "poolId";//记录id
    String MAX_POOL_ID = "maxPoolId";//记录存放的最大id，为了新增需要

    // 超时的规范数据，单位 s
    int EXPIRE_HOUR = 60*60;          //一小时
    int EXPIRE_DAY = 60*60*24;        //一天
    int EXPIRE_MONTH = 60*60*24*30;   //一个月

    String[] REDIS_INFO = {"Server","Clients","Memory","Persistence","Stats","Replication","CPU","Cluster","Keyspace"};
    String REDIS_INFO_TITLE="#";
    String REDIS_INFO_END="\n";
    String REDIS_INFO_SEPARATE=":";
}
