package com.sinoif.esb.query.model.param;

import com.sinoif.esb.enums.ResponseState;

import java.util.Date;

/**
 * @author 袁毅雄
 * @description 系统入参Bean-接口调用日志、异常接口日志 列表查询参数
 * @date 2019/11/1
 */
public class InterfaceInvokeLogParam extends BasicQueryParam{

    /**
     * 日志id
     */
    private Long logId;


    /**
     * 接口开始调用时间
     */
    private Date invokeTime;

    /**
     * 接口结束调用时间
     */
    private Date completeTime;

    /**
     * 接口名称
     */
    private String interfaceName;

    /**
     * 接口输入/输出类别
     */
    private String typeTransfer;

    /**
     * 是否已处理
     */
    private boolean handled;


    /**
     * 接口响应状态
     */
    private ResponseState responseStatus;

    public Long getLogId() {
        return logId;
    }

    public InterfaceInvokeLogParam setLogId(Long logId) {
        this.logId = logId;
        return this;
    }

    public Date getInvokeTime() {
        return invokeTime;
    }

    public InterfaceInvokeLogParam setInvokeTime(Date invokeTime) {
        this.invokeTime = invokeTime;
        return this;
    }

    public Date getCompleteTime() {
        return completeTime;
    }

    public InterfaceInvokeLogParam setCompleteTime(Date completeTime) {
        this.completeTime = completeTime;
        return this;
    }

    public String getTypeTransfer() {
        return typeTransfer;
    }

    public InterfaceInvokeLogParam setTypeTransfer(String typeTransfer) {
        this.typeTransfer = typeTransfer;
        return this;
    }

    public boolean isHandled() {
        return handled;
    }

    public InterfaceInvokeLogParam setHandled(boolean handled) {
        this.handled = handled;
        return this;
    }

    public String getInterfaceName() {
        return interfaceName;
    }

    public InterfaceInvokeLogParam setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
        return this;
    }

    public ResponseState getResponseStatus() {
        return responseStatus;
    }

    public void setResponseStatus(ResponseState responseStatus) {
        this.responseStatus = responseStatus;
    }
}
