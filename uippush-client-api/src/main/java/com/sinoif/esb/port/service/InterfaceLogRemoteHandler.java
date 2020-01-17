package com.sinoif.esb.port.service;

import com.alibaba.fastjson.JSONObject;
import com.sinoif.esb.port.bean.InvokeResult;
import com.sinoif.esb.port.dto.InterfaceApproveDTO;
import com.sinoif.esb.port.param.InterfaceApproveParam;

import java.util.List;

/**
 * 接口异常操作
 */
public interface InterfaceLogRemoteHandler {
    /**
     * 忽略异常
     *
     * @param id 异常记录id
     */
    void markComplete(long id);

    /**
     * 重试异常
     *
     * @param id 异常记录id
     */
    InvokeResult invokeRetry(long id);

    /**
     * 审核数据
     *
     * @param approveDataId 对应的审核数据id
     * @param isApproved 是否通过审核
     *
     * @return 输出接口调用结果
     */
    InvokeResult approveInterfaceData(long approveDataId,boolean isApproved);

    /**
     * 审核拒绝
     *
     * @param id
     */
    void approveReject(long id);

}
