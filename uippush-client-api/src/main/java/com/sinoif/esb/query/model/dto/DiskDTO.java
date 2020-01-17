package com.sinoif.esb.query.model.dto;


import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.ToStringSerializer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 磁盘
 */
public class DiskDTO implements Serializable {


    /**
     * 磁盘名称
     */
    private String name;

    /**
     * 磁盘型号
     */
    private String mode;

    /**
     * 磁盘序列号
     */
    private String serial;

    /**
     * 磁盘大小  单位Byte
     */
    @JSONField(serializeUsing = ToStringSerializer.class)
    private Long sizeByte;

    /**
     * 磁盘大小  单位GB
     */
    private String sizeGB;

    /**
     * 磁盘读取的次数
     */
    @JSONField(serializeUsing = ToStringSerializer.class)
    private Long reads;

    /**
     * 从磁盘读取的字节数 单位Byte
     */
    @JSONField(serializeUsing = ToStringSerializer.class)
    private Long readBytes;

    /**
     * 从磁盘读取的字节数 单位GB
     */
    private String readGB;

    /**
     * 磁盘写入的次数
     */
    @JSONField(serializeUsing = ToStringSerializer.class)
    private Long writes;

    /**
     * 从磁盘写入的字节数 单位Byte
     */
    @JSONField(serializeUsing = ToStringSerializer.class)
    private Long writeBytes;

    /**
     * 从磁盘写入的字节数 单位GB
     */
    private String writeGB;

    /**
     * 读写磁盘所用的毫秒数
     */
    @JSONField(serializeUsing = ToStringSerializer.class)
    private Long transferTimeMs;

    /**
     * 读写磁盘所用的秒数
     */
    @JSONField(serializeUsing = ToStringSerializer.class)
    private Long transferTimeSeconds;

    /**
     * 磁盘的分区
     */
    private List<PartitionDTO> Partitions = new ArrayList<>();

    public String getName() {
        return name;
    }

    public DiskDTO setName(String name) {
        this.name = name;
        return this;
    }

    public String getMode() {
        return mode;
    }

    public DiskDTO setMode(String mode) {
        this.mode = mode;
        return this;
    }

    public String getSerial() {
        return serial;
    }

    public DiskDTO setSerial(String serial) {
        this.serial = serial;
        return this;
    }

    public Long getSizeByte() {
        return sizeByte;
    }

    public DiskDTO setSizeByte(Long sizeByte) {
        this.sizeByte = sizeByte;
        return this;
    }

    public String getSizeGB() {
        return sizeGB;
    }

    public DiskDTO setSizeGB(String sizeGB) {
        this.sizeGB = sizeGB;
        return this;
    }

    public Long getReads() {
        return reads;
    }

    public DiskDTO setReads(Long reads) {
        this.reads = reads;
        return this;
    }

    public Long getReadBytes() {
        return readBytes;
    }

    public DiskDTO setReadBytes(Long readBytes) {
        this.readBytes = readBytes;
        return this;
    }

    public String getReadGB() {
        return readGB;
    }

    public DiskDTO setReadGB(String readGB) {
        this.readGB = readGB;
        return this;
    }

    public Long getWrites() {
        return writes;
    }

    public DiskDTO setWrites(Long writes) {
        this.writes = writes;
        return this;
    }

    public Long getWriteBytes() {
        return writeBytes;
    }

    public DiskDTO setWriteBytes(Long writeBytes) {
        this.writeBytes = writeBytes;
        return this;
    }

    public String getWriteGB() {
        return writeGB;
    }

    public DiskDTO setWriteGB(String writeGB) {
        this.writeGB = writeGB;
        return this;
    }

    public Long getTransferTimeMs() {
        return transferTimeMs;
    }

    public DiskDTO setTransferTimeMs(Long transferTimeMs) {
        this.transferTimeMs = transferTimeMs;
        return this;
    }

    public Long getTransferTimeSeconds() {
        return transferTimeSeconds;
    }

    public DiskDTO setTransferTimeSeconds(Long transferTimeSeconds) {
        this.transferTimeSeconds = transferTimeSeconds;
        return this;
    }

    public List<PartitionDTO> getPartitions() {
        return Partitions;
    }

    public DiskDTO setPartitions(List<PartitionDTO> partitions) {
        Partitions = partitions;
        return this;
    }
}
