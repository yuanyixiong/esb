package com.sinoif.esbimpl.core.kafka;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mongodb.BasicDBObject;
import com.mongodb.MongoWriteException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.sinoif.esb.constants.CoreConstants;
import com.sinoif.esb.port.bean.InvokeResult;
import com.sinoif.esb.port.service.EsbPortalRemoteService;
import com.sinoif.esb.utils.sequence.SequenceUtil;
import com.sinoif.esbimpl.core.kafka.api.IMessageProcessor;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * 输出接口消息处理类，从kafka consumer group中读取消息，然后调用输出接口输出数据，同时将数据保存到数据库供被动接口，异常处理功能使用。
 */
public class OutputPortProcessor implements IMessageProcessor {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    EsbPortalRemoteService esbPortalRemoteService;
    // topic，决定此processor处理的是哪种类型的数据。
    String topic;
    // 所属应用组
    String group;
    // 接口数据对应的key属性
    String keyProperty;
    // 更新时间属性
    String updateTimeProperty;


    MongoDatabase mongoDatabase;

    SimpleDateFormat sdf = new SimpleDateFormat(CoreConstants.DATETIME_FORMAT);
    /**
     * 构造函数
     *
     * @param group          consumer group名称
     * @param topic          topic名称
     * @param esbPortalRemoteService esb-portal模块服务
     * @param mongoDatabase  输出数据库
     * @param keyProperty    队列数据的主键属性名称
     */
    public OutputPortProcessor(String group, String topic, EsbPortalRemoteService esbPortalRemoteService,
                               MongoDatabase mongoDatabase, String keyProperty,String updateTimeProperty) {
        this.mongoDatabase = mongoDatabase;
        this.group = group;
        this.esbPortalRemoteService = esbPortalRemoteService;
        this.topic = topic;
        this.keyProperty = keyProperty;
        this.updateTimeProperty = updateTimeProperty;
    }

    /**
     * 消息处理
     *
     * @param key   消息的key
     * @param value 消息的value
     * @return 处理结果，决定是否将kafka消息标记为已读
     */
    @Override
    public InvokeResult process(String key, String value) {
        logger.info("接收到消息：topic={},group={}, key={}", topic, group, key);
        if (CoreConstants.MONGO_TOPIC.equals(group)) {
            try{
                long batchNo = SequenceUtil.getId();
                MongoCollection collection = mongoDatabase.getCollection(topic);
                if (value.startsWith("[")) {
                    JSONArray jsonArray = (JSONArray) JSONArray.parse(value);
                    jsonArray.forEach(json -> saveObject((JSONObject) json, collection,batchNo));
                } else {
                    saveObject(JSONObject.parseObject(value), collection,batchNo);
                }
            }catch (Exception e){
                logger.info("输出到mongodb错误：{}",e.getMessage());
                e.printStackTrace();
                return InvokeResult.fail(null,e.getMessage());
            }
            return InvokeResult.success(null,"保存数据成功");
        } else {
            InvokeResult result = esbPortalRemoteService.invokeInterfaceByKafka(topic, value,group);
            logger.info("根据topic执行方法响应：topic={}, result={}", topic, result);
            if (!result.isSuccess()) {
                // 输出接口调用失败，修改mongodb记录的状态
                MongoCollection collection = mongoDatabase.getCollection(topic);
                if (value.startsWith("[")) {
                    JSONArray jsonArray = (JSONArray) JSONArray.parse(value);
                    jsonArray.forEach(json -> updateFailed((JSONObject) json, collection));
                } else {
                    updateFailed(JSONObject.parseObject(value), collection);
                }
            }
            return result;
        }
    }

    /**
     * 将接口数据存储
     *
     * @param json       接口数据
     * @param collection MongoCollection
     * @param batchNo 插入数据的批号，用于审核数据发送
     */
    private void saveObject(JSONObject json, MongoCollection collection,long batchNo) {
        json.put("_id", json.get(keyProperty));
        json.put("_time", sdf.format(new Date()));
        json.put("_batch_no",batchNo);
        try{
            Document doc = Document.parse(json.toString());
            BasicDBObject bson = new BasicDBObject();
            bson.append("_id",json.get(keyProperty));
            Object result = collection.findOneAndReplace(bson,doc);
            if(result==null){
                collection.insertOne(doc);
            }
        }catch (MongoWriteException e){
            logger.debug("write mondodb 错误："+e.getMessage());
        }
    }

    /**
     * 更新mongodb里面的记录的状态为失败
     *
     * @param json       接口数据
     * @param collection mongoCollection
     */
    private void updateFailed(JSONObject json, MongoCollection collection) {
        BasicDBObject newDocument = new BasicDBObject();
        newDocument.append("$set", new BasicDBObject().append("_status", 0));
        BasicDBObject searchQuery = new BasicDBObject().append(keyProperty, json.get(keyProperty));
        collection.updateOne(searchQuery, newDocument);
    }

}
