package com.sinoif.esbimpl.port;

import com.sinoif.esb.platform.PlatformService;
import com.sinoif.esbimpl.port.interfaces.InterfaceContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;


/**
 * 项目上、下文启动监听类
 */
@Component
public class ContextListener implements ApplicationListener {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Qualifier("platformService")
    PlatformService platformService;

    @Qualifier("interfaceContext")
    InterfaceContext interfaceContext;

    @Override
    public void onApplicationEvent(ApplicationEvent _) {
        logger.debug("esb-port loaded!");
//        List<Interface> list = platformService.getAllInterfaces();
//        list.forEach(i -> interfaceContext.registerInterfaces(i));
    }

}
