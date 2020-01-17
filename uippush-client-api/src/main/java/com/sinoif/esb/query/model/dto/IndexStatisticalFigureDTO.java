package com.sinoif.esb.query.model.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.ToStringSerializer;

import java.io.Serializable;
import java.util.Date;

/**
 * 首页报表
 */
public class IndexStatisticalFigureDTO implements Serializable {

    @JSONField(format = "yyyy-MM-dd")
    private Date day;

    @JSONField(serializeUsing = ToStringSerializer.class)
    private Long number;

    public Date getDay() {
        return day;
    }

    public IndexStatisticalFigureDTO setDay(Date day) {
        this.day = day;
        return this;
    }

    public Long getNumber() {
        return number;
    }

    public IndexStatisticalFigureDTO setNumber(Long number) {
        this.number = number;
        return this;
    }
}
