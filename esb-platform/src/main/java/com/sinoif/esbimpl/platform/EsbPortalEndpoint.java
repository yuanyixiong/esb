package com.sinoif.esbimpl.platform;

import com.sinoif.esb.port.bean.Interface;
import com.sinoif.esb.port.service.EsbPortalRemoteService;

public class EsbPortalEndpoint {
    private EsbPortalRemoteService esbPortalRemoteService;

    public String registerInterface(Interface esbInterface) {
        return esbPortalRemoteService.registerInterfaces(esbInterface);
    }

    public String getWsdlProperties(String wsdlUrl){
        return esbPortalRemoteService.getWsdlProperty(wsdlUrl);
    }

    public void setEsbPortalRemoteService(EsbPortalRemoteService esbPortalRemoteService) {
        this.esbPortalRemoteService = esbPortalRemoteService;
    }
}
