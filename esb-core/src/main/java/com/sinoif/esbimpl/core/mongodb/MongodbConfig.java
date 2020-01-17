package com.sinoif.esbimpl.core.mongodb;

import com.mongodb.MongoClientSettings;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * mongodb 配置类
 */
@Configuration
@PropertySource("classpath:mongo.properties")
public class MongodbConfig {

    // 数据库名称
    @Value("${mongo_db}")
    String dbName;

    // 用户名
    @Value("${mongo_username}")
    String username;

    // 密码
    @Value("${mongo_password}")
    String password;

    // mongodb集群
    @Value("${mongo_host}")
    String host;

    // mongodb端口
    @Value("${mongo_port}")
    int port;

    @Bean
    MongoDatabase getMongoClient() {
        return getMc().getDatabase("esb");
    }

    @Bean
    MongoClient getMc() {
        MongoClient client = MongoClients.create(getMongoClientSettings());
        return client;
    }

    /**
     * 获取mongodb的配置属性对象
     *
     * @return MongoClientSettings
     */
    @Bean
    MongoClientSettings getMongoClientSettings() {
        String[] nodeIps = host.split(",");
        List<ServerAddress>  mongoNodes = new ArrayList<>();
        for(String ip:nodeIps){
            mongoNodes.add(new ServerAddress(ip,port));
        }
        MongoCredential credential = MongoCredential.createCredential(username, "admin", password.toCharArray());
        MongoClientSettings settings = MongoClientSettings.builder()
                .credential(credential)
                .applyToClusterSettings(builder ->builder.hosts(mongoNodes))
                .applyToConnectionPoolSettings(builder ->builder.maxSize(100))
                .applyToSocketSettings(builder -> builder.connectTimeout(30, TimeUnit.SECONDS))
                .applyToConnectionPoolSettings(builder -> builder.maxConnectionIdleTime(30, TimeUnit.SECONDS)
                .maxConnectionLifeTime(10, TimeUnit.SECONDS))
                .build();
        return settings;
    }

    /**
     * mongodb templates Bean
     *
     * @return MongoTemplate
     */
    @Bean
    MongoTemplate getMongoTemplate(){
        return new MongoTemplate(getMc(),"esb");
    }

}
