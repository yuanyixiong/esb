package com.sinoif.esb.port.remote;

import com.sinoif.esb.query.model.dto.ServiceInformationDTO;

import java.util.List;

/**
 * @author 袁毅雄
 * @description 远程接口-ESB portal 心跳服务
 * @date 2019/11/1
 */
public interface PortalHeartbeatRemoteService {

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
