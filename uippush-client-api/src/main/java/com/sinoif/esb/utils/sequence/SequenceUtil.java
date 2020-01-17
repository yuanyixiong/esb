package com.sinoif.esb.utils.sequence;

/**
 * @author 袁毅雄
 * @description 雪花算法 twitter的snowflake算法 -- java实现 协议格式： 0 - 41位时间戳 - 5位数据中心标识 - 5位机器标识 - 12位序列号
 * @date 2019/10/30
 */
public class SequenceUtil {

    private static SnowFlake SNOW_FLAKE;

    public static Long getId() {
        return SNOW_FLAKE.nextId();
    }

    static {
        SNOW_FLAKE = new SnowFlake(2, 3);
    }
}

