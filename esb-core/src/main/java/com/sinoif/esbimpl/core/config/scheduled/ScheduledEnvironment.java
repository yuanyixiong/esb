package com.sinoif.esbimpl.core.config.scheduled;

import com.alibaba.fastjson.JSON;
import com.sinoif.esb.query.model.dto.EnvironmentDTO;
import com.sinoif.esb.query.model.dto.EnvironmentInformationDTO;
import com.sinoif.esb.query.model.dto.ServiceInformationDTO;
import com.sinoif.esb.utils.OS;
import com.sinoif.esbimpl.core.mongodb.MongoService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * 定时获取环境信息
 */
@Component
public class ScheduledEnvironment {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private MongoService mongoService;

    private final String ENVIRONMENT_INFORMATION = "environmentInformation";

    private final String SERVICE_INFORMATION = "serviceInformation";

    @Value("${dubbo.application.qos.port}")
    private Integer port;

    @Scheduled(cron = "0/10 * * * * ?")
    public void envHeartbeat() {
        logger.info("开始心跳:{}", new Date());
        OS os = new OS();
        EnvironmentDTO environmentInformation = os.environmentInformation();
        String ip = environmentInformation.getJvmDTO().getIp();
        EnvironmentInformationDTO env = new EnvironmentInformationDTO()
                .setId(String.format("%s:%s", ip, port))
                .setTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .setEnvironment(environmentInformation);
        String envJSON = JSON.toJSONString(env);
        mongoService.mongoDbSaveOrUpdate(ENVIRONMENT_INFORMATION, envJSON, "_id", env.getId());
    }

    @Scheduled(cron = "0/5 * * * * ?")
    public void serviceSurviveHeartbeat() {
        logger.info("开始心跳:{}", new Date());
        String ip = null;
        try {
            ip = InetAddress.getLocalHost().getHostAddress();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (StringUtils.isNotEmpty(ip)) {
            String id = String.format("%s:%s", ip, port);
            ServiceInformationDTO service = new ServiceInformationDTO()
                    .setId(id)
                    .setReplyTime(new Date())
                    .setSurvive(true);
            String serviceJSON = JSON.toJSONString(service);
            mongoService.mongoDbSaveOrUpdate(SERVICE_INFORMATION, serviceJSON, "_id", service.getId());
        }
    }
}