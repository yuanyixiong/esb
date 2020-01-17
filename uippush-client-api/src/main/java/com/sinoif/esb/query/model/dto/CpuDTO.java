package com.sinoif.esb.query.model.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.ToStringSerializer;

import java.util.*;
import java.io.Serializable;

/**
 * CPU
 */
public class CpuDTO implements Serializable {

    /**
     * 正常运行时间
     */
    private String runTime;

    /**
     * CPU逻辑处理器的数量
     */
    @JSONField(serializeUsing = ToStringSerializer.class)
    private Integer logicalProcessorCount;

    /**
     * CPU物理处理器的数量
     */
    @JSONField(serializeUsing = ToStringSerializer.class)
    private Integer physicalProcessorCount;

    /**
     * cpu一秒的负载 ,监控开始
     */
    private List<String> beforeLoad = new ArrayList<>();

    /**
     * cpu一秒的负载 ,监控开始一秒后
     */
    private List<String> afterLoad = new ArrayList<>();

    /**
     * 负载明细
     */
    private Map<String, String> loadInfo = new HashMap<>();

    /**
     * CPU最近一段时间的负载
     */
    @JSONField(serializeUsing = ToStringSerializer.class)
    private String systemCpuLoadBetweenTicks;

    /**
     * CPU当前负载
     */
    @JSONField(serializeUsing = ToStringSerializer.class)
    private String systemCpuLoad;

    /**
     * CPU负载的平均值
     */
    private List<String> systemLoadAverage = new ArrayList<>();

    /**
     * CPU每个处理器的负载
     */
    private List<String> processorCpuLoadBetweenTicks = new ArrayList<>();


    public String getRunTime() {
        return runTime;
    }

    public CpuDTO setRunTime(String runTime) {
        this.runTime = runTime;
        return this;
    }

    public Integer getLogicalProcessorCount() {
        return logicalProcessorCount;
    }

    public CpuDTO setLogicalProcessorCount(Integer logicalProcessorCount) {
        this.logicalProcessorCount = logicalProcessorCount;
        return this;
    }

    public Integer getPhysicalProcessorCount() {
        return physicalProcessorCount;
    }

    public CpuDTO setPhysicalProcessorCount(Integer physicalProcessorCount) {
        this.physicalProcessorCount = physicalProcessorCount;
        return this;
    }

    public List<String> getBeforeLoad() {
        return beforeLoad;
    }

    public CpuDTO setBeforeLoad(List<String> beforeLoad) {
        this.beforeLoad = beforeLoad;
        return this;
    }

    public List<String> getAfterLoad() {
        return afterLoad;
    }

    public CpuDTO setAfterLoad(List<String> afterLoad) {
        this.afterLoad = afterLoad;
        return this;
    }

    public Map<String, String> getLoadInfo() {
        return loadInfo;
    }

    public CpuDTO setLoadInfo(Map<String, String> loadInfo) {
        this.loadInfo = loadInfo;
        return this;
    }

    public String getSystemCpuLoadBetweenTicks() {
        return systemCpuLoadBetweenTicks;
    }

    public CpuDTO setSystemCpuLoadBetweenTicks(String systemCpuLoadBetweenTicks) {
        this.systemCpuLoadBetweenTicks = systemCpuLoadBetweenTicks;
        return this;
    }

    public String getSystemCpuLoad() {
        return systemCpuLoad;
    }

    public CpuDTO setSystemCpuLoad(String systemCpuLoad) {
        this.systemCpuLoad = systemCpuLoad;
        return this;
    }

    public List<String> getSystemLoadAverage() {
        return systemLoadAverage;
    }

    public CpuDTO setSystemLoadAverage(List<String> systemLoadAverage) {
        this.systemLoadAverage = systemLoadAverage;
        return this;
    }

    public List<String> getProcessorCpuLoadBetweenTicks() {
        return processorCpuLoadBetweenTicks;
    }

    public CpuDTO setProcessorCpuLoadBetweenTicks(List<String> processorCpuLoadBetweenTicks) {
        this.processorCpuLoadBetweenTicks = processorCpuLoadBetweenTicks;
        return this;
    }
}

