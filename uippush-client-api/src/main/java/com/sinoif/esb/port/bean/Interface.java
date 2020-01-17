package com.sinoif.esb.port.bean;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sinoif.esb.enums.*;

import java.io.Serializable;
import java.util.LinkedHashMap;

/**
 * 定义一个接口。是esb平台与Esb输入、输出模块交互的主要数据结构。
 */
public class Interface implements Serializable {
    // 接口id
    private long id;

    // 接口名称
    private String name;

    // 同步接口还是异步接口
    private TypeSyncEnum typeSync;

    // http or https
    private ProtocolTypeEnum protocolType;
    // websocket or restapi
    private ProtocolEnum protocol;
    // 服务器ip
    private String serverIp;
    // 服务端口
    private int serverPort;
    // 主动接口的服务地址, 被动接口的唯一标识符
    private String sendUrl;
    // 接口数据的主键
    private String keyProperty;
    // 接口数据是否需要审核
    private boolean needApprove;
    // RestApi Http请求的中的 mime 属性
    private RequestParamTypeEnum requestParamType;
    // post or get
    private RequestType requestType;
    // RestApi HttpResponse的 mime 属性
    private String responseType;
    // Webservice接口的 targetNamespace 属性，调用esb-portal提供的可查询到
    private String targetNameSpace;
    private String localPart;
    // Webservice接口的 soapAction属性 属性，调用esb-portal提供的可查询到
    private String wsMethod;
    // 主动 or 被动
    private TypeActiveEnum typeActive;
    // 输入 or 输出
    private TypeTransferEnum typeTransfer;
    // kafka topic
    private String topic;
    // 对应应用的id,用于创建consumer group, 对接口消息进行分组消费
    private long appId = -1;
    // 对应的应用名称，用于日志记录
    private String appName;
    // 参数，map的key指参数的名称，map的值为参数表达式
    private LinkedHashMap<String, String> params;
      // 被动接口的请求者ip地址
    private String requestIp;
    // 接口消息处理器类名(主动输入消处消需要)
    private String processorName = "com.sinoif.esbimpl.port.interfaces.processor.DefaultInterfaceResponseProcessor";

    // 编码
    private String charset;
    // 超时时间，为null取对应协议的超时时间
    private int timeOut;
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public ProtocolTypeEnum getProtocolType() {
        return protocolType;
    }

    public void setProtocolType(ProtocolTypeEnum protocolType) {
        this.protocolType = protocolType;
    }

    public ProtocolEnum getProtocol() {
        return protocol;
    }

    public void setProtocol(ProtocolEnum protocol) {
        this.protocol = protocol;
    }

    public String getServerIp() {
        return serverIp;
    }

    public void setServerIp(String serverIp) {
        this.serverIp = serverIp;
    }

    public int getServerPort() {
        return serverPort;
    }

    public void setServerPort(int serverPort) {
        this.serverPort = serverPort;
    }

    public String getSendUrl() {
        return sendUrl;
    }

    public void setSendUrl(String sendUrl) {
        this.sendUrl = sendUrl;
    }

    public String getKeyProperty() {
        return keyProperty;
    }

    public void setKeyProperty(String keyProperty) {
        this.keyProperty = keyProperty;
    }

    public boolean isNeedApprove() {
        return needApprove;
    }

    public void setNeedApprove(boolean needApprove) {
        this.needApprove = needApprove;
    }

    public RequestParamTypeEnum getRequestParamType() {
        return requestParamType;
    }

    public void setRequestParamType(RequestParamTypeEnum requestParamType) {
        this.requestParamType = requestParamType;
    }

    public RequestType getRequestType() {
        return requestType;
    }

    public void setRequestType(RequestType requestType) {
        this.requestType = requestType;
    }

    public String getResponseType() {
        return responseType;
    }

    public void setResponseType(String responseType) {
        this.responseType = responseType;
    }

    public String getTargetNameSpace() {
        return targetNameSpace;
    }

    public void setTargetNameSpace(String targetNameSpace) {
        this.targetNameSpace = targetNameSpace;
    }

    public String getLocalPart() {
        return localPart;
    }

    public void setLocalPart(String localPart) {
        this.localPart = localPart;
    }

    public String getWsMethod() {
        return wsMethod;
    }

    public void setWsMethod(String wsMethod) {
        this.wsMethod = wsMethod;
    }

    public TypeActiveEnum getTypeActive() {
        return typeActive;
    }

    public void setTypeActive(TypeActiveEnum typeActive) {
        this.typeActive = typeActive;
    }

    public TypeTransferEnum getTypeTransfer() {
        return typeTransfer;
    }

    public void setTypeTransfer(TypeTransferEnum typeTransfer) {
        this.typeTransfer = typeTransfer;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public long getAppId() {
        return appId;
    }

    public void setAppId(long appId) {
        this.appId = appId;
    }

    public TypeSyncEnum getTypeSync() {
        return typeSync;
    }

    public void setTypeSync(TypeSyncEnum typeSync) {
        this.typeSync = typeSync;
    }

    public String getProcessorName() {
        return processorName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTimeOut() {
        return timeOut;
    }

    public void setTimeOut(int timeOut) {
        this.timeOut = timeOut;
    }

    public String getCharset() {
        return charset;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }

    public void setProcessorName(String processorName) {
        this.processorName = processorName;
    }

    public String getRequestIp() {
        return requestIp;
    }

    public void setRequestIp(String requestIp) {
        this.requestIp = requestIp;
    }

    public LinkedHashMap<String, String> getParams() {
        return params;
    }

    public void setParams(LinkedHashMap<String, String> params) {
        this.params = params;
    }

    @Override
    public String toString() {
        return "Interface{" +
                "id=" + id +
                "name=" + name +
                ", typeSync=" + typeSync +
                ", protocolType=" + protocolType +
                ", protocol=" + protocol +
                ", serverIp='" + serverIp + '\'' +
                ", serverPort=" + serverPort +
                ", sendUrl='" + sendUrl + '\'' +
                ", keyProperty='" + keyProperty + '\'' +
                ", needApprove=" + needApprove +
                ", requestParamType=" + requestParamType +
                ", requestType=" + requestType +
                ", responseType='" + responseType + '\'' +
                ", targetNameSpace='" + targetNameSpace + '\'' +
                ", localPart='" + localPart + '\'' +
                ", wsMethod='" + wsMethod + '\'' +
                ", typeActive=" + typeActive +
                ", typeTransfer=" + typeTransfer +
                ", topic='" + topic + '\'' +
                ", appId=" + appId +
                ", appName='" + appName + '\'' +
                ", params=" + params +
                ", processorName='" + processorName + '\'' +
                ", charset='" + charset + '\'' +
                ", timeOut=" + timeOut +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Interface) {
            return ((Interface) obj).getId() == this.getId();
        }
        return false;
    }

}
