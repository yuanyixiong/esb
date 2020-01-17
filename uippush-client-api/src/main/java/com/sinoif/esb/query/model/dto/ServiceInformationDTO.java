package com.sinoif.esb.query.model.dto;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;
import java.util.Date;

public class ServiceInformationDTO implements Serializable {

    @JSONField(name = "_id")
    private String id;

    @JSONField(format = "yyyy-MM-dd HH:mm:ss", name = "replyTime")
    private Date replyTime;

    private boolean survive;

    public String getId() {
        return id;
    }

    public ServiceInformationDTO setId(String id) {
        this.id = id;
        return this;
    }

    public Date getReplyTime() {
        return replyTime;
    }

    public ServiceInformationDTO setReplyTime(Date replyTime) {
        this.replyTime = replyTime;
        return this;
    }

    public boolean isSurvive() {
        return survive;
    }

    public ServiceInformationDTO setSurvive(boolean survive) {
        this.survive = survive;
        return this;
    }
}
