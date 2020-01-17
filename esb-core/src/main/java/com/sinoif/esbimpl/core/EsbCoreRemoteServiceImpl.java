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

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sinoif.esb.constants.CoreConstants;
import com.sinoif.esb.constants.PortalConstants;
import com.sinoif.esb.core.service.EsbCoreRemoteService;
import com.sinoif.esb.enums.TypeActiveEnum;
import com.sinoif.esb.enums.TypeTransferEnum;
import com.sinoif.esb.port.bean.Interface;
import com.sinoif.esb.port.bean.InvokeResult;
import com.sinoif.esb.query.model.dto.ApproveInfoDataDTO;
import com.sinoif.esb.utils.sequence.SequenceUtil;
import com.sinoif.esbimpl.core.kafka.KafkaManager;
import com.sinoif.esbimpl.core.kafka.KafkaUtil;
import com.sinoif.esbimpl.core.mongodb.MongoService;
import org.apache.curator.framework.CuratorFramework;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * esb-core模块接口实现类
 */
@Configuration
@Component("esbCoreServiceImpl")
public class EsbCoreRemoteServiceImpl implements EsbCoreRemoteService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private KafkaManager kafkaManager;

    @Autowired
    MongoService mongoService;

    @Autowired
    CuratorFramework client;

    @PostConstruct
    private void loadInterfaces() throws Exception {
        logger.debug("esb-core加载缓存中的接口：");
        String path = "/interface";
        List<String> children = null;
        try {
            children = client.getChildren().forPath(path);
        } catch (Exception e) {
            logger.debug("zk 中未配置接口");
            return;
        }
        for (String subPath : children) {
            String interfaceJson = null;
            try {
                interfaceJson = new String(client.getData().forPath(path + "/" + subPath), "utf-8");
                Interface esbInterface = JSONObject.parseObject(interfaceJson, Interface.class);
                logger.debug("注册输入接口" + esbInterface);
                registerInterface(esbInterface);
            } catch (Exception e) {
                e.printStackTrace();
                logger.error(e.getMessage());
                logger.error("加截接口失败：" + interfaceJson);
            }
        }
    }

    /**
     * 向kafka发送消息
     *
     * @param topic    topic
     * @param key      数据键
     * @param jsonData 数据值（json）
     * @return 是否发送成功
     */
    @Override
    public boolean inputData(String topic, String key, String jsonData) {
        logger.info("向kafka发送消息,topic={}, key={},data={}", topic, key, jsonData.substring(0, jsonData.length() > 100 ? 100 : jsonData.length() - 1));
        kafkaManager.sendMessage(topic, key, jsonData);
        logger.info("消息发送成功" + jsonData.substring(0, jsonData.length() > 100 ? 100 : jsonData.length() - 1));
        return true;
    }

    /**
     * 接口注册，输入接口注册mongo consumer group, 输出接口注对应应用的 consumer group
     *
     * @param esbInterface 向核心模块注册的接口, 要求
     *                     1. typeTransfer不能为空
     *                     2.输出接口appName不能为空
     *                     3.typeActive为主动
     *                     4.keyProperty不能为空
     * @return 成功：“success"
     */
    @Override
    public String registerInterface(Interface esbInterface) {
        logger.info("接口注册：esbInterface={}", esbInterface);
        if (esbInterface.getTypeActive() == null) {
            return "注册失败：主被动属性不能为空";
        } else if (esbInterface.getAppId() == -1 && TypeTransferEnum.OUTPUT == esbInterface.getTypeTransfer()) {
            return "注册失败：输出接口必须关联应用";
        } else if (esbInterface.getTypeTransfer() == null) {
            return "注册失败：接口传输类型（输入、输出）不能为空";
        } else if (StringUtils.isEmpty(esbInterface.getTopic())) {
            return "注册失败：接口 topic 属性不能为空";
        } else if (TypeTransferEnum.INPUT == esbInterface.getTypeTransfer()
                && StringUtils.isEmpty(esbInterface.getKeyProperty())) {
            return "注册失败：输入接口的 keyProperty 属性不能为空";
        } else if (TypeTransferEnum.OUTPUT == esbInterface.getTypeTransfer()
                && TypeActiveEnum.REACTIVE == esbInterface.getTypeActive()) {
            return "被动输出接口无需注册";
        } else if (esbInterface.getTypeActive() == TypeActiveEnum.INITIATIVE && esbInterface.getParams() == null) {
            return "注册失败：主动接参数列表不能为空";
        }
        return "success";
    }

    /**
     * 启动接口对应的kafka队列
     *
     * @param esbInterface 接口
     * @return 成功：”success“
     */
    public String startInterface(Interface esbInterface) {
        if ("success".equals(registerInterface(esbInterface))) {
            if (esbInterface.getTypeTransfer() == TypeTransferEnum.INPUT) {
                String updateTimeProperty = null;
                LinkedHashMap<String, String> params = esbInterface.getParams();
                if (params != null && params.containsKey(CoreConstants.INCREMENTAL_POLICY)) {
                    JSONObject policy = (JSONObject) JSON.parse(params.get(CoreConstants.INCREMENTAL_POLICY));
                    updateTimeProperty = policy.getString(CoreConstants.INCREMENTAL_POLICY_FIELD);
                }
                kafkaManager.startContainer(CoreConstants.MONGO_TOPIC, esbInterface.getTopic(), esbInterface.getKeyProperty(), updateTimeProperty);
            } else {
                kafkaManager.startContainer(esbInterface.getAppId() + "", esbInterface.getTopic(), esbInterface.getKeyProperty(), null);
            }
        }
        mongoService.createCollectionAndIndex(esbInterface);
        return "success";
    }

    /**
     * 删除接口时调用此方法，停止接口对象的kafka队列
     *
     * @param esbInterface 要删除的接口
     * @return 成功：”success"
     */
    @Override
    public String deleteInterfae(Interface esbInterface) {
        kafkaManager.stopInterfaceConsumer(esbInterface);
        return "success";
    }

    /**
     * 按条件查询mongodb数据库
     *
     * @param topic 接口对应的topic，也是mongodb中的表名（collection）
     * @param params 参数map
     * @return 返回结果 json 字符串
     * @throws Exception 参数格式异常
     */
    @Override
    public String mongoDbQuery(String topic, HashMap<String, String> params) throws Exception {
        return mongoService.queryByCondition(topic, params);
    }

    /**
     * mongodb 接页查询
     *
     * @param topic      Collection名称
     * @param params     条件参数
     * @param pagingSize 每页显示的数据条数
     * @param asc        瀑布流分页顺序
     * @param lastKey    瀑布流分页的列
     * @param lastValue  瀑布流分页的值
     * @return 返回值（json字符串）
     */
    @Override
    public String mongoDbQuery(String topic, HashMap<String, String> params, Integer pagingSize, boolean asc, String lastKey, String lastValue) {
        try {
            return mongoService.queryByCondition(topic, params, pagingSize, asc, lastKey, lastValue);
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    /**
     * 保存接口调用日志
     *
     * @param topic 目标Collection
     * @param jsonData 业务数据
     * @param keyProperty 主键名称
     * @param time 日志插入时间
     * @param dataCount 接口传输数据的记录数
     * @return 日志保存结果
     */
    @Override
    public InvokeResult saveLog(String topic, String jsonData, String keyProperty, String time, int dataCount) {
        return mongoService.saveData(topic, jsonData, keyProperty, -1, time, "cnt", dataCount);
    }

    /**
     * 保存一条日志对应的数据名细
     *
     * @param parentId 日志id
     * @param interfaceId 接口id
     * @param parentTime 日志记录时间
     * @param topic topic
     * @param jsonString 日志数据
     * @param keyProperty 日志数据对应的id属性名
     * @return 写入结果
     */
    @Override
    public InvokeResult saveLogData(long parentId, String interfaceId, String parentTime, String topic, String jsonString, String keyProperty) {
        return mongoService.saveData(topic, jsonString, keyProperty, parentId, parentTime, "_interface_id", interfaceId);
    }

    /**
     * 更新mongodb
     *
     * @param topic 目录collection
     * @param data 要更新的数据，key-列表， value-值
     * @param keyProperty id例名称
     * @param keyValue id属性的名称
     * @return 受影响记录的条数
     */
    @Override
    public long mongoDbUpdate(String topic, HashMap<String, String> data, String keyProperty, Object keyValue) {
        HashMap<String, Object> conditions = new HashMap<>();
        conditions.put(keyProperty, keyValue);
        return mongoService.update(topic, data, conditions);
    }

    /**
     * 最大日期字段数据，用于增量更新
     *
     * @param topic 接口对应topic
     * @param lastUpdateTimeField 日期属性名称
     * @return 最大日期值
     */
    @Override
    public String getMaxDateOfFetchedData(String topic, String lastUpdateTimeField) {
        return mongoService.getMaxDateOfFetchedData(topic, lastUpdateTimeField);
    }

    /**
     * 保存待审核数据的明细信息
     *
     * @param id
     * @param interfaceId 接口id
     * @param topic 接口topic
     * @param inputSystem 源系充
     * @param outputSystem 目标系统
     * @param interfaceName 接口名称
     * @param time 时间
     * @param data 需要审核的数据
     */
    @Deprecated
    @Override
    public void saveApproveInfo(long id, long interfaceId, String topic, String inputSystem, String outputSystem, String interfaceName, String time, String data) {
        HashMap<String, Object> dataMap = new HashMap<>();
        dataMap.put("interface_id", interfaceId + "");
        dataMap.put("topic", topic);
        dataMap.put("input_system", inputSystem);
        dataMap.put("output_system", outputSystem);
        dataMap.put("approveId", id + "");
        dataMap.put("interface_name", interfaceName == null ? "notset" : interfaceName);
        dataMap.put("_handled", "false");
        Object dataObj = getData(data);
        dataMap.put("data_count", getDateSize(dataObj));
        // 保存列表
        mongoService.saveData(PortalConstants.COLLECTION_APPROVE_INFO, JSON.toJSONString(dataMap), "approveId", -1, time);
        // 保存待审核明细
        ApproveInfoDataDTO approveDetail = new ApproveInfoDataDTO();
        approveDetail.setApproveStatus(PortalConstants.NOT_PROCESSED);
        approveDetail.setParent(id + "");
        approveDetail.setInterfaceId(interfaceId + "");
        approveDetail.setTopic(topic);
        if (StringUtils.isEmpty(data)) {
            return;
        }
        if (dataObj instanceof String || dataObj instanceof JSONObject) {
            approveDetail.setId(SequenceUtil.getId() + "");
            approveDetail.setData(dataObj);
            mongoService.saveData(PortalConstants.COLLECTION_APPROVE_INFO_DATA, JSON.toJSONString(approveDetail), "_id", id, time);
        } else if (dataObj instanceof JSONArray) {
            JSONArray array = (JSONArray) dataObj;
            for (Object o : array) {
                approveDetail.setId(SequenceUtil.getId() + "");
                approveDetail.setData(o);
                mongoService.saveData(PortalConstants.COLLECTION_APPROVE_INFO_DATA, JSON.toJSONString(approveDetail), "_id", id, time);
            }
        }
    }

    /**
     * 根据id获取数据记录
     *
     * @param topic topic
     * @param idProperty id 属性
     * @param idValue id 值
     * @return 数据记录
     */
    @Override
    public JSONObject findDataById(String topic, String idProperty, String idValue) {
        HashMap<String, String> queryMap = new HashMap<>();
        queryMap.put(idProperty, "=" + idValue);
        String result;
        try {
            result = mongoService.queryByCondition(topic, queryMap);
            return JSONArray.parseArray(result).getJSONObject(0);
        } catch (Exception e) {
            return null;
        }
    }


    /**
     * 方便测试方法
     * 获取当前所有的kafka消息监听器
     *
     * @return 监听器消息json字符串
     */
    @Override
    public String getListeners() {
        return JSON.toJSONString(KafkaUtil.consumersMap);
    }


    public void setKafkaManager(KafkaManager kafkaManager) {
        this.kafkaManager = kafkaManager;
    }

    /**
     * 将json字符串转换成 JSONObject 或 JSONArray对象
     * @param data 数据字符串
     * @return 对象
     */
    private Object getData(String data) {
        if (StringUtils.isEmpty(data)) {
            return null;
        }
        if (data.startsWith("{")) {
            return JSONObject.parseObject(data);
        } else if (data.startsWith("[")) {
            return JSONArray.parseArray(data);
        }
        return data;
    }

    /**
     * 获取json字符串中的记录数量
     *
     * @param data json字符串
     * @return 数据数量
     */
    private int getDateSize(Object data) {
        if (data == null) {
            return 0;
        }
        if (data instanceof String || data instanceof JSONObject) {
            return 1;
        } else if (data instanceof JSONArray) {
            return ((JSONArray) data).size();
        }
        return 0;
    }
}
