package com.qiutian.middleware;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;

/**
 * @author qiutian
 */
@Slf4j
public class Application {
    public static void main(String[] args){
        SpringApplication.run(javafx.application.Application.class,args);
        log.info("应用启动成功");
    }
}
