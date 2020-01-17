package com.sinoif.esbimpl.port.interfaces;

import com.call.client.cache.ICacheManager;
import com.call.client.cache.LocalCacheManager;
import com.sinoif.esb.core.service.EsbCoreRemoteService;
import com.sinoif.esb.enums.TypeTransferEnum;
import com.sinoif.esb.port.bean.Interface;
import com.sinoif.esbimpl.port.zookeeper.InterfaceCoordinator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 接口缓存类，负责接信息在esb-portal模块的缓存
 */
@Component
@DependsOn({"curatorClient"})
public class InterfaceContext {

    @Autowired
    InterfaceCoordinator coordinator;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    /**
     * 接口缓存  key=接口ID，value=Interface
     */
    private static ICacheManager<Interface> cacheManager = new LocalCacheManager<>(false);

    private EsbCoreRemoteService esbCoreRemoteService;

    /**
     * 向portal模块注册接口
     *
     * @param esbInterface 接口对象
     * @return 是否成功，成功：success； 失败：失败的原因
     */
    public String registerInterfaces(Interface esbInterface) {
        try {
            coordinator.insertOrUpdateInterface(esbInterface);
        } catch (Exception exception) {
            return "注册失败：" + exception.getMessage();
        }
        return registerToEsbCore(esbInterface);
    }

    public String registerToEsbCore(Interface esbInterface) {
        return esbCoreRemoteService.registerInterface(esbInterface);
    }

    /**
     * zk 中的接口更新时同时更新portal中的缓存
     *
     * @param esbInterface
     */
    public void updateCache(Interface esbInterface) {
        cacheManager.put(String.valueOf(esbInterface.getId()), esbInterface);
//        registerToEsbCore(esbInterface);
    }

    /**
     * 清除esb-portal 与 esb-core模块中的接口信息缓存,
     * esb-core模块中的缓存会自行清楚
     *
     * @param interfaceId 接口id
     */
    public void eraseInterfaceCache(long interfaceId) {
        // 清除port模块缓存
        cacheManager.remove(String.valueOf(interfaceId));
    }

    /**
     * 删除接口
     *
     * @param interfaceId 要删除的接口的 id
     * @return 删除接口是否成功
     */
    public boolean deleteInterface(long interfaceId) {
        return coordinator.deleteInterface(getInterfaceById(interfaceId));
    }

    public void setEsbCoreRemoteService(EsbCoreRemoteService esbCoreRemoteService) {
        this.esbCoreRemoteService = esbCoreRemoteService;
    }

    /**
     * 通过被接口id获取接口
     *
     * @param id 接口id
     * @return 接口id对应的接口
     */
    public Interface getInterfaceById(long id) {
        return cacheManager.get(String.valueOf(id));
    }

    /**
     * 根据TOPIC获取接口信息
     *
     * @param topic
     * @return
     */
    public List<Interface> getInterfaceByTopic(String topic) {
        return cacheManager.getAll().stream().filter(i -> topic.equals(i.getTopic()))
                .collect(Collectors.toList());
    }

    /**
     * 根据topic与应用组查询接口
     *
     * @param topic topic
     * @param group 应用组
     * @return 接口
     */
    public Interface getUniqueInterface(String topic, String group) {
        return getInterfaceByTopic(topic).stream()
                .filter(i -> group.equals(String.valueOf(i.getAppId()))
                        && i.getTypeTransfer() == TypeTransferEnum.OUTPUT)
                .findFirst().orElse(null);
    }

    /**
     * 根据topic获取输入接口
     *
     * @param topic topic
     * @return 输入接口
     */
    public Interface getInputInterface(String topic) {
        return getInterfaceByTopic(topic).stream().filter(i -> i.getTypeTransfer() == TypeTransferEnum.INPUT).findFirst().orElse(null);
    }


    /**
     * 返回把有缓存的接口
     *
     * @return 缓存的接口信息
     */
    public List<Interface> getAll() {
        return cacheManager.getAll();
    }

}
