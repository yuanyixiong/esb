package com.sinoif.esbimpl.port.interfaces;

import com.call.client.bean.Context;
import com.call.client.bean.Protocol;
import com.call.client.bean.WebServiceProtocol;
import com.call.client.client.CallResult;
import com.call.client.client.ClientProxy;
import com.sinoif.esb.port.bean.Interface;
import com.sinoif.esb.port.bean.InvokeResult;
import com.sinoif.esb.utils.InvokeParamHolder;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.LinkedHashMap;

/**
 * webservice 接口执行器，负责执行Webservice接口
 */
public class WsPortExecutor extends BaseExecutor {

    private final Logger logger = LogManager.getLogger(WsPortExecutor.class);

    /**
     * 执行webservice接口
     *
     * @param esbInterface webservice类型的接口
     * @return 接口执行返回值
     */
    @Override
    public InvokeResult invoke(Interface esbInterface, com.call.client.bean.ConnectConfig config) {
        LinkedHashMap<String, String> params = InvokeParamHolder.get();
        if(params==null){
            params = filterParam(esbInterface.getParams());
        }
        String wsdlUrl = esbInterface.getProtocolType() + "://" + esbInterface.getServerIp() + ":" + esbInterface.getServerPort() + "/" + esbInterface.getSendUrl();
        Context<Protocol> context = new Context<>();
        context.setConnectConfig(config);
        WebServiceProtocol protocol = new WebServiceProtocol(wsdlUrl, esbInterface.getWsMethod());
        context.setProtocol(protocol);
        context.setParams(params);
        CallResult result = ClientProxy.call(context);
        return handleResponse(result, esbInterface);
    }
}
