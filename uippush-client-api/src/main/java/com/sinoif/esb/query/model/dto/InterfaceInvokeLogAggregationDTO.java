package com.sinoif.esb.query.model.dto;

import java.io.Serializable;

public class InterfaceInvokeLogAggregationDTO extends InterfaceInvokeLogDTO implements Serializable {
    /**
     * 接口调用数
     */
    private Long cnt;

    public Long getCnt() {
        return cnt;
    }

    public void setCnt(Long cnt) {
        this.cnt = cnt;
    }
}
