package com.redis.common.exception;

/**
 * Created by https://github.com/kuangcp on 17-6-18  上午9:28
 */
public class TypeErrorException extends MythException{
    public TypeErrorException() {
        super();
    }

    public TypeErrorException(String message) {
        super(message);
    }

    public TypeErrorException(String message, Class location) {
        super(message, location);
    }

    public TypeErrorException(String message, Throwable cause, Class location) {
        super(message, cause, location);

    }
}
