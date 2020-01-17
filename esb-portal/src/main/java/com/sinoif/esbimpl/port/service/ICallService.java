package com.sinoif.esbimpl.port.service;

import com.sinoif.esb.port.bean.InvokeResult;
import com.sinoif.esbimpl.port.annotation.InvokeLog;
import com.sinoif.esbimpl.port.service.bean.CallRequest;

/**
 * @author chenxj
 * @date 2019/10/22
 */
public interface ICallService {
    @InvokeLog
    InvokeResult process(CallRequest request) throws Exception;
}
