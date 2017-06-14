package com.redis.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Created by https://github.com/kuangcp on 17-6-8  下午8:41
 */
@Component
public class ActionCore {
    private Logger logger = LoggerFactory.getLogger(ActionCore.class);
    public String Redis(){
        logger.info("3434");
        logger.error("7895403");
        return "Send Redis from redis_core";
    }
}
