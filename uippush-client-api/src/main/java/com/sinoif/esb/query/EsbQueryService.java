package com.sinoif.esb.query;

public interface EsbQueryService {
    /**
     * 获取未审核的数据
     *
     * @param topic 数据对应对列的名称，interface中的topic
     * @return json数据类型的返回
     */
    String getUnapprovedData(String topic);
}
