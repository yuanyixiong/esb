package com.sinoif.esbimpl.core.kafka.api;

import com.sinoif.esb.port.bean.InvokeResult;

/**
 * kafka消息处理接口
 */
public interface IMessageProcessor {

  /**
   * 处理kafka消息
   * @param key 消息的key
   * @param json 消息的value
   * @return 是否已读
   */
  InvokeResult process(String key, String json);
}
