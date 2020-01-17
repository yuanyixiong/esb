package com.sinoif.esb.enums;

public enum ResponseState {

    SUCCESS(1, "请求成功"),
    FAIL(-1, "请求失败");

    /**
     * 代码
     */
    Integer code;
    /**
     * 信息
     */
    String message;

    ResponseState(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * 通过代码获取枚举项
     *
     * @param code
     * @return
     */
    public static ResponseState getByCode(Integer code) {
        if (code == null) {
            return null;
        }
        for (ResponseState responseCode : ResponseState.values()) {
            if (responseCode.getCode().equals(code)) {
                return responseCode;
            }
        }
        return null;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
