package com.sinoif.esb.port.service;

import com.call.client.client.CallResult;
import com.sinoif.esb.port.bean.Interface;
import com.sinoif.esb.port.bean.InvokeResult;

/**
 * 处理webservice的返回结果
 */
public interface InterfaceResponseProcessor {
    /**
     * 处理webservice类型接口的返回结果
     *
     * @param response 字符类型的webservice请求返回
     * @return invokeresult
     */
    InvokeResult process(CallResult response, Interface esbInterface);
}
