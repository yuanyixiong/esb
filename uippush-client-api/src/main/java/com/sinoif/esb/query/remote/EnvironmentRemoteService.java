package com.sinoif.esb.query.remote;

import com.sinoif.esb.query.model.dto.EnvironmentInformationAggregationDTO;
import com.sinoif.esb.query.model.dto.EnvironmentInformationDTO;

import java.util.List;

/**
 * @author 袁毅雄
 * @description 远程接口-环境信息
 * @date 2019/11/1
 */
public interface EnvironmentRemoteService {

    /**
     * 环境信息
     * @return
     */
    List<EnvironmentInformationDTO> environmentInformation();

    /**
     * 环境信息聚合
     * @return
     */
    EnvironmentInformationAggregationDTO environmentInformationAggregation();
}
