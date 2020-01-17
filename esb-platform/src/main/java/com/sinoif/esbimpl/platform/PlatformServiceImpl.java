package com.sinoif.esbimpl.platform;

import com.sinoif.esb.platform.PlatformService;
import com.sinoif.esb.port.bean.Interface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class PlatformServiceImpl implements PlatformService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Override
    public Interface getInterfaceInfo(long id) {
        logger.debug("read interface!");
        if (id == 1) {
            return TestUtil.generateImportInterface();
        } else if(id==2) {
            return TestUtil.generateOutputInterface();
        }else if(id==3){
            return TestUtil.generateRestApiOutputInterface();
        }else{
            return null;
        }
    }

    /**
     * 这里要根据topic与组名（即应用名）来获取接口列表
     * @param topic topic名称
     * @param group 组（应用）名称
     * @return 接口列表
     */
    @Override
    public List<Interface> getInterfacesByTopic(String topic,String group) {
        List<Interface> testInterfaces = new ArrayList<>();
        testInterfaces.add(TestUtil.generateRestApiOutputInterface());
        testInterfaces.add(TestUtil.generateRestApiOutputInterface2());
        return testInterfaces;
    }

}
