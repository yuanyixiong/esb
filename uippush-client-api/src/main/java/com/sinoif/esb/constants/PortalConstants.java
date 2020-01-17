package com.sinoif.esb.constants;

/**
 * <p></p>
 *
 * @author chenxj
 * @date 2019/10/23
 */
public interface PortalConstants {
    String ITF_NOT_EXITS = "接口不存在";
    String ITF_ID_IS_EMPTY = "接口ID不能为空";
    String PARAM_ERROR = "参数参数不正确";
    String SERVICE_NOT_SUPPORT = "不支持方法";
    String UNKNOWN_ERROR = "请求失败";
    String ITF_TYPE_ERROR = "执行接口类型错误";
    String KEY_PROPERTY_ERROR = "key property不存在";
    String DATA_APPROVED = "数据不存在或已审核";

    /**
     * collection名常量
     */
    String COLLECTION_INVOKE_LOG = "invoke_log";
    String COLLECTION_INVOKE_EXCEPTION = "invoke_exception";
    String COLLECTION_INVOKE_LOG_DATA = "invoke_log_data";
    String COLLECTION_APPROVE_INFO = "interface_approve_info";
    String COLLECTION_APPROVE_INFO_DATA = "interface_approve_info_data";

    /**
     * 字段常量
     */
    String ID_INVOKE_LOG = "_id";
    String COL_APPROVE = "_approve_status";
    String COL_APPROVE_TIME = "_approve_time";
    String HANDLED = "handled";
    String TIME = "_time";
    String PARENT="_parent";
    String INTERFACE_ID = "_interface_id";
    String TOPIC = "_topic";


    /**
     * 字段常量
     */
    String APPROVED = "approved";
    String REJECTED = "rejected";
    String NOT_PROCESSED = "not_processed";

    /**
     * 时间格式
     */
    String TIME_FORMATTER = "yyyy-MM-dd HH:mm:ss";
}
