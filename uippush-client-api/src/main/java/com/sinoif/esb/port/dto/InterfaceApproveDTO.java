package com.sinoif.esb.port.dto;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;

/**
 * 待审核结口列表数据
 */
public class InterfaceApproveDTO implements Serializable {

    // 待审核记录自增id
    private String _id;

    // 是否审核
    private boolean approved;

    // 待审核记录的数据（json格式）
    private String data;

    // 待审核记录产生日期
    private String _time;

    // 接口id
    private long interfaceId;

    /**
     * 输出系统
     */
    @JSONField(name = "output_system")
    private String outputSystem;

    /**
     * 输入系统
     */
    @JSONField(name="input_system")
    private String inputSystem;

    /**
     * 接口名称
     */
    @JSONField(name ="interface_name")
    private String interfaceName;

    /**
     * 数量
     */
    @JSONField(name="cnt")
    private long count;

    @JSONField(name = "_id")
    public String get_id() {
        return _id;
    }

    @JSONField(name = "_id")
    public void set_id(String _id) {
        this._id = _id;
    }

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @JSONField(name = "_time")
    public String get_time() {
        return _time;
    }

    @JSONField(name = "_time")
    public void set_time(String _time) {
        this._time = _time;
    }

    @JSONField(name = "interface_id")
    public long getInterfaceId() {
        return interfaceId;
    }

    @JSONField(name = "interface_id")
    public void setInterfaceId(long interfaceId) {
        this.interfaceId = interfaceId;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
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

    public String getInterfaceName() {
        return interfaceName;
    }

    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }
}
