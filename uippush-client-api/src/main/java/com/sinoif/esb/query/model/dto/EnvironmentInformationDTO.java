package com.sinoif.esb.query.model.dto;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;

public class EnvironmentInformationDTO implements Serializable {

    @JSONField(name = "_id")
    private String id;

    @JSONField(name = "_time")
    private String time;

    @JSONField(name = "_environmentInformation")
    private EnvironmentDTO environment;

    public String getId() {
        return id;
    }

    public EnvironmentInformationDTO setId(String id) {
        this.id = id;
        return this;
    }

    public String getTime() {
        return time;
    }

    public EnvironmentInformationDTO setTime(String time) {
        this.time = time;
        return this;
    }

    public EnvironmentDTO getEnvironment() {
        return environment;
    }

    public EnvironmentInformationDTO setEnvironment(EnvironmentDTO environment) {
        this.environment = environment;
        return this;
    }
}
