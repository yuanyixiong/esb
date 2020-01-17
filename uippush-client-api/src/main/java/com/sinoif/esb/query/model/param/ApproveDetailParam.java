package com.sinoif.esb.query.model.param;

import com.sinoif.esb.constants.PortalConstants;

/**
 * 查询记录明细查询参数
 */
public class ApproveDetailParam extends BasicQueryParam{
    /**
     * 审核状态
     */
    String approvedStatus = PortalConstants.NOT_PROCESSED;

    public String getApprovedStatus() {
        return approvedStatus;
    }

    public void setApprovedStatus(String approvedStatus) {
        this.approvedStatus = approvedStatus;
    }
}
