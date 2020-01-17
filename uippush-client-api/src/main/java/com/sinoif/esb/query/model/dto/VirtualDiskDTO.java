package com.sinoif.esb.query.model.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.ToStringSerializer;

import java.io.Serializable;


/**
 * 虚拟磁盘
 */
public class VirtualDiskDTO implements Serializable {

    /**
     * 名称
     */
    private String name;

    /**
     * 描述
     */
    private String description;

    /**
     * 磁盘类型
     */
    private String type;

    /**
     * 可用空间 单位Byte
     */
    @JSONField(serializeUsing = ToStringSerializer.class)
    private Long usableSpaceByte;

    /**
     * 可用空间 单位Gib
     */
    private String usableSpaceGib;

    /**
     * 全部空间 单位Byte
     */
    @JSONField(serializeUsing = ToStringSerializer.class)
    private Long totalSpaceByte;

    /**
     * 全部空间 单位Gib
     */
    private String totalSpaceGib;

    /**
     * 可用空间占比
     */
    @JSONField(serializeUsing = ToStringSerializer.class)
    private Double available;

    /**
     * 物理位置
     */
    private String volume;

    /**
     * 逻辑位置
     */
    private String logicalVolume;

    /**
     * 磁盘位置
     */
    private String mount;


    public String getName() {
        return name;
    }

    public VirtualDiskDTO setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public VirtualDiskDTO setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getType() {
        return type;
    }

    public VirtualDiskDTO setType(String type) {
        this.type = type;
        return this;
    }

    public Long getUsableSpaceByte() {
        return usableSpaceByte;
    }

    public VirtualDiskDTO setUsableSpaceByte(Long usableSpaceByte) {
        this.usableSpaceByte = usableSpaceByte;
        return this;
    }

    public String getUsableSpaceGib() {
        return usableSpaceGib;
    }

    public VirtualDiskDTO setUsableSpaceGib(String usableSpaceGib) {
        this.usableSpaceGib = usableSpaceGib;
        return this;
    }

    public Long getTotalSpaceByte() {
        return totalSpaceByte;
    }

    public VirtualDiskDTO setTotalSpaceByte(Long totalSpaceByte) {
        this.totalSpaceByte = totalSpaceByte;
        return this;
    }

    public String getTotalSpaceGib() {
        return totalSpaceGib;
    }

    public VirtualDiskDTO setTotalSpaceGib(String totalSpaceGib) {
        this.totalSpaceGib = totalSpaceGib;
        return this;
    }

    public Double getAvailable() {
        return available;
    }

    public VirtualDiskDTO setAvailable(Double available) {
        this.available = available;
        return this;
    }

    public String getVolume() {
        return volume;
    }

    public VirtualDiskDTO setVolume(String volume) {
        this.volume = volume;
        return this;
    }

    public String getLogicalVolume() {
        return logicalVolume;
    }

    public VirtualDiskDTO setLogicalVolume(String logicalVolume) {
        this.logicalVolume = logicalVolume;
        return this;
    }

    public String getMount() {
        return mount;
    }

    public VirtualDiskDTO setMount(String mount) {
        this.mount = mount;
        return this;
    }
}
