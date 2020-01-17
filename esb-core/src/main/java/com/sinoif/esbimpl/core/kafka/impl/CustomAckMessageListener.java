package com.sinoif.esbimpl.core.kafka.impl;

import com.sinoif.esb.port.bean.InvokeResult;
import com.sinoif.esb.utils.ConcurrentRunnable;
import com.sinoif.esbimpl.core.kafka.api.IMessageProcessor;
import com.sinoif.esbimpl.core.util.CustomThreadPool;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.TopicPartition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.listener.AcknowledgingMessageListener;
import org.springframework.kafka.listener.ConsumerSeekAware;
import org.springframework.kafka.support.Acknowledgment;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

/**
 * 自定义kafka消息监听器
 */
public class CustomAckMessageListener implements AcknowledgingMessageListener<String, String>, ConsumerSeekAware {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private IMessageProcessor messageProcessor;
    private ConsumerSeekAware.ConsumerSeekCallback seekCallback;
    CustomThreadPool poolExecutor;

    /**
     * 接收kafka消息
     *
     * @param consumerRecord kafka消息
     * @param acknowledgment kafka响应回复接口
     */
    @Override
    public void onMessage (
            ConsumerRecord<String, String> consumerRecord, Acknowledgment acknowledgment) {
        logger.debug("current kafka offset:" + consumerRecord.offset());
        // 消息处理
        handleMessage(consumerRecord);
        acknowledgment.acknowledge();
    }

    /**
     * 消息处理，从kafka中读取消息，调用输出接口将数据发到输出系统
     *
     * @param consumerRecord kafka消息
     * @return 执行结果
     */
    private InvokeResult handleMessage(ConsumerRecord<String, String> consumerRecord){
        try {
            logger.info("输出消息到输出接口：key" + consumerRecord.key());
            LinkedHashMap<String,String> dataMap = new LinkedHashMap<>();
            dataMap.put("data",consumerRecord.value());
            ConcurrentRunnable runnable = new ConcurrentRunnable(
                    ()->messageProcessor.process(consumerRecord.key(), consumerRecord.value()),dataMap);
            List<Future<InvokeResult>> futures = poolExecutor.invokeAll(Collections.singletonList(runnable),
                    50, TimeUnit.SECONDS); // 强制保证一个输出消息处理少于50秒
            if(futures.size()>0){
                return futures.get(0).get();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return InvokeResult.fail(null,"超过最大消息处理时长："+e.getMessage());
        }
        return InvokeResult.success(null,"success");
    }

    /**
     * 自定义kafka消息监听器
     *
     * @param messageProcessor 消息处理器
     * @param poolExecutor 消息处理线程池
     */
    public CustomAckMessageListener(IMessageProcessor messageProcessor, CustomThreadPool poolExecutor) {
        this.messageProcessor = messageProcessor;
        this.poolExecutor = poolExecutor;
    }

    /**
     * 注册kafka寻址回调,使用Consumer能进行寻址操作
     *
     * @param consumerSeekCallback 寻址操作回调
     */
    @Override
    public void registerSeekCallback(ConsumerSeekCallback consumerSeekCallback) {
        this.seekCallback = consumerSeekCallback;
    }

    /**
     * 当消息partition发生改变时
     *
     * @param map 新的partition以及它的offset
     * @param consumerSeekCallback 寻址操作回调句抦
     */
    @Override
    public void onPartitionsAssigned(Map<TopicPartition, Long> map, ConsumerSeekCallback consumerSeekCallback) {

    }

    /**
     * 当container 空闲时执行的回调函数
     *
     * @param map 新的partition以及它的offset
     * @param consumerSeekCallback 寻址操作回调句抦
     */
    @Override
    public void onIdleContainer(Map<TopicPartition, Long> map, ConsumerSeekCallback consumerSeekCallback) {

    }
}
