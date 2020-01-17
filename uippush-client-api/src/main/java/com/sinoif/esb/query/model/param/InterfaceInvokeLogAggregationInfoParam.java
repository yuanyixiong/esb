package com.sinoif.esb.query.model.param;

import java.io.Serializable;

/**
 * 接口调用日志聚合查询参数
 */
public class InterfaceInvokeLogAggregationInfoParam implements Serializable {

    private Long interfaceId;

    private Long lastId;

    public Long getLastId() {
        return lastId;
    }

    public InterfaceInvokeLogAggregationInfoParam setLastId(Long lastId) {
        this.lastId = lastId;
        return this;
    }

    public Long getInterfaceId() {
        return interfaceId;
    }

    public InterfaceInvokeLogAggregationInfoParam setInterfaceId(Long interfaceId) {
        this.interfaceId = interfaceId;
        return this;
    }

}
