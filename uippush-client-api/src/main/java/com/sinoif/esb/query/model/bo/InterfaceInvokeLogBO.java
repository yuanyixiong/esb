package com.sinoif.esb.query.model.bo;

import com.sinoif.esb.enums.ResponseState;
import com.sinoif.esb.query.model.param.BasicQueryParam;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Objects;

/**
 * @author 袁毅雄
 * @description 系统业务Bean-接口调用日志、异常接口日志 参数
 * @date 2019/11/1
 */
public class InterfaceInvokeLogBO extends BasicQueryParam {

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
     * 瀑布流分页最后一个日志id
     */
    private Long lastId;

    /**
     * 是否已处理
     */
    private Boolean handled;

    /**
     * 接口响应状态
     */
    private ResponseState responseStatus;

    /**
     * 输入系统
     */
    private String inputSystem;

    /**
     * 输出系统
     */
    private String outputSystem;

    public Long getLogId() {
        return logId;
    }

    public InterfaceInvokeLogBO setLogId(Long logId) {
        this.logId = logId;
        return this;
    }


    public Date getInvokeTime() {
        return invokeTime;
    }

    public String getInvokeBeginTimeStr() {
        if (Objects.nonNull(this.getInvokeTime())) {
            return LocalDateTime.ofInstant(this.getInvokeTime().toInstant(), ZoneId.systemDefault()).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        }
        return null;
    }

    public InterfaceInvokeLogBO setInvokeTime(Date invokeTime) {
        this.invokeTime = invokeTime;
        return this;
    }

    public Date getCompleteTime() {
        return completeTime;
    }

    public String getInvokeEndTimeStr() {
        if (Objects.nonNull(this.getCompleteTime())) {
            return LocalDateTime.ofInstant(this.getCompleteTime().toInstant(), ZoneId.systemDefault()).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        }
        return null;
    }

    public InterfaceInvokeLogBO setCompleteTime(Date completeTime) {
        this.completeTime = completeTime;
        return this;
    }



    public Long getLastId() {
        return lastId;
    }

    public InterfaceInvokeLogBO setLastId(Long lastId) {
        this.lastId = lastId;
        return this;
    }

    public ResponseState getResponseStatus() {
        return responseStatus;
    }

    public InterfaceInvokeLogBO setResponseStatus(ResponseState responseStatus) {
        this.responseStatus = responseStatus;
        return this;
    }

    public String getInterfaceName() {
        return interfaceName;
    }

    public InterfaceInvokeLogBO setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
        return this;
    }

    public String getTypeTransfer() {
        return typeTransfer;
    }

    public InterfaceInvokeLogBO setTypeTransfer(String typeTransfer) {
        this.typeTransfer = typeTransfer;
        return this;
    }

    public Boolean getHandled() {
        return handled;
    }

    public void setHandled(Boolean handled) {
        this.handled = handled;
    }

    public String getInputSystem() {
        return inputSystem;
    }

    public void setInputSystem(String inputSystem) {
        this.inputSystem = inputSystem;
    }

    public String getOutputSystem() {
        return outputSystem;
    }

    public void setOutputSystem(String outputSystem) {
        this.outputSystem = outputSystem;
    }

}
