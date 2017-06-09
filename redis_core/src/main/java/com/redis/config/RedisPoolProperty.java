package com.redis.config;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by https://github.com/kuangcp on 17-6-9  下午9:09
 * RedisPool 的必要的非必要的所有连接属性
 */
@Getter
@Setter
public class RedisPoolProperty {
    private int maxActive=400;
    private int maxIdle=300;
    private int maxWaitMills=1000000;
    private boolean testOnBorrow=true;
    private int port=6379;
    private int timeout=60;
    private String host="127.0.0.1";
    private String name;
    private String poolId;
}
