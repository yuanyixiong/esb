package com.sinoif.esb.constants;

public class CoreConstants {
    // mongodb consumer对应的topic名称
    public final static String MONGO_TOPIC = "mongo";
    // 增量更新策略参数名称
    public final static String INCREMENTAL_POLICY = "INCREMENTAL_POLICY";

    public final static String INCREMENTAL_POLICY_PARAM_NAME = "INCREMENTAL_POLICY_PARAM_NAME";
    public final static String INCREMENTAL_POLICY_PARAM_VALUE_EXPRESSION = "INCREMENTAL_POLICY_PARAM_VALUE_EXPRESSION";
    public final static String INCREMENTAL_POLICY_PARAM_VALUE_EXPRESSION_ALL = "INCREMENTAL_POLICY_PARAM_VALUE_EXPRESSION_ALL";

    public final static String INCREMENTAL_POLICY_FIELD = "INCREMENTAL_POLICY_FIELD";
    // 统计最后更新时间的kafka stream 名称
    public final static String MAX_DATE_STREAM = "max_date_stream";

    public final static String DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
}
