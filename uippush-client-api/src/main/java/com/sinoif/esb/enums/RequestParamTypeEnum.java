package com.sinoif.esb.enums;

public enum RequestParamTypeEnum {
    /**
     *
     */
    JSON("JSON", "application/json", "RestApi"),
    FORMDATA("FORM_DATA", "表单", "RestApi"),
    XML("XML", "表单", "RestApi,Webservice");

    private String code;
    // 类型名称
    private String mimeName;
    // 类型适用接口类型
    private String interfaceType;

    RequestParamTypeEnum(String code, String mimeName, String interfaceType) {
        this.code = code;
        this.mimeName = mimeName;
        this.interfaceType = interfaceType;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMimeName() {
        return mimeName;
    }

    public void setMimeName(String mimeName) {
        this.mimeName = mimeName;
    }

    public String getInterfaceType() {
        return interfaceType;
    }

    public void setInterfaceType(String interfaceType) {
        this.interfaceType = interfaceType;
    }

    /**
     * 通过代码获取枚举项
     *
     * @param code
     * @return
     */
    public static RequestParamTypeEnum getByCode(String code) {
        if (code == null) {
            return null;
        }
        for (RequestParamTypeEnum responseCode : RequestParamTypeEnum.values()) {
            if (responseCode.getCode().equals(code)) {
                return responseCode;
            }
        }
        return null;
    }
}
