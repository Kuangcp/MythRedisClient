package com.redis.common.exception;

/**
 * Created by https://github.com/kuangcp on 17-6-22  上午10:20
 */
public class ActionErrorException extends MythException {
    public ActionErrorException() {
    }

    public ActionErrorException(String message) {
        super(message);
    }

    public ActionErrorException(String message, Class location) {
        super(message, location);
    }

    public ActionErrorException(String message, Throwable cause, Class location) {
        super(message, cause, location);
    }
}
