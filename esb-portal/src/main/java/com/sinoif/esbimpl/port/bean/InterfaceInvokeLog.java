package com.sinoif.esbimpl.port.bean;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.sinoif.esb.enums.ResponseState;

import java.util.LinkedHashMap;

/***
 * @author yuanyixiong
 * @date 2019/11/1
 * @describe 接口调用记录
 */
public class InterfaceInvokeLog {

    /**
     * 接口记录的唯一id
     */
    @JSONField(name = "_id")
    private Long id;

    /***
     * 调用的接口id
     */
    @JSONField(name = "interface_id")
    private String interfaceId;

    /**
     * 接口编码
     */
    @JSONField(name = "interface_charset")
    private String interfaceCharset;

    /**
     * 接口名称
     */
    @JSONField(name = "interface_name")
    private String interfaceName;

    @JSONField(name = "request_type")
    private String requestType;

    /**
     * 输出系统
     */
    @JSONField(name = "output_system")
    private String outputSystem;
    /**
     * 接口主动/被动类别
     */
    @JSONField(name = "type_active")
    private String typeActive;


    /**
     * 接口输入/输出类别
     */
    @JSONField(name = "type_transfer")
    private String typeTransfer;

    /**
     * 源系统
     */
    @JSONField(name = "input_system")
    private String inputSystem;

    /**
     * 调用接口的响应状态
     */
    @JSONField(name = "response_status")
    private ResponseState responseStatus;

    /**
     * 调用接口的响应结果
     */
    @JSONField(name = "invoke_result")
    private String invokeResult;

    /**
     * 调用接口响应失败的消息
     */
    @JSONField(name = "fail_message")
    private String failMessage;

    /**
     * 接口请求地址
     */
    @JSONField(name = "interface_address")
    private String interfaceAddress;

    /**
     * 请求类型
     */
    private String submitType;

    /**
     * 接口执行时长
     */
    @JSONField(name = "execute_duration")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long executeDuration;

    /**
     * 接口调用时间
     */
    @JSONField(name = "invoke_time", format = "yyyy-MM-dd HH:mm:ss")
    private String invokeTime;

    /**
     * 接口调用时间
     */
    @JSONField(name = "complete_time", format = "yyyy-MM-dd HH:mm:ss")
    private String completeTime;

    /**
     * 是否处理，无论是重试还是忽略，处理状态都会被修改为true
     */
    @JSONField(name = "handled")
    private String handled = "false";

    /**
     * 接口对应的应用id
     */
    @JSONField(name = "app_id")
    private String appId;

    /**
     * 接口调用时传递的参数
     */
    @JSONField(name = "interface_params")
    private LinkedHashMap<String, String> interfaceParams;

    /**
     * 接口传输的数据
     */
    private String data;

    /**
     * 关联的异常（此字段不为0说明此异常是重试异常时产生的新异常）
     */
    @JSONField(name = "parent_exception_id")
    Long parentExceptionId;

    /**
     * 聚合时的条数
     */
    private long cnt = 0;

    public String getTypeTransfer() {
        return typeTransfer;
    }

    public void setTypeTransfer(String typeTransfer) {
        this.typeTransfer = typeTransfer;
    }

    public String getTypeActive() {
        return typeActive;
    }

    public void setTypeActive(String typeActive) {
        this.typeActive = typeActive;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getInterfaceId() {
        return interfaceId;
    }

    public InterfaceInvokeLog setInterfaceId(String interfaceId) {
        this.interfaceId = interfaceId;
        return this;
    }

    public ResponseState getResponseStatus() {
        return responseStatus;
    }

    public InterfaceInvokeLog setResponseStatus(ResponseState responseStatus) {
        this.responseStatus = responseStatus;
        return this;
    }

    public String getInvokeResult() {
        return invokeResult;
    }

    public InterfaceInvokeLog setInvokeResult(String invokeResult) {
        this.invokeResult = invokeResult;
        return this;
    }

    public String getFailMessage() {
        return failMessage;
    }

    public InterfaceInvokeLog setFailMessage(String failMessage) {
        this.failMessage = failMessage;
        return this;
    }

    public Long getExecuteDuration() {
        return executeDuration;
    }

    public InterfaceInvokeLog setExecuteDuration(Long executeDuration) {
        this.executeDuration = executeDuration;
        return this;
    }

    public String getInvokeTime() {
        return invokeTime;
    }

    public InterfaceInvokeLog setInvokeTime(String invokeTime) {
        this.invokeTime = invokeTime;
        return this;
    }

    public String getInterfaceCharset() {
        return interfaceCharset;
    }

    public InterfaceInvokeLog setInterfaceCharset(String interfaceCharset) {
        this.interfaceCharset = interfaceCharset;
        return this;
    }

    public String getHandled() {
        return handled;
    }

    public void setHandled(String handled) {
        this.handled = handled;
    }

    public String getAppId() {
        return appId;
    }

    public InterfaceInvokeLog setAppId(String ap_handledpId) {
        this.appId = appId;
        return this;
    }

    public String getOutputSystem() {
        return outputSystem;
    }

    public InterfaceInvokeLog setOutputSystem(String outputSystem) {
        this.outputSystem = outputSystem;
        return this;
    }

    public String getInterfaceName() {
        return interfaceName;
    }

    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }

    public String getInputSystem() {
        return inputSystem;
    }

    public void setInputSystem(String inputSystem) {
        this.inputSystem = inputSystem;
    }

    public String getCompleteTime() {
        return completeTime;
    }

    public void setCompleteTime(String completeTime) {
        this.completeTime = completeTime;
    }

    public Long getParentExceptionId() {
        return parentExceptionId;
    }

    public void setParentExceptionId(Long parentExceptionId) {
        this.parentExceptionId = parentExceptionId;
    }

    public String getInterfaceAddress() {
        return interfaceAddress;
    }

    public void setInterfaceAddress(String interfaceAddress) {
        this.interfaceAddress = interfaceAddress;
    }

    public String getSubmitType() {
        return submitType;
    }

    public void setSubmitType(String submitType) {
        this.submitType = submitType;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getRequestType() {
        return requestType;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

    public long getCnt() {
        return cnt;
    }

    public void setCnt(long cnt) {
        this.cnt = cnt;
    }

    public LinkedHashMap<String, String> getInterfaceParams() {
        return interfaceParams;
    }

    public void setInterfaceParams(LinkedHashMap<String, String> interfaceParams) {
        this.interfaceParams = interfaceParams;
    }
}
