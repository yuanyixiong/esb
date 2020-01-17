package com.sinoif.esbimpl.core.kafka;

import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Indexes;
import com.sinoif.esb.constants.CoreConstants;
import com.sinoif.esb.enums.TypeTransferEnum;
import com.sinoif.esb.port.bean.Interface;
import com.sinoif.esb.port.service.EsbPortalRemoteService;
import com.sinoif.esbimpl.core.kafka.impl.CustomAckMessageListener;
import com.sinoif.esbimpl.core.util.CustomThreadPool;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.kafka.core.ConsumerFactory;

import javax.annotation.PreDestroy;
import java.util.Properties;

/**
 * kafka操作服务，1. 连接kafka  2.动态创建kafka队列， consumer、consumer group
 * 3.启动kafka consumer 监听 4.向 kafka 队列发送数据
 */
@Configuration
@PropertySource("classpath:kafka.properties")
public class KafkaManager implements AsyncNotify {
    private final org.slf4j.Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${bootstrap_servers}")
    String bootstrapServer;

    @Value("${message_processing_thread_no}")
    int consumeThreads;

    @Value("${max.poll.interval.ms}")
    int maxProcessingTime;

    private Producer<String, String> messageTemplate;

    private EsbPortalRemoteService esbPortalRemoteService;


    @Autowired
    private MongoDatabase mongoDatabase;

    @Autowired
    ConsumerFactory factory;

    @Autowired
    CustomThreadPool customThreadPoolExecutor;

    /**
     * 动态创建consumer与consumerGroup
     *
     * @param group       consumer group可以是应用的名称。
     * @param topic       topic名称
     * @param keyProperty 除列存储数据的主键
     * @return 创建是否成功
     */
    public boolean startContainer(String group, String topic, String keyProperty, String updateTimeProperty) {
        Logger.getRootLogger().setLevel(Level.INFO);
        logger.debug("register group:" + group);
        // 他建自定义消息处理，esb中的消息处理主要是调用输出接口，将数据传输到输出系统
        OutputPortProcessor outputPortProcessor = new OutputPortProcessor(group, topic, esbPortalRemoteService, mongoDatabase, keyProperty, updateTimeProperty);
        CustomAckMessageListener customAckMessageListener = new CustomAckMessageListener(outputPortProcessor,customThreadPoolExecutor);        // 启动consumer监听
        KafkaUtil.startOrCreateConsumers(topic, group,customAckMessageListener,factory);
        // 在mongodb 的collection创建更新时间字段的索引
        if (CoreConstants.MONGO_TOPIC.equalsIgnoreCase(topic) && updateTimeProperty != null) {
            mongoDatabase.getCollection(topic).createIndex(Indexes.descending(updateTimeProperty));
        }
        return true;
    }

    /**
     * 将接口对应的kafka container 关闭。接口被删除时应调用此方法。
     *
     * @param esbInterface 接口对象
     * @return 成功："success"
     */
    public String stopInterfaceConsumer(Interface esbInterface) {
        String appId = esbInterface.getTypeTransfer()== TypeTransferEnum.INPUT? CoreConstants.MONGO_TOPIC:(esbInterface.getAppId()+"");
        return KafkaUtil.stopInterfaceConsumer(esbInterface.getTopic(),appId);
    }

    /**
     * 集止所有的consumer, 应用关闭的时候调用
     */
    @PreDestroy
    public void stop() {
        KafkaUtil.stopConsumer(null);
    }

    /**
     * 启动消息发送模版
     */
    private void startTemplate() {
        Properties props = new Properties();
        Properties properties = getBaseProperty();
        for (String k : properties.stringPropertyNames()) {
            props.put(k, properties.getProperty(k));
        }
        props.put("acks", "all");
        props.put("retries", 3);
        props.put("batch.size", 100);
        props.put("linger.ms", 20);
//        props.put("bootstrap.servers", bootstrapServer);
//        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
//        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        messageTemplate = new KafkaProducer<>(props);
    }

    /**
     * 发送消息消息
     *
     * @param topic 发送的topic
     * @param key 消息的key
     * @param value 消息的value
     */
    public void sendMessage(String topic, String key, String value) {
        messageTemplate.send(new ProducerRecord<>(topic, key, value));
    }

    /**
     * 设置 esbportal 接口
     *
     * @param esbPortalRemoteService esbportal接口
     */
    public void setEsbPortalRemoteService(EsbPortalRemoteService esbPortalRemoteService) {
        this.esbPortalRemoteService = esbPortalRemoteService;
        startTemplate();
    }

    public void esbServiceReady() {
    }

    /**
     * kafka container通用属性设置
     *
     * @return kafka properties
     */
    public Properties getBaseProperty() {
        Properties props = new Properties();
        props.put("bootstrap.servers", bootstrapServer);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        return props;
    }

    public static void main(String[] args) {
        Properties properties = new KafkaManager().getBaseProperty();
        for (String k : properties.stringPropertyNames()) {
            System.out.println(k + "=" + properties.getProperty(k));
        }
//        String arrays = "[\n" +
//                "  {\n" +
//                "    \"id\": \"23\",\n" +
//                "    \"currency\": \"C0001\",\n" +
//                "    \"name\": \"江苏璇众贸易有限公司\",\n" +
//                "    \"money\": \"35000万\",\n" +
//                "    \"dr\": 0,\n" +
//                "    \"groupCoreCompany\": \"0000000002\",\n" +
//                "    \"code\": \"2\",\n" +
//                "    \"principal\": \"杨柳青\",\n" +
//                "    \"countryZone\": \"CT3116\",\n" +
//                "    \"majorBusiness\": \"贸易\"\n" +
//                "  },\n" +
//                "  {\n" +
//                "    \"id\": \"2\",\n" +
//                "    \"currency\": \"C0001\",\n" +
//                "    \"name\": \"江苏璇众贸易有限公司\",\n" +
//                "    \"money\": \"35000万\",\n" +
//                "    \"dr\": 0,\n" +
//                "    \"groupCoreCompany\": \"0000000002\",\n" +
//                "    \"code\": \"2\",\n" +
//                "    \"principal\": \"杨柳青\",\n" +
//                "    \"countryZone\": \"CT3116\",\n" +
//                "    \"majorBusiness\": \"贸易\"\n" +
//                "  }\n" +
//                "]";
//        JSONArray arr = JSONObject.parseArray(arrays);
//        Iterator iterable = arr.iterator();
//        while(iterable.hasNext()){
//            JSONObject obj = (JSONObject)iterable.next();
//            System.out.println(obj.get("id"));
//        }
    }
}
