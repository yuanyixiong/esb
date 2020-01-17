package com.sinoif.esb.query.model.dto;


import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.ToStringSerializer;

import java.io.Serializable;

public class PartitionDTO implements Serializable {

    /**
     * 分区标识
     */
    private String identification;

    /**
     * 分区的名称
     */
    private String name;

    /**
     * 分区的类型或描述
     */
    private String type;

    /**
     * 设备ID(主要)
     */
    @JSONField(serializeUsing = ToStringSerializer.class)
    private Integer major;

    /**
     * 设备ID(次要)
     */
    private Integer minor;

    /**
     * 分区大小 单位Byte
     */
    @JSONField(serializeUsing = ToStringSerializer.class)
    private Long sizeByte;

    /**
     * 分区大小 单位GB
     */
    private String sizeGB;

    /**
     * 分区安装位置
     */
    private String mountPoint;

    public String getIdentification() {
        return identification;
    }

    public PartitionDTO setIdentification(String identification) {
        this.identification = identification;
        return this;
    }

    public String getName() {
        return name;
    }

    public PartitionDTO setName(String name) {
        this.name = name;
        return this;
    }

    public String getType() {
        return type;
    }

    public PartitionDTO setType(String type) {
        this.type = type;
        return this;
    }

    public Integer getMajor() {
        return major;
    }

    public PartitionDTO setMajor(Integer major) {
        this.major = major;
        return this;
    }

    public Integer getMinor() {
        return minor;
    }

    public PartitionDTO setMinor(Integer minor) {
        this.minor = minor;
        return this;
    }

    public Long getSizeByte() {
        return sizeByte;
    }

    public PartitionDTO setSizeByte(Long sizeByte) {
        this.sizeByte = sizeByte;
        return this;
    }

    public String getSizeGB() {
        return sizeGB;
    }

    public PartitionDTO setSizeGB(String sizeGB) {
        this.sizeGB = sizeGB;
        return this;
    }

    public String getMountPoint() {
        return mountPoint;
    }

    public PartitionDTO setMountPoint(String mountPoint) {
        this.mountPoint = mountPoint;
        return this;
    }
}
