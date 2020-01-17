package com.sinoif.esb.query.model.bo;

import java.io.Serializable;

/**
 * 接口调用日志聚合查询结果bean, 封装接口日志主表数据
 */
public class InterfaceInvokeLogAggregationInfoBO implements Serializable {

    private Long interfaceId;

    private Long lastId;

    public Long getLastId() {
        return lastId;
    }

    public void setLastId(Long lastId) {
        this.lastId = lastId;
    }

    public Long getInterfaceId() {
        return interfaceId;
    }

    public void setInterfaceId(Long interfaceId) {
        this.interfaceId = interfaceId;
    }
}
