package com.sinoif.esbimpl.port.zookeeper;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * zookeeper连接配置bean
 */
@Configuration
@PropertySource("classpath:zookeeper.properties")
public class ZookeeperConfig {
    private final Logger logger = LogManager.getLogger(this.getClass());

    @Value("${zk.connect}")
    String zkConnect;

    /**
     * CuratorFramework配置bean
     *
     * @return
     */
    @Bean("curatorClient")
    public CuratorFramework getCuratorClient() {
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
        CuratorFramework client =
                CuratorFrameworkFactory.builder()
                        .connectString(zkConnect)
                        .sessionTimeoutMs(5000)
                        .connectionTimeoutMs(5000)
                        .retryPolicy(retryPolicy)
                        .build();
        client.start();
        return client;
    }
}

