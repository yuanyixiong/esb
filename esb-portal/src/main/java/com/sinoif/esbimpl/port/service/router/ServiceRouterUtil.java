package com.sinoif.esbimpl.port.service.router;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <p></p>
 *
 * @author chenxj
 * @date 2019/10/22
 */
@Component
public class ServiceRouterUtil implements ApplicationContextAware {

    private ApplicationContext appCtx;

    private Map<String, Object> routeMap = new ConcurrentHashMap<>();

    private Logger logger = LoggerFactory.getLogger(ServiceRouterUtil.class);

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        appCtx = applicationContext;
        registerService();
    }

    /**
     * 注册服务接口
     */
    private void registerService() {
        Map<String, Object> allWebResBeans = appCtx.getBeansWithAnnotation(ServiceRoute.class);
        for (Object bean : allWebResBeans.values()) {
            String routeName = null;
            Annotation anno = AnnotationUtils.getAnnotation(bean.getClass(), ServiceRoute.class);
            if (anno != null) {
                routeName = (String) AnnotationUtils.getValue(anno);
            }
            if (routeName != null) {
                routeMap.put(routeName, bean);
                this.logger.debug("register route,routeName={},bean={}", new Object[]{routeName,
                        bean});
            }
        }
    }

    public Object getServiceRoute(String name) {
        if (StringUtils.isNotEmpty(name)) {
            return routeMap.get(name);
        }
        return null;
    }
}
