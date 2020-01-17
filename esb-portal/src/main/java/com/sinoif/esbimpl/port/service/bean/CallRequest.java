package com.sinoif.esbimpl.port.service.bean;

import com.sinoif.esb.port.bean.Interface;

import java.util.LinkedHashMap;

/**
 * <p></p>
 *
 * @author chenxj
 * @date 2019/10/22
 */
public class CallRequest {
    /**
     * 接口ID
     */
    private long interfaceId;

    /**
     * 请求服务
     */
    private String service;
    /**
     * 应用编码
     */
    private String appCode;

    /**
     * 参数
     */
    private LinkedHashMap<String, String> data;

    private Interface itf;

    public long getInterfaceId() {
        return interfaceId;
    }

    public void setInterfaceId(long interfaceId) {
        this.interfaceId = interfaceId;
    }

    public String getAppCode() {
        return appCode;
    }

    public void setAppCode(String appCode) {
        this.appCode = appCode;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public LinkedHashMap<String, String> getData() {
        if (data == null) {
            data = new LinkedHashMap<>(1);
        }
        return data;
    }

    public Interface getItf() {
        return itf;
    }

    public void setItf(Interface itf) {
        this.itf = itf;
    }

    public void setData(LinkedHashMap<String, String> data) {
        this.data = data;
    }
}
