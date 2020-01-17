package com.sinoif.esb.query.model.dto;

import java.io.Serializable;

/**
 * 传感器
 */
public class SensorsDTO implements Serializable {

    /**
     * CPU温度
     */
    private String cpuTemperature;

    /**
     * 风扇速度
     */
    private String[] fanSpeeds;

    /**
     * CPU电压
     */
    private String cpuVoltage;

    public String getCpuTemperature() {
        return cpuTemperature;
    }

    public SensorsDTO setCpuTemperature(String cpuTemperature) {
        this.cpuTemperature = cpuTemperature;
        return this;
    }

    public String[] getFanSpeeds() {
        return fanSpeeds;
    }

    public SensorsDTO setFanSpeeds(String[] fanSpeeds) {
        this.fanSpeeds = fanSpeeds;
        return this;
    }

    public String getCpuVoltage() {
        return cpuVoltage;
    }

    public SensorsDTO setCpuVoltage(String cpuVoltage) {
        this.cpuVoltage = cpuVoltage;
        return this;
    }

}
