package com.github.kuangcp.controller;

import com.redis.test.ActionCore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by https://github.com/kuangcp on 17-6-8  下午7:43
 */
@ComponentScan("com.redis.test")
@RestController
@RequestMapping("/r")
public class RunConfig {

    @Autowired
    ActionCore core;
    @RequestMapping("/r")
    public String run(){
        return "89";
    }

    @RequestMapping("/o")
    public String redis(){
        return core.Redis();
    }
}
