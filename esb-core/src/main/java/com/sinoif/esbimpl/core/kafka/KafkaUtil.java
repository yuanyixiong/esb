package com.sinoif.esbimpl.core.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.listener.KafkaMessageListenerContainer;

import java.util.HashMap;
import java.util.Map;

/**
 * kafka 工具类由KafkaManager类创建，具体负责kafka consumer group的创建。
 */
public final class KafkaUtil {
    private final static Logger logger = LoggerFactory.getLogger(KafkaUtil.class);

    public static Map<String, KafkaMessageListenerContainer<String, String>> consumersMap =
            new HashMap<>();

    /**
     * 先检查要创建的kafka consumer group是否在，如果不存在就创建，否则不做任务操作，所以可以安全地重复调用
     *
     * @param topic              consumer group监听的topic
     * @param messageListener    消息监听器的实现
     *                           based on enable.auto.commit
     */
    public static void startOrCreateConsumers(
            final String topic,
            final String group,
            final Object messageListener,
            ConsumerFactory<String, String> factory
    ) {
        KafkaMessageListenerContainer<String, String> container = consumersMap.get(group + ":" + topic);
        if (container != null) {
            if (!container.isRunning()) {
                container.start();
                logger.info("开始已存在的监听器：{}：{}",group+"",topic);
            }else{
                logger.info("topic:" + topic + "已经存在！");
            }
            return;
        }
        ContainerProperties containerProps = new ContainerProperties(topic);
        containerProps.setGroupId(group);
        containerProps.setPollTimeout(100);
        containerProps.setAckMode(ContainerProperties.AckMode.MANUAL_IMMEDIATE);
        container = new KafkaMessageListenerContainer<>(factory, containerProps);
        container.setupMessageListener(messageListener);
        container.setBeanName(group+":"+topic);
        container.start();
        logger.info("启动kafka消息监听，topic ={}, appId={}", topic, group);
        consumersMap.put(group + ":" + topic, container);
    }

    /**
     * 停止接口对应的消费者
     *
     * @param topic topic
     * @param appId 应用id
     * @return 成功："success"
     */
    public static String stopInterfaceConsumer(String topic, String appId) {
        logger.info("关闭监听器" + "topic:" + topic + " appid:" + appId);

        KafkaMessageListenerContainer container = consumersMap.get(appId + ":" + topic);
        if (container != null) {
            container.stop(()->{
                logger.info("停止成功");
            });
            container.setAutoStartup(false);
            consumersMap.remove(appId + ":" + topic);
            logger.info("关闭成功");
        }
        return "success";
    }

    /**
     * 通过topic名称获取Consumer然后关闭consumer的监听
     *
     * @param topic 要关闭的consumer的topic名秒
     */
    public static void stopConsumer(final String topic) {
        logger.info("关闭监听器" + "topic:" + topic);
        if (topic == null) {
            consumersMap.forEach((__, v) -> v.stop());
        } else {
            KafkaMessageListenerContainer<String, String> container = consumersMap.get(topic);
            container.stop();
            logger.info("consumer stopped!!");
        }
    }

    private KafkaUtil() {
        throw new UnsupportedOperationException("不允许直接创建KafkaUtil");
    }

}
