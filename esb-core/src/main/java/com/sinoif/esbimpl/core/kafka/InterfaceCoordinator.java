package com.sinoif.esbimpl.core.kafka;

import com.alibaba.fastjson.JSONObject;
import com.sinoif.esb.port.bean.Interface;
import com.sinoif.esbimpl.core.EsbCoreRemoteServiceImpl;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 通过zookeeper znode 保存接口信息，并通过监听接口信息改动及时更新本地缓存的方式
 * 保持多结点的缓存信息一致性。
 */
@Component
public class InterfaceCoordinator {
    private static final Logger log = LogManager.getLogger(InterfaceCoordinator.class);
    private static String path = "/interface";

    public static ConcurrentHashMap<String, HashMap<String,Interface>> interfaceMap = new ConcurrentHashMap<>();

    @Autowired
    CuratorFramework client;

    @Autowired
    @Qualifier(value = "esbCoreServiceImpl")
    EsbCoreRemoteServiceImpl esbCoreService;

    /**
     * 监听zookeeper 存储接口信息的目录，如果有改动则通知所有的节点
     *
     * @throws Exception
     */
    @PostConstruct
    public void startMonitor() throws Exception {
        if (client.checkExists().forPath(path) == null) {
            client.create().withMode(CreateMode.PERSISTENT);
        }
        ExecutorService exec = Executors.newFixedThreadPool(2);
        PathChildrenCache cache = new PathChildrenCache(client, path, true, false, exec);

        cache.start(PathChildrenCache.StartMode.NORMAL);
        cache.getListenable().addListener((client, event) -> {
            switch (event.getType()) {
                case CHILD_ADDED:
                    byte[] addedInterface = client.getData().storingStatIn(new Stat()).forPath(path);
                    if (addedInterface != null) {
                        log.debug("检测到新增接口：" + event.getData().getPath());
                        String interfaceJson = new String(event.getData().getData(), "utf-8");
                        Interface newInterface = JSONObject.parseObject(interfaceJson, Interface.class);
                        String result = esbCoreService.startInterface(newInterface);
                        HashMap<String,Interface> appInterfaceMap = interfaceMap.get(newInterface.getTopic());
                        if(appInterfaceMap==null){
                            appInterfaceMap = new HashMap<>();
                        }
                        appInterfaceMap.put(newInterface.getAppName(),newInterface);
                        interfaceMap.put(newInterface.getTopic(),appInterfaceMap);
                        log.info("注册（重新注册）接口："+result);
                    }
                    break;
                case CHILD_REMOVED:
                    log.debug("检测到接口删除：" + event.getData().getPath());
                    String deleteInterfaceJson = new String(event.getData().getData(), "utf-8");
                    Interface deleteInterface = JSONObject.parseObject(deleteInterfaceJson, Interface.class);
                    esbCoreService.deleteInterfae(deleteInterface);
                    interfaceMap.get(deleteInterface.getTopic()).remove(deleteInterface.getAppName());
                    break;
                default:
                    break;
            }
        });
    }

    /**
     * bean被销毁时将zookeeper客户端关闭
     */
    @PreDestroy
    public void closeClient() {
        client.close();
    }
}
