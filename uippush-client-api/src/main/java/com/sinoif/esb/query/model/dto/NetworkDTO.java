package com.sinoif.esb.query.model.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.ToStringSerializer;

import java.util.*;

import java.io.Serializable;

public class NetworkDTO implements Serializable {

    /**
     * 实际的网络名称
     */
    private String name;

    /**
     * 显示的网络名称
     */
    private String displayName;

    /**
     * 硬件地址、物理地址、MAC地址
     */
    private String macaddr;

    /**
     * 所能通过的最大数据包大小
     */
    @JSONField(serializeUsing = ToStringSerializer.class)
    private Integer mtu;

    /**
     * 传输速度 单位Byte
     */
    @JSONField(serializeUsing = ToStringSerializer.class)
    private Long speed;

    /**
     * 传输速度 单位bps
     */
    private String speedBps;

    /**
     * IPV4
     */
    private List<String> ipv4 = new ArrayList<>();

    /**
     * IPV6
     */
    private List<String> ipv6 = new ArrayList<>();

    /**
     * 接收的数据包
     */
    @JSONField(serializeUsing = ToStringSerializer.class)
    private Long receivePackets;

    /**
     * 接收的速度 单位Byte
     */
    @JSONField(serializeUsing = ToStringSerializer.class)
    private Long receiveBytes;

    /**
     * 接收的速度 单位MiB
     */
    private String receiveMib;

    /**
     * 接收发生的错误数
     */
    @JSONField(serializeUsing = ToStringSerializer.class)
    private Long receiveError;

    /**
     * 发送的包的总数
     */
    @JSONField(serializeUsing = ToStringSerializer.class)
    private Long sentPackets;

    /**
     * 发送的速度 单位Byte
     */
    @JSONField(serializeUsing = ToStringSerializer.class)
    private Long sentBytes;

    /**
     * 发送的速度 单位MiB
     */
    private String sentMib;

    /**
     * 发送发生的错误数
     */
    @JSONField(serializeUsing = ToStringSerializer.class)
    private Long sentError;

    public String getName() {
        return name;
    }

    public NetworkDTO setName(String name) {
        this.name = name;
        return this;
    }

    public String getDisplayName() {
        return displayName;
    }

    public NetworkDTO setDisplayName(String displayName) {
        this.displayName = displayName;
        return this;
    }

    public String getMacaddr() {
        return macaddr;
    }

    public NetworkDTO setMacaddr(String macaddr) {
        this.macaddr = macaddr;
        return this;
    }

    public Integer getMtu() {
        return mtu;
    }

    public NetworkDTO setMtu(Integer mtu) {
        this.mtu = mtu;
        return this;
    }

    public Long getSpeed() {
        return speed;
    }

    public NetworkDTO setSpeed(Long speed) {
        this.speed = speed;
        return this;
    }

    public String getSpeedBps() {
        return speedBps;
    }

    public NetworkDTO setSpeedBps(String speedBps) {
        this.speedBps = speedBps;
        return this;
    }

    public List<String> getIpv4() {
        return ipv4;
    }

    public NetworkDTO setIpv4(List<String> ipv4) {
        this.ipv4 = ipv4;
        return this;
    }

    public List<String> getIpv6() {
        return ipv6;
    }

    public NetworkDTO setIpv6(List<String> ipv6) {
        this.ipv6 = ipv6;
        return this;
    }

    public Long getReceivePackets() {
        return receivePackets;
    }

    public NetworkDTO setReceivePackets(Long receivePackets) {
        this.receivePackets = receivePackets;
        return this;
    }

    public Long getReceiveBytes() {
        return receiveBytes;
    }

    public NetworkDTO setReceiveBytes(Long receiveBytes) {
        this.receiveBytes = receiveBytes;
        return this;
    }

    public String getReceiveMib() {
        return receiveMib;
    }

    public NetworkDTO setReceiveMib(String receiveMib) {
        this.receiveMib = receiveMib;
        return this;
    }

    public Long getReceiveError() {
        return receiveError;
    }

    public NetworkDTO setReceiveError(Long receiveError) {
        this.receiveError = receiveError;
        return this;
    }

    public Long getSentPackets() {
        return sentPackets;
    }

    public NetworkDTO setSentPackets(Long sentPackets) {
        this.sentPackets = sentPackets;
        return this;
    }

    public Long getSentBytes() {
        return sentBytes;
    }

    public NetworkDTO setSentBytes(Long sentBytes) {
        this.sentBytes = sentBytes;
        return this;
    }

    public String getSentMib() {
        return sentMib;
    }

    public NetworkDTO setSentMib(String sentMib) {
        this.sentMib = sentMib;
        return this;
    }

    public Long getSentError() {
        return sentError;
    }

    public NetworkDTO setSentError(Long sentError) {
        this.sentError = sentError;
        return this;
    }
}
