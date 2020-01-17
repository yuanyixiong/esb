package com.sinoif.esb.query.service.impl;

import com.sinoif.esb.constants.PortalConstants;
import com.sinoif.esb.port.dto.InterfaceApproveDTO;
import com.sinoif.esb.port.param.InterfaceApproveParam;
import com.sinoif.esb.port.service.EsbPortalRemoteService;
import com.sinoif.esb.query.config.monodb.MongoService;
import com.sinoif.esb.query.model.dto.ApproveInfoDataDTO;
import com.sinoif.esb.query.model.param.ApproveDetailParam;
import com.sinoif.esb.query.page.PageData;
import com.sinoif.esb.query.remote.InterfaceApproveInfoRemoteService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;

/**
 * 审核服务类
 */
public class InterfaceApproveInfoServiceImpl implements InterfaceApproveInfoRemoteService {
    @Autowired
    MongoService mongoService;

    @Autowired
    EsbPortalRemoteService esbPortalRemoteService;

    /**
     * 查询待审核列表
     *
     * @param param 分页查询待审核数据的查询条件
     * @return 待审核列表数据
     */
    @Override
    public PageData<InterfaceApproveDTO> queryApproveData(InterfaceApproveParam param) {
        HashMap<String, String> groupMap = new HashMap<>();
        groupMap.put("interface_name", "");
        groupMap.put("input_system", "");
        groupMap.put("output_system", "");
        groupMap.put("interface_id", "");
        Object[] result = mongoService.aggregateQuery(PortalConstants.COLLECTION_APPROVE_INFO, "interface_id", null, groupMap, param.getPage(),
                param.getPageSize(), "data_count");
        return PageData.getPageData(result, InterfaceApproveDTO.class);
    }

    /**
     * 要审核数据的明细
     *
     * @param param 审核明细请求参数
     * @return 审核明细数据
     */
    @Override
    public PageData queryApproveDetail(ApproveDetailParam param) {
        HashMap<String, Object> queryMap = new HashMap<>();
        queryMap.put("_interface_id", "=" + param.getInterfaceId());
        queryMap.put(PortalConstants.COL_APPROVE, "=" + PortalConstants.NOT_PROCESSED);
        Object[] queryResult = mongoService.queryByCondition(PortalConstants.COLLECTION_APPROVE_INFO_DATA, queryMap, param.getPageSize(), param.getPage(), true);
        return PageData.getPageData(queryResult, ApproveInfoDataDTO.class);
    }
}
