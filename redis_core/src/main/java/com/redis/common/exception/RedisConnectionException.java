package com.redis.common.exception;

/**
 * Created by https://github.com/kuangcp on 17-6-9  下午9:17
 * 在得到异常的时候就加入日志
 */
public class RedisConnectionException extends MythException{
    public RedisConnectionException(){}
    public RedisConnectionException(String msg){
        super(msg);
    }
    public RedisConnectionException(String msg,Class location){
        super(msg,location);
    }

    public RedisConnectionException(String message, Throwable cause, Class location) {
        super(message, cause, location);
    }

}
