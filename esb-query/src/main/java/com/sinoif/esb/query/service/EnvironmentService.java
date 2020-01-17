package com.sinoif.esb.query.service;

import com.sinoif.esb.query.model.dto.EnvironmentInformationAggregationDTO;
import com.sinoif.esb.query.model.dto.EnvironmentInformationDTO;

import java.util.List;

/**
 * @author 袁毅雄
 * @description 环境信息 ，主要包括服务器cpu 内存等
 * @date 2019/11/1
 */
public interface EnvironmentService {

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
