package com.sinoif.esb.query.model.dto;

import java.io.Serializable;

/**
 * 基础dto，包含一些基字段
 */
public class BaseboardDTO implements Serializable {
    /**
     * 制造商
     */
    private String manufacturer;

    /**
     * 模型
     */
    private String model;

    /**
     * 版本
     */
    private String version;

    /**
     * 序号，编号
     */
    private String serialNumber;

    public String getManufacturer() {
        return manufacturer;
    }

    public BaseboardDTO setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
        return this;
    }

    public String getModel() {
        return model;
    }

    public BaseboardDTO setModel(String model) {
        this.model = model;
        return this;
    }

    public String getVersion() {
        return version;
    }

    public BaseboardDTO setVersion(String version) {
        this.version = version;
        return this;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public BaseboardDTO setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
        return this;
    }
}
