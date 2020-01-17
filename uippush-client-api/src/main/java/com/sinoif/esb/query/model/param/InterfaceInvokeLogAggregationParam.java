package com.sinoif.esb.query.model.param;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 袁毅雄
 * @description 系统入参Bean-接口调用日志、异常接口日志 聚合统计参数
 * @date 2019/11/1
 */
public class InterfaceInvokeLogAggregationParam implements Serializable {

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

    public InterfaceInvokeLogAggregationParam setInvokeBeginTime(Date invokeBeginTime) {
        this.invokeBeginTime = invokeBeginTime;
        return this;
    }

    public Date getInvokeEndTime() {
        return invokeEndTime;
    }

    public InterfaceInvokeLogAggregationParam setInvokeEndTime(Date invokeEndTime) {
        this.invokeEndTime = invokeEndTime;
        return this;
    }

    public String getInterfaceName() {
        return interfaceName;
    }

    public InterfaceInvokeLogAggregationParam setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
        return this;
    }

    public Long getLastId() {
        return lastId;
    }

    public InterfaceInvokeLogAggregationParam setLastId(Long lastId) {
        this.lastId = lastId;
        return this;
    }
}
