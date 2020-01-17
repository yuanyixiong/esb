package com.sinoif.esb.query.model.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.ToStringSerializer;

import java.io.Serializable;

/**
 * 内存
 */
public class MemoryDTO implements Serializable {

    /**
     * 总内存
     */
    @JSONField(serializeUsing = ToStringSerializer.class)
    private Long total;
    /**
     * 可用内存
     */
    @JSONField(serializeUsing = ToStringSerializer.class)
    private Long available;

    /**
     * 交换空间总内存
     */
    @JSONField(serializeUsing = ToStringSerializer.class)
    private Long swapTotal;

    /**
     * 交换空间总内存
     */
    @JSONField(serializeUsing = ToStringSerializer.class)
    private Long swapUsed;

    public Long getTotal() {
        return total;
    }

    public MemoryDTO setTotal(Long total) {
        this.total = total;
        return this;
    }

    public Long getAvailable() {
        return available;
    }

    public MemoryDTO setAvailable(Long available) {
        this.available = available;
        return this;
    }

    public Long getSwapTotal() {
        return swapTotal;
    }

    public MemoryDTO setSwapTotal(Long swapTotal) {
        this.swapTotal = swapTotal;
        return this;
    }

    public Long getSwapUsed() {
        return swapUsed;
    }

    public MemoryDTO setSwapUsed(Long swapUsed) {
        this.swapUsed = swapUsed;
        return this;
    }
}
