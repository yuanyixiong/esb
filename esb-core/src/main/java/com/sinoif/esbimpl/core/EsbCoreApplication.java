/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.sinoif.esbimpl.core;

import org.apache.log4j.FileAppender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.File;

/**
 * esb core模块的启动类
 */
public class EsbCoreApplication {
    private final static Logger logger = LoggerFactory.getLogger(EsbCoreApplication.class);

    public static void main(String[] args) throws Exception {

        String path = new File("").getAbsolutePath();

        FileAppender appender = (FileAppender) org.apache.log4j.Logger.getRootLogger().getAppender("logFile");
        appender.setFile(path + File.separator + "esb-core.log");

        logger.info("esb-core 开始启动");
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring/esb-core-provider.xml");
        EsbCoreRemoteServiceImpl esbCoreService = context.getBean("esbCoreRemoteService", EsbCoreRemoteServiceImpl.class);
        context.start();
        System.in.read();
//        esbCoreService.stopContainer();
    }
}