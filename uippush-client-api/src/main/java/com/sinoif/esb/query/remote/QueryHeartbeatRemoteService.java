package com.sinoif.esb.query.remote;

import com.sinoif.esb.query.model.dto.ServiceInformationDTO;

import java.util.List;

/**
 * @author 袁毅雄
 * @description 远程接口-ESB query 心跳服务
 * @date 2019/11/1
 */
public interface QueryHeartbeatRemoteService {

    /**
     * 心跳功能
     *
     * @return
     */
    boolean heartbeat();

    /**
     * 服务存活状态
     * @return
     */
    List<ServiceInformationDTO> serviceSurviveState();
}
