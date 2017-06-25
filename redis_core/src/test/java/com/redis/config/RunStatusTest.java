package com.redis.config;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

/**
 * Created by https://github.com/kuangcp on 17-6-9  上午10:24
 */
public class RunStatusTest {
    private static Logger logger = LoggerFactory.getLogger(RunStatus.class);
    // 将配置文件放在用户目录下作为隐藏文件
    @Test
    public void path(){
        String propertyFile = System.getProperty("user.home") + File.separatorChar +".MythRedisClient.properties";
        logger.info(propertyFile);
        assert(propertyFile.endsWith(".MythRedisClient.properties"));
    }
}
