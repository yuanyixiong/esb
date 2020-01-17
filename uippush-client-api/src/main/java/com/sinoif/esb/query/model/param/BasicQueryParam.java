package com.sinoif.esb.query.model.param;

import java.io.Serializable;

/**
 * 基础查询参数
 */
public class BasicQueryParam implements Serializable {

    /**
     * 接口topic
     */
    private String topic;

    /**
     * 接口id
     */
    private long interfaceId;

    /**
     * 当前页
     */
    int page;

    /**
     * 分页数量
     */
    private int pageSize;

    public long getInterfaceId() {
        return interfaceId;
    }

    public void setInterfaceId(long interfaceId) {
        this.interfaceId = interfaceId;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }
}
