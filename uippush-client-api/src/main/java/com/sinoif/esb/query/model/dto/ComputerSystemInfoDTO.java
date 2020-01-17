package com.sinoif.esb.query.model.dto;

import java.io.Serializable;

/**
 * 计算机系统信息
 */
public class ComputerSystemInfoDTO implements Serializable {

    /**
     * 制造商
     */
    private String manufacturer;

    /**
     * 模型
     */
    private String model;

    /**
     * 序号，编号
     */
    private String serialNumber;

    /**
     * 软件
     */
    private FirmwareDTO firmware;

    /**
     * 基线板
     */
    private BaseboardDTO baseboard;

    public String getManufacturer() {
        return manufacturer;
    }

    public ComputerSystemInfoDTO setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
        return this;
    }

    public String getModel() {
        return model;
    }

    public ComputerSystemInfoDTO setModel(String model) {
        this.model = model;
        return this;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public ComputerSystemInfoDTO setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
        return this;
    }

    public FirmwareDTO getFirmware() {
        return firmware;
    }

    public ComputerSystemInfoDTO setFirmware(FirmwareDTO firmware) {
        this.firmware = firmware;
        return this;
    }

    public BaseboardDTO getBaseboard() {
        return baseboard;
    }

    public ComputerSystemInfoDTO setBaseboard(BaseboardDTO baseboard) {
        this.baseboard = baseboard;
        return this;
    }
}
