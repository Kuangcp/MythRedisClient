package com.redis.common.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by https://github.com/kuangcp on 17-6-12  上午9:57
 */
public class MythException extends Exception{
    private Logger logger;
    public MythException() {
    }

    public MythException(String message) {
        super(message);
    }

    public MythException(String message, Class location) {
        super(message);
        logger = LoggerFactory.getLogger(location);
        logger.error("<"+this.getClass().getSimpleName()+">:"+message);
    }
    public MythException(String message,Throwable cause, Class location) {
        super(message,cause);
        logger = LoggerFactory.getLogger(location);
        logger.error("<"+this.getClass().getSimpleName()+">:"+message);
    }
}
