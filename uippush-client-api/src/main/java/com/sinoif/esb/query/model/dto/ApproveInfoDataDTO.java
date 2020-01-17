package com.sinoif.esb.query.model.dto;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;

/**
 * <p>待审核数据明细</p>
 *
 * @author chenxjuc
 * @date 2019/11/11
 */
public class ApproveInfoDataDTO implements Serializable {

    @JSONField(name = "_id")
    private String id;

    /***
     * 待审核数据 JSON格式
     */
    @JSONField(name = "data")
    private Object data;

    @JSONField(name = "_parent")
    private String parent;

    @JSONField(name = "_interface_id")
    private String interfaceId;

    @JSONField(name = "_topic")
    private String topic;

    @JSONField(name = "_approve_status")
    private String approveStatus;

    /**
     * 审核时间
     */
    @JSONField(name = "_approve_time", format = "yyyy-MM-dd HH:mm:ss")
    private String approveTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public String getInterfaceId() {
        return interfaceId;
    }

    public void setInterfaceId(String interfaceId) {
        this.interfaceId = interfaceId;
    }

    public String getApproveStatus() {
        return approveStatus;
    }

    public void setApproveStatus(String approveStatus) {
        this.approveStatus = approveStatus;
    }

    public String getApproveTime() {
        return approveTime;
    }

    public void setApproveTime(String approveTime) {
        this.approveTime = approveTime;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }
}
