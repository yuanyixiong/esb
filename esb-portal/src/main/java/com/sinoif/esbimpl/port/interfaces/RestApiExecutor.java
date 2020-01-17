package com.sinoif.esbimpl.port.interfaces;

import com.call.client.bean.ConnectConfig;
import com.call.client.bean.Context;
import com.call.client.bean.HttpProtocol;
import com.call.client.client.CallResult;
import com.call.client.client.ClientProxy;
import com.call.client.enums.HttpMethodEnum;
import com.sinoif.esb.enums.RequestParamTypeEnum;
import com.sinoif.esb.enums.RequestType;
import com.sinoif.esb.port.bean.Interface;
import com.sinoif.esb.port.bean.InvokeResult;
import com.sinoif.esb.utils.InvokeParamHolder;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.LinkedHashMap;

/**
 * RestApi接口执行器，负责执行RestApi接口
 */
public class RestApiExecutor extends BaseExecutor {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 执行RestApi接口
     *
     * @param esbInterface 要执行的接口
     * @return 接口执行的返回值
     */
    @Override
    public InvokeResult invoke(Interface esbInterface, ConnectConfig config){
        Context<HttpProtocol> context = getRequest(esbInterface);
        context.setConnectConfig(config);
        CallResult result = ClientProxy.call(context);
        return handleResponse(result, esbInterface);
    }

    /**
     * 根据接口与置信息构建Request对象
     *
     * @param esbInterface 要执行的接口
     * @return Request对象
     * @throws UnsupportedEncodingException
     */
    private Context<HttpProtocol> getRequest(Interface esbInterface) {
        LinkedHashMap<String, String> params = InvokeParamHolder.get();
        if(params==null){
            params = filterParam(esbInterface.getParams());
        }
        String url = esbInterface.getProtocolType() + "://" + esbInterface.getServerIp() + ":" + esbInterface.getServerPort() + "/" + esbInterface.getSendUrl();
        logger.debug("执行RestApi: url = " + url);
        Context<HttpProtocol> context = new Context<>();
        if(StringUtils.isEmpty(esbInterface.getCharset())){
            esbInterface.setCharset("utf-8");
        }
        Charset charset = Charset.forName(esbInterface.getCharset());
        HttpProtocol protocol = esbInterface.getRequestType() == RequestType.GET ? new HttpProtocol(url) : new HttpProtocol(url, HttpMethodEnum.POST);
        protocol.setCharset(charset);
        context.setProtocol(protocol);
        if (params != null) {
            if (esbInterface.getRequestParamType() == RequestParamTypeEnum.FORMDATA && esbInterface.getRequestType() == RequestType.POST) {
                context.setParams(params);
            } else if (esbInterface.getRequestParamType() == RequestParamTypeEnum.JSON) {
                protocol.setContentType("application/json");
                String jsonBody = params.entrySet().iterator().next().getValue();
                context.setParams(jsonBody);
            }
        }
        return context;
    }

    /**
     * 构建通用消息返回处理Handler
     *
     * @return handler
     */
    private ResponseHandler<String> getResponseHandler() {
        return handler -> {
            int status = handler.getStatusLine().getStatusCode();
            if (status >= 200 && status < 300) {
                HttpEntity entity = handler.getEntity();
                return entity != null ? EntityUtils.toString(entity) : "";
            } else {
                throw new ClientProtocolException("Unexpected response status: " + status);
            }
        };
    }
}
