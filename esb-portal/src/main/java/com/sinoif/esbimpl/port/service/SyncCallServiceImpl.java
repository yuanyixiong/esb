package com.sinoif.esbimpl.port.service;

import com.sinoif.esb.constants.PortalConstants;
import com.sinoif.esb.enums.TypeActiveEnum;
import com.sinoif.esb.port.bean.Interface;
import com.sinoif.esb.port.bean.InvokeResult;
import com.sinoif.esb.port.service.EsbPortalRemoteService;
import com.sinoif.esbimpl.port.service.bean.CallRequest;
import com.sinoif.esbimpl.port.service.router.ServiceRoute;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;

/**
 * <p>同步请求方式</p>
 *
 * @author chenxj
 * @date 2019/10/22
 */
@ServiceRoute("sync_call")
@Component
public class SyncCallServiceImpl extends AbstractCallService implements PortalConstants {
    private Logger logger = Logger.getLogger(SyncCallServiceImpl.class);

    @Autowired
    private EsbPortalRemoteService esbPortalRemoteService;

    @Override
    public InvokeResult process(CallRequest request) throws Exception {
        Interface itf = request.getItf();
        if (itf.getTypeActive() != TypeActiveEnum.SYNC_CALL) {
            return InvokeResult.fail(itf,ITF_TYPE_ERROR);
        }
        LinkedHashMap<String, String> params = new LinkedHashMap<>();
        if (request.getData() != null) {
            request.getData().forEach(params::put);
        }
        try {
            return esbPortalRemoteService.invokeSyncInterface(request.getInterfaceId(), params);
        } catch (Exception e) {
            e.printStackTrace();
            return InvokeResult.fail(itf,e.getMessage());
        }
    }
}
