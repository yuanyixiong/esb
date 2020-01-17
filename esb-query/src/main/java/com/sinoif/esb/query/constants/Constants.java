package com.sinoif.esb.query.constants;

/**
 * @author 袁毅雄
 * @description 系统常量类
 * @date 2019/11/1
 */
public interface Constants {

    /**
     * 格式常量
     */
    interface FormatterConstants {

        /**
         * 时间格式
         */
        String TIME_FORMATTER = "yyyy-MM-dd HH:mm:ss";

    }

    /**
     * MongoDb 数据存储常量
     */
    interface MongoDbConstants {

        /**
         * mongodb接口调用记录
         */
        String MONGO_COLLECTION = "invoke_log";

    }
}
