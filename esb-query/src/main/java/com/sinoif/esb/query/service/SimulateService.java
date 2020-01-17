package com.sinoif.esb.query.service;

import com.sinoif.uiptrade.sdk.bean.EsbConnectionSetup;
import com.sinoif.uiptrade.sdk.bean.WhiteList;
import com.sinoif.uiptrade.sdk.request.ConnectionSetupReq;
import com.sinoif.uiptrade.sdk.request.WhiteListReq;
import com.sinoif.uiptrade.sdk.service.ConnectSetUpService;
import com.sinoif.uiptrade.sdk.service.WhitelistService;

import java.util.ArrayList;
import java.util.List;

/**
 * 为方便快速本地开发，特模拟一个服务。上线时不会用到。
 */
public class SimulateService implements ConnectSetUpService, WhitelistService {

    @Override
    public List<EsbConnectionSetup> getConnectionSetupByParam(ConnectionSetupReq connectionSetupReq) {
        List<EsbConnectionSetup> setups = new ArrayList<>();
        EsbConnectionSetup setup = new EsbConnectionSetup();
        setup.setTimeout(3000);
        setups.add(setup);
        return  setups;
    }


    @Override
    public List<WhiteList> getWhiteList(WhiteListReq whiteListReq) {
        List<WhiteList> whiteLists = new ArrayList<>();
        whiteLists.add(new WhiteList());
        return whiteLists;
    }
}
