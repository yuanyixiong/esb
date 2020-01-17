package com.sinoif.esbimpl.core.kafka.impl;

import com.sinoif.esbimpl.core.kafka.api.IMessageProcessor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.listener.MessageListener;

/**
 * 自动确认消息监听器
 */
public class CustomMessageListener implements MessageListener<String, String> {

    // 注入消息处理实现
    private IMessageProcessor messageProcessor;

    /**
     * 接口kafka消息
     * @param consumerRecord kafka消息
     */
    @Override
    public void onMessage(ConsumerRecord<String, String> consumerRecord) {

        // 处理消息
        messageProcessor.process(consumerRecord.key(), consumerRecord.value());
    }

    /**
     * 注册消息处理器
     *
     * @param messageProcessor 消息处理器
     */
    public CustomMessageListener(IMessageProcessor messageProcessor) {
        this.messageProcessor = messageProcessor;
    }
}
