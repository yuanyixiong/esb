package com.sinoif.esb.enums;

public enum RequestType {
    /**
     *
     */
    POST("post"),
    GET("get");

    String requestTypeName;

    RequestType(String requestTypeName) {
        this.requestTypeName = requestTypeName;
    }

    /**
     * 通过代码获取枚举项
     *
     * @param code
     * @return
     */
    public static RequestType getByCode(String code) {
        if (code == null) {
            return null;
        }
        for (RequestType responseCode : RequestType.values()) {
            if (responseCode.getRequestTypeName().equals(code)) {
                return responseCode;
            }
        }
        return null;
    }

    public String getRequestTypeName() {
        return requestTypeName;
    }
}
