package com.sinoif.esb.query.model.bo;


import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Objects;

/**
 * @author 袁毅雄
 * @description 系统业务Bean-接口调用日志、异常接口日志 聚合统计参数
 * @date 2019/11/1
 */
public class InterfaceInvokeLogAggregationBO implements Serializable {


    /**
     * 接口开始调用时间
     */
    private Date invokeBeginTime;

    /**
     * 接口结束调用时间
     */
    private Date invokeEndTime;

    /**
     * 接口名称
     */
    private String interfaceName;

    /**
     * 瀑布流分页最后一个
     */
    private Long lastId;


    public Date getInvokeBeginTime() {
        return invokeBeginTime;
    }

    public InterfaceInvokeLogAggregationBO setInvokeBeginTime(Date invokeBeginTime) {
        this.invokeBeginTime = invokeBeginTime;
        return this;
    }

    public Date getInvokeEndTime() {
        return invokeEndTime;
    }

    public InterfaceInvokeLogAggregationBO setInvokeEndTime(Date invokeEndTime) {
        this.invokeEndTime = invokeEndTime;
        return this;
    }

    public String getInterfaceName() {
        return interfaceName;
    }

    public InterfaceInvokeLogAggregationBO setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
        return this;
    }

    public Long getLastId() {
        return lastId;
    }

    public InterfaceInvokeLogAggregationBO setLastId(Long lastId) {
        this.lastId = lastId;
        return this;
    }
}
