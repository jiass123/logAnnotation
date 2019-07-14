package com.jiass.controller;

import com.jiass.annotation.LogRecord;
import org.apache.tomcat.jni.Time;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

@RestController
public class LogController {

    @LogRecord
    @RequestMapping("/test")
    public void test1() throws InterruptedException {
        System.out.println("执行....");
//        TimeUnit.SECONDS.sleep(3);
    }

    @LogRecord
    @RequestMapping("/test2")
    public void test2() throws InterruptedException {
        System.out.println("执行....");
        int i = 1/0;
//        TimeUnit.SECONDS.sleep(3);
    }
}
