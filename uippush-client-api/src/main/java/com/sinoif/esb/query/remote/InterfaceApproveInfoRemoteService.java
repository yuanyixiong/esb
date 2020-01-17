package com.sinoif.esb.query.remote;

import com.sinoif.esb.port.dto.InterfaceApproveDTO;
import com.sinoif.esb.port.param.InterfaceApproveParam;
import com.sinoif.esb.query.model.dto.InterfaceInvokeLogAggregationDTO;
import com.sinoif.esb.query.model.param.ApproveDetailParam;
import com.sinoif.esb.query.page.PageData;

import java.util.Date;
import java.util.List;

/**
 * @author 袁毅雄
 * @description 远程接口- 审核数据
 * @date 2019/11/1
 */
public interface InterfaceApproveInfoRemoteService {
    /**
     * 查询待审核列表
     *
     * @param param 分页查询待审核数据的查询条件
     * @return 查询到的待审核数据
     */
    PageData<InterfaceApproveDTO> queryApproveData(InterfaceApproveParam param);

    PageData queryApproveDetail(ApproveDetailParam param);

}
