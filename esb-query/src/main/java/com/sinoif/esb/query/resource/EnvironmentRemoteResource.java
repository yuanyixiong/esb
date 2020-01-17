package com.sinoif.esb.query.resource;

import com.sinoif.esb.query.model.dto.EnvironmentInformationAggregationDTO;
import com.sinoif.esb.query.model.dto.EnvironmentInformationDTO;
import com.sinoif.esb.query.remote.EnvironmentRemoteService;
import com.sinoif.esb.query.service.EnvironmentService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author 袁毅雄
 * @description 远程接口的实现-环境信息
 * @date 2019/11/1
 */
public class EnvironmentRemoteResource implements EnvironmentRemoteService {

    /**
     * 环境信息本地服务
     */
    @Autowired
    private EnvironmentService environmentService;

    /**
     * 环境信息
     * @return
     */
    @Override
    public List<EnvironmentInformationDTO> environmentInformation() {
        return environmentService.environmentInformation();
    }

    @Override
    public EnvironmentInformationAggregationDTO environmentInformationAggregation() {
        return environmentService.environmentInformationAggregation();
    }
}
