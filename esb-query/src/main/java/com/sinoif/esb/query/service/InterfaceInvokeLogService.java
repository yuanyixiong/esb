package com.sinoif.esb.query.service;


import com.alibaba.fastjson.JSONArray;
import com.sinoif.esb.query.model.bo.InterfaceInvokeLogAggregationBO;
import com.sinoif.esb.query.model.bo.InterfaceInvokeLogAggregationInfoBO;
import com.sinoif.esb.query.model.bo.InterfaceInvokeLogBO;
import com.sinoif.esb.query.model.dto.InterfaceInvokeLogAggregationDTO;
import com.sinoif.esb.query.model.dto.InterfaceInvokeLogDTO;
import com.sinoif.esb.query.page.PageData;

import java.util.List;

/**
 * @author 袁毅雄
 * @description 接口调用日志，查询接口调用情况
 * @date 2019/11/1
 */
public interface InterfaceInvokeLogService {

    /**
     * 接口列表
     *
     * @param bo
     * @return
     */
    PageData<InterfaceInvokeLogDTO> interfaceInvokeLog(InterfaceInvokeLogBO bo);

    /**
     * 接口聚合列表
     *
     * @param bo
     * @return
     */
    List<InterfaceInvokeLogAggregationDTO> interfaceAggregation(InterfaceInvokeLogAggregationBO bo);

    JSONArray interfaceAggregationInfo(InterfaceInvokeLogAggregationInfoBO bo);
}
