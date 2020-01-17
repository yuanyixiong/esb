package com.sinoif.esb.enums;

/**
 * <p></p>
 *
 * @author chenxj
 * @date 2019/10/9
 */
public enum ProtocolEnum {
    /**
     * 10 RestApi 20 WebService 30 Socket 40 MQ'
     */
    REST_API("10", "RestApi"),
    WEBSERVICE("20", "WebService");

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
    public static ProtocolEnum getByCode(String code) {
        if (code == null) {
            return null;
        }
        for (ProtocolEnum responseCode : ProtocolEnum.values()) {
            if (responseCode.getCode().equals(code)) {
                return responseCode;
            }
        }
        return null;
    }

    ProtocolEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }}
