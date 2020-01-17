package com.sinoif.esb.enums;

/**
 * <p></p>
 *
 * @author chenxj
 * @date 2019/10/9
 */
public enum TypeSyncEnum {
    /**
     * 是否同步（1-同步 2-异步）
     */
    ASYNC("2", "异步"),
    SYNC("1", "同步");

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
    public static TypeSyncEnum getByCode(String code) {
        if (code == null) {
            return null;
        }
        for (TypeSyncEnum responseCode : TypeSyncEnum.values()) {
            if (responseCode.getCode().equals(code)) {
                return responseCode;
            }
        }
        return null;
    }

    TypeSyncEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }}
