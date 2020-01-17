package com.sinoif.esbimpl.port.resource;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoDatabase;
import com.sinoif.esb.port.remote.PortalHeartbeatRemoteService;
import com.sinoif.esb.query.model.dto.ServiceInformationDTO;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.List;

/**
 * @author 袁毅雄
 * @description 远程实现-ESB portal 心跳服务
 * @date 2019/11/1
 */
public class PortalHeartbeatRemoteResopurce implements PortalHeartbeatRemoteService {

    private final String SERVICE_INFORMATION = "serviceInformation";

    /**
     * 应答时间
     */
    private final Integer reply = 5;

    @Autowired
    private MongoDatabase mongoDatabase;

    @Override
    public boolean heartbeat() {
        return true;
    }

    @Override
    public List<ServiceInformationDTO> serviceSurviveState() {
        FindIterable<Document> findIterable = mongoDatabase.getCollection(SERVICE_INFORMATION).find();
        List<ServiceInformationDTO> services = Lists.newArrayList();
        for (Document document : findIterable) {
            String json = JSONObject.toJSONString(document);
            services.add(JSONObject.parseObject(json, ServiceInformationDTO.class));
        }
        Long newReply = services.stream().map(ServiceInformationDTO::getReplyTime).mapToLong(Date::getTime).summaryStatistics().getMax();
        LocalDateTime newReplyTime = Instant.ofEpochMilli(newReply).atZone(ZoneOffset.ofHours(8)).toLocalDateTime();
        LocalDateTime limitReplyTime = newReplyTime.plusSeconds(reply * -2);
        services.forEach(service -> service.setSurvive(Instant.ofEpochMilli(service.getReplyTime().getTime()).atZone(ZoneOffset.ofHours(8)).toLocalDateTime().isAfter(limitReplyTime)));
        return services;
    }
}
