package com.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Created by https://github.com/kuangcp on 17-6-13  下午9:06
 */
// 依赖冲突Spring启动失败 扫描多个包
@ComponentScan({"com.redis","redis.manager"})
@Configuration
public class SpringInit {
    private Logger logger = LoggerFactory.getLogger(SpringInit.class);
    public SpringInit(){
        logger.info("Spring配置类 构造器初始化");
    }
    public void test(){
        logger.info("test");
    }
}
