package com.sinoif.esb.query.model.dto;

import java.io.Serializable;

public class FirmwareDTO implements Serializable {

    /**
     * 制造商
     */
    private String manufacturer;

    /**
     * 名称
     */
    private String name;

    /**
     * 简介
     */
    private String description;

    /**
     * 版本
     */
    private String version;

    /**
     * 出厂日期
     */
    private String releaseDate;

    public String getManufacturer() {
        return manufacturer;
    }

    public FirmwareDTO setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
        return this;
    }

    public String getName() {
        return name;
    }

    public FirmwareDTO setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public FirmwareDTO setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getVersion() {
        return version;
    }

    public FirmwareDTO setVersion(String version) {
        this.version = version;
        return this;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public FirmwareDTO setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
        return this;
    }
}
