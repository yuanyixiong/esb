package com.sinoif.esb.enums;

/**
 * <p></p>
 *
 * @author chenxj
 * @date 2019/10/9
 */
public enum TypeActiveEnum {
    /**
     *
     */
    INITIATIVE ("INITIATIVE", "主动"),
    REACTIVE ("REACTIVE", "被动"),
    SYNC_CALL ("SYNC_CALL", "直接调用"),
    ;

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
    public static TypeActiveEnum getByCode(String code) {
        if (code == null) {
            return null;
        }
        for (TypeActiveEnum responseCode : TypeActiveEnum.values()) {
            if (responseCode.getCode().equals(code)) {
                return responseCode;
            }
        }
        return null;
    }

    TypeActiveEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }}
