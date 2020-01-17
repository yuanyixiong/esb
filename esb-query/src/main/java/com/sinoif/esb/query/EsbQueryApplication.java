package com.sinoif.esb.query;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

/**
 * esb-query 模块启动主类
 */
public class EsbQueryApplication {
    public static void main(String[] args) throws IOException {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring/query-provider.xml");
        context.start();
        System.in.read();
    }
}
