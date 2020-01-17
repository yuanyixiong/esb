package com.sinoif.esbimpl.port.service;

import com.sinoif.esb.constants.PortalConstants;
import com.sinoif.esb.core.service.EsbCoreRemoteService;
import com.sinoif.esb.enums.TypeActiveEnum;
import com.sinoif.esb.enums.TypeTransferEnum;
import com.sinoif.esb.port.bean.Interface;
import com.sinoif.esb.port.bean.InvokeResult;
import com.sinoif.esbimpl.port.service.bean.CallRequest;
import com.sinoif.esbimpl.port.service.router.ServiceRoute;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.HashMap;

/**
 * <p>被动输出方式</p>
 *
 * @author chenxj
 * @date 2019/10/22
 */
@ServiceRoute("reactive_output")
@Component
public class ReactiveOutputServiceImpl extends AbstractCallService implements PortalConstants {
    private Logger logger = Logger.getLogger(ReactiveOutputServiceImpl.class);

    @Autowired
    @Qualifier("esbCoreRemoteService")
    private EsbCoreRemoteService esbCoreRemoteService;

    @Override
    public InvokeResult process(CallRequest request)  {
        Interface itf = request.getItf();
        if (itf.getTypeTransfer() != TypeTransferEnum.OUTPUT
                && itf.getTypeActive() != TypeActiveEnum.REACTIVE) {
            return InvokeResult.fail(itf,ITF_TYPE_ERROR);
        }
        HashMap<String, String> params = new HashMap<>();
        request.getData().entrySet().forEach(ent->params.put(ent.getKey(),"="+ent.getValue()));
        String result = null;
        try {
            result = esbCoreRemoteService.mongoDbQuery(itf.getTopic(), params);
            return InvokeResult.success(itf,"查询成功", result);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return InvokeResult.fail(itf,e.getMessage());
        }
    }
}
