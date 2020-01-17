package com.sinoif.esbimpl.port.interfaces;

import com.alibaba.fastjson.JSONObject;
import com.call.client.bean.SoapEndpoint;
import com.call.client.client.ClientProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * webservice接口信息提取器，负责根据Wsdl地址提取webservice提口的一些必要信息；
 * 例如：WsMethod,localpart等
 */
public class WsdlExtractor {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 获取webservice属性
     *
     * @param wsdlUrl wsdl地址
     * @return json 格式的 webservice属性
     */
    public String getWsdlProperty(String wsdlUrl) {
        List<SoapEndpoint> endpoints = ClientProxy.getEndpoints(wsdlUrl);
        return JSONObject.toJSONString(endpoints);
    }
}
