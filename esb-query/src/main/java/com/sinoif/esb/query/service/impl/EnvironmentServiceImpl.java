package com.sinoif.esb.query.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoDatabase;
import com.sinoif.esb.query.model.dto.*;
import com.sinoif.esb.query.service.EnvironmentService;
import org.apache.commons.collections.CollectionUtils;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.util.function.Tuple2;
import reactor.util.function.Tuples;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author 袁毅雄
 * @description 环境信息的实现
 * @date 2019/11/1
 */
@Service
public class EnvironmentServiceImpl implements EnvironmentService {

    @Autowired
    private MongoDatabase mongoDatabase;

    private final String ENVIRONMENT_INFORMATION = "environmentInformation";

    /**
     * 环境信息
     *
     * @return
     */
    @Override
    public List<EnvironmentInformationDTO> environmentInformation() {
        FindIterable<Document> findIterable = mongoDatabase.getCollection(ENVIRONMENT_INFORMATION).find();
        List<EnvironmentInformationDTO> envs = Lists.newArrayList();
        for (Document document : findIterable) {
            String json = JSONObject.toJSONString(document);
            envs.add(JSONObject.parseObject(json, EnvironmentInformationDTO.class));
        }
        return envs;
    }

    @Override
    public EnvironmentInformationAggregationDTO environmentInformationAggregation() {
        List<EnvironmentInformationDTO> envs = environmentInformation();
        EnvironmentInformationAggregationDTO envAgg = new EnvironmentInformationAggregationDTO();
        if (CollectionUtils.isEmpty(envs)) {
            return envAgg;
        }

        /**各节点-统计时间**/
        Map<String, String> agggregationTime = envs.stream().collect(Collectors.toMap(EnvironmentInformationDTO::getId, EnvironmentInformationDTO::getTime));

        /**各节点-CPU**/
        Map<String, CpuDTO> cpuNode = envs.stream().map(e -> Tuples.of(e.getId(), e.getEnvironment().getCpu())).collect(Collectors.toMap(Tuple2::getT1, Tuple2::getT2));

        /**各节点-内存**/
        Map<String, MemoryDTO> memoryNode = envs.stream().map(e -> Tuples.of(e.getId(), e.getEnvironment().getMemory())).collect(Collectors.toMap(Tuple2::getT1, Tuple2::getT2));

        /**各节点-硬盘**/
        Map<String, List<DiskDTO>> diskNode = envs.stream().map(e -> Tuples.of(e.getId(), e.getEnvironment().getDisks())).collect(Collectors.toMap(Tuple2::getT1, Tuple2::getT2));

        /**各节点-网络**/
        Map<String, List<NetworkDTO>> networkNode = envs.stream().map(e -> Tuples.of(e.getId(), e.getEnvironment().getNetworks())).collect(Collectors.toMap(Tuple2::getT1, Tuple2::getT2));

        return envAgg.setNode(envs.size())
                .setAgggregationTime(agggregationTime)
                .setCpuNode(cpuNode)
                .setMemoryNode(memoryNode)
                .setNetworkNode(networkNode)
                .setDiskNode(diskNode);
    }
}
