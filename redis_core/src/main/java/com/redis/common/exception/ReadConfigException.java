package com.redis.common.exception;

/**
 * Created by https://github.com/kuangcp on 17-6-12  上午9:54
 */
public class ReadConfigException extends MythException{
    public ReadConfigException() {
    }

    public ReadConfigException(String message) {
        super(message);
    }

    public ReadConfigException(String message, Class location) {
        super(message,location);
    }

    public ReadConfigException(String message, Throwable cause, Class location) {
        super(message, cause, location);
    }
}
