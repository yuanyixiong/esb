package com.sinoif.esb.platform;

import com.sinoif.esb.port.bean.Interface;

import java.util.List;

/**
 * esb平台向esb-portal模块提供的接口，需要esb平台层来实现。
 */
public interface PlatformService {
    /**
     * 根据接口id获取接口信息
     *
     * @param id 接口id
     * @return 接口实例。
     */
    Interface getInterfaceInfo(long id);

    /**
     * 根据topic名称获取所有关联的输出接口。
     *
     * @param topic topic名称
     * @param application 接口对应的应用名，在esb-core中会使用这个应用名给topic分组
     * @return 输出接口列表
     */
    List<Interface> getInterfacesByTopic(String topic,String application);
}
