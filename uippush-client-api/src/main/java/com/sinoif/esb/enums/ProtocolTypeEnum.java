package com.sinoif.esb.enums;

/**
 * <p></p>
 *
 * @author chenxj
 * @date 2019/10/9
 */
public enum ProtocolTypeEnum {
    /**
     * HTTP HTTPS
     */
    HTTP("http", "HTTP"),
    HTTPS("https", "HTTPS");

    /**
     * 代码
     */
    private final String code;
    /**
     * 信息
     */
    private final String message;

    /**
     * 通过代码获取枚举项
     *
     * @param code
     * @return
     */
    public static ProtocolTypeEnum getByCode(String code) {
        if (code == null) {
            return null;
        }
        for (ProtocolTypeEnum responseCode : ProtocolTypeEnum.values()) {
            if (responseCode.getCode().equals(code)) {
                return responseCode;
            }
        }
        return null;
    }

    ProtocolTypeEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }}
