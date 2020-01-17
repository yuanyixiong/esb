package com.sinoif.esb.port.bean;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;

/**
 * <p>执行结果</p>
 *
 * @author chenxj
 * @date 2019/10/10
 */
public class InvokeResult implements Serializable {
    // 调用编号 0：按接口调用 100：审核调用 其它，异常重试，值为异常编号
    long invokeNumber;

    public static InvokeResult success(Interface ife,String message) {
        return new InvokeResult(ife,true, message, null);
    }

    public static InvokeResult success(Interface ife, String message, String response) {
        return new InvokeResult(ife, true, message, response);
    }

    public static InvokeResult fail(Interface ife, String message) {
        return new InvokeResult(ife, false, message, null);
    }

    public InvokeResult(Interface ife, boolean success, String message, String response) {
        this.success = success;
        this.message = message;
        this.response = response;
        this.ife = ife;
    }

    public InvokeResult() {

    }

    /**
     * 是否成功， true：请求成功， false：失败
     */
    private boolean success = false;

    /**
     * 信息记录；失败，返回失败原因
     */
    private String message;

    /**
     * 同步接口类型，响应结果; json格式
     */
    private String response;

    /**
     * 调用的接口
     */

    @JsonIgnore
    @JSONField(serialize = false,deserialize = false)
    private Interface ife;

    public long getInvokeNumber() {
        return invokeNumber;
    }

    public void setInvokeNumber(long invokeNumber) {
        this.invokeNumber = invokeNumber;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public Interface getIfe() {
        return ife;
    }

    public void setIfe(Interface ife) {
        this.ife = ife;
    }

    @Override
    public String toString() {
        return "InvokeResult{" +
                "success=" + success +
                ", message='" + message + '\'' +
                ", response='" + response + '\'' +
                '}';
    }
}
