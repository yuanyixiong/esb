package com.sinoif.esb.query.model.dto;

import java.io.Serializable;

/**
 * 中央处理器
 */
public class CentralProcessorDTO implements Serializable {

    /**
     * 物理CPU
     */
    private String physicalProcessorCount;

    /**
     * 逻辑CPU
     */
    private String logicalProcessorCount;

    /**
     * CPU识别码
     */
    private String identifier;

    /**
     * 处理器Id
     */
    private String processorID;

    public String getPhysicalProcessorCount() {
        return physicalProcessorCount;
    }

    public CentralProcessorDTO setPhysicalProcessorCount(String physicalProcessorCount) {
        this.physicalProcessorCount = physicalProcessorCount;
        return this;
    }

    public String getLogicalProcessorCount() {
        return logicalProcessorCount;
    }

    public CentralProcessorDTO setLogicalProcessorCount(String logicalProcessorCount) {
        this.logicalProcessorCount = logicalProcessorCount;
        return this;
    }

    public String getIdentifier() {
        return identifier;
    }

    public CentralProcessorDTO setIdentifier(String identifier) {
        this.identifier = identifier;
        return this;
    }

    public String getProcessorID() {
        return processorID;
    }

    public CentralProcessorDTO setProcessorID(String processorID) {
        this.processorID = processorID;
        return this;
    }
}
