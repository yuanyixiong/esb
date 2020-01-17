package com.sinoif.esb.query.model.param;

import java.util.ArrayList;
import java.util.Date;

/**
 * 查口预警信息查询参数
 */
public class InterfaceInvokeAlarmParam extends BasicQueryParam {
    /**
     * 要查询的接口id
     */
    ArrayList<String> interfaceIds;
    /**
     * 统计开始时间
     */
    Date beginTime;
    /**
     * 统计结束时间
     */
    Date endTime;

    public ArrayList<String> getInterfaceIds() {
        return interfaceIds;
    }

    public void setInterfaceIds(ArrayList<String> interfaceIds) {
        this.interfaceIds = interfaceIds;
    }

    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
}
