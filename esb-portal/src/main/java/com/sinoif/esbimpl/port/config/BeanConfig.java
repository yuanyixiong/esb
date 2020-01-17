package com.sinoif.esbimpl.port.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Beans Java配置类，配置模块需要使用到的Bean
 */
@Configuration
public class BeanConfig {
    @Bean
    ObjectMapper getObjectMapper(){
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper;
    }
}
