package com.sinoif.esb.query.model.dto;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;

public class InterfaceInvokeExceptionDto implements Serializable {

    /**
     * 接口id
     */
    @JSONField(name ="interface_id")
    private String interfaceId;

    /**
     * 接口地址
      */
    @JSONField(name ="interface_address")
    private String interfaceAddress;

    /**
     * 接口名称
     */
    @JSONField(name ="interface_name")
    private String interfaceName;

    /**
     * 输出系统
     */
    @JSONField(name = "output_system")
    private String outputSystem;

    /**
     * 源系统
     */
    @JSONField(name="input_system")
    private String inputSystem;

    /**
     * 异常数量
     */
    @JSONField(name="cnt")
    private long count;

    public String getInterfaceAddress() {
        return interfaceAddress;
    }

    public void setInterfaceAddress(String interfaceAddress) {
        this.interfaceAddress = interfaceAddress;
    }

    public String getInterfaceName() {
        return interfaceName;
    }

    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }

    public String getOutputSystem() {
        return outputSystem;
    }

    public void setOutputSystem(String outputSystem) {
        this.outputSystem = outputSystem;
    }

    public String getInputSystem() {
        return inputSystem;
    }

    public void setInputSystem(String inputSystem) {
        this.inputSystem = inputSystem;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public String getInterfaceId() {
        return interfaceId;
    }

    public void setInterfaceId(String interfaceId) {
        this.interfaceId = interfaceId;
    }
}
