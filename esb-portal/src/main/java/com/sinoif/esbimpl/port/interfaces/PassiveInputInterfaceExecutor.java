package com.sinoif.esbimpl.port.interfaces;

import com.alibaba.fastjson.JSON;
import com.call.client.bean.ConnectConfig;
import com.sinoif.esb.port.bean.Interface;
import com.sinoif.esb.port.bean.InvokeResult;
import com.sinoif.esb.utils.InvokeParamHolder;
import org.springframework.stereotype.Component;

/**
 * 负责输入被动接口执行的executor
 */
@Component
public class PassiveInputInterfaceExecutor extends BaseExecutor {

    @Override
    public InvokeResult invoke(Interface esbInterface, ConnectConfig config) {
        return InvokeResult.success(esbInterface,"被动输入成功", JSON.toJSONString(InvokeParamHolder.get()));
    }
}
