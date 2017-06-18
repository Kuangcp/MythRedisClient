package com.redis;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Created by https://github.com/kuangcp on 17-6-13  下午9:06
 */
// 依赖冲突Spring启动失败 扫描多个包
@ComponentScan({"com.redis","redis.manager"})
@Configuration
public class SpringInit {
    public SpringInit(){
        System.out.println("spring容器启动初始化。。。");
    }
}
