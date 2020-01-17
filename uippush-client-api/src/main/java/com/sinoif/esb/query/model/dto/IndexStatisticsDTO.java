package com.sinoif.esb.query.model.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.ToStringSerializer;

import java.io.Serializable;

/**
 * 首页统计
 */
public class IndexStatisticsDTO implements Serializable {

    /**
     * 在线接口：当月活动的接口数量
     */
    @JSONField(serializeUsing = ToStringSerializer.class)
    private Long onLineNumber;

    /**
     * 瞬时数据：当天全部接口日志数量
     */
    @JSONField(serializeUsing = ToStringSerializer.class)
    private Long instantNumber;

    /**
     * 总数据量：历史所有数量统计
     */
    @JSONField(serializeUsing = ToStringSerializer.class)
    private Long totalNumber;

    /***本周***/
    /**
     * 本周总数
     */
    @JSONField(serializeUsing = ToStringSerializer.class)
    private Long thisWeekTotalNumber;

    /**
     * 本周环比上周浮动
     */
    @JSONField(serializeUsing = ToStringSerializer.class)
    private Double thisWeekFloatNumber;

    /***本月***/
    /**
     * 本月总数
     */
    @JSONField(serializeUsing = ToStringSerializer.class)
    private Long thisMonthTotalNumber;

    /**
     * 本月环比上月浮动
     */
    @JSONField(serializeUsing = ToStringSerializer.class)
    private Double thisMonthFloatNumber;


    /***上周***/
    /**
     * 上周总数
     */
    @JSONField(serializeUsing = ToStringSerializer.class)
    private Long lastWeekTotalNumber;

    /**
     * 上周环比上上周浮动
     */
    @JSONField(serializeUsing = ToStringSerializer.class)
    private Double lastWeekFloatNumber;

    /***上月***/
    /**
     * 上月总数
     */
    @JSONField(serializeUsing = ToStringSerializer.class)
    private Long lastMonthTotalNumber;

    /**
     * 上月环比上上月浮动
     */
    @JSONField(serializeUsing = ToStringSerializer.class)
    private Double lastMonthFloatNumber;

    public Long getOnLineNumber() {
        return onLineNumber;
    }

    public IndexStatisticsDTO setOnLineNumber(Long onLineNumber) {
        this.onLineNumber = onLineNumber;
        return this;
    }

    public Long getInstantNumber() {
        return instantNumber;
    }

    public IndexStatisticsDTO setInstantNumber(Long instantNumber) {
        this.instantNumber = instantNumber;
        return this;
    }

    public Long getTotalNumber() {
        return totalNumber;
    }

    public IndexStatisticsDTO setTotalNumber(Long totalNumber) {
        this.totalNumber = totalNumber;
        return this;
    }

    public Long getThisWeekTotalNumber() {
        return thisWeekTotalNumber;
    }

    public IndexStatisticsDTO setThisWeekTotalNumber(Long thisWeekTotalNumber) {
        this.thisWeekTotalNumber = thisWeekTotalNumber;
        return this;
    }

    public Double getThisWeekFloatNumber() {
        return thisWeekFloatNumber;
    }

    public IndexStatisticsDTO setThisWeekFloatNumber(Double thisWeekFloatNumber) {
        this.thisWeekFloatNumber = thisWeekFloatNumber;
        return this;
    }

    public Long getThisMonthTotalNumber() {
        return thisMonthTotalNumber;
    }

    public IndexStatisticsDTO setThisMonthTotalNumber(Long thisMonthTotalNumber) {
        this.thisMonthTotalNumber = thisMonthTotalNumber;
        return this;
    }

    public Double getThisMonthFloatNumber() {
        return thisMonthFloatNumber;
    }

    public IndexStatisticsDTO setThisMonthFloatNumber(Double thisMonthFloatNumber) {
        this.thisMonthFloatNumber = thisMonthFloatNumber;
        return this;
    }

    public Long getLastWeekTotalNumber() {
        return lastWeekTotalNumber;
    }

    public IndexStatisticsDTO setLastWeekTotalNumber(Long lastWeekTotalNumber) {
        this.lastWeekTotalNumber = lastWeekTotalNumber;
        return this;
    }

    public Double getLastWeekFloatNumber() {
        return lastWeekFloatNumber;
    }

    public IndexStatisticsDTO setLastWeekFloatNumber(Double lastWeekFloatNumber) {
        this.lastWeekFloatNumber = lastWeekFloatNumber;
        return this;
    }

    public Long getLastMonthTotalNumber() {
        return lastMonthTotalNumber;
    }

    public IndexStatisticsDTO setLastMonthTotalNumber(Long lastMonthTotalNumber) {
        this.lastMonthTotalNumber = lastMonthTotalNumber;
        return this;
    }

    public Double getLastMonthFloatNumber() {
        return lastMonthFloatNumber;
    }

    public IndexStatisticsDTO setLastMonthFloatNumber(Double lastMonthFloatNumber) {
        this.lastMonthFloatNumber = lastMonthFloatNumber;
        return this;
    }
}
