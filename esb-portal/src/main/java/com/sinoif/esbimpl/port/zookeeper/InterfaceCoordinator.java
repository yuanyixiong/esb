package com.sinoif.esbimpl.port.zookeeper;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sinoif.esb.port.bean.Interface;
import com.sinoif.esbimpl.port.interfaces.InterfaceContext;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.List;
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

    @Autowired
    @Lazy
    InterfaceContext interfaceContext;

    @Autowired
    CuratorFramework client;


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
                        interfaceContext.updateCache((Interface) JSONObject.parseObject(interfaceJson, Interface.class));
                    }
                    break;
                case CHILD_REMOVED:
                    log.debug("检测到接口删除：" + event.getData().getPath());
                    String deleteInterfaceJson = new String(event.getData().getData(), "utf-8");
                    Interface deleteInterface = JSONObject.parseObject(deleteInterfaceJson, Interface.class);
                    interfaceContext.eraseInterfaceCache(deleteInterface.getId());
                    break;
                default:
                    break;
            }
        });
        reloadInterfaces();
    }


    /**
     * 检测到zookeeper中的信息修改时，同时更新本地缓存
     *
     * @param esbInterface 从zookeeper znode中拿到的缓存信息
     */
    public String insertOrUpdateInterface(Interface esbInterface) throws Exception {
        String znodePath = path + "/" + esbInterface.getId() + "";
        if(client.checkExists().forPath(znodePath) != null){
            client.delete().forPath(znodePath);
        }
        client.create().withMode(CreateMode.PERSISTENT).forPath(znodePath, JSON.toJSONBytes(esbInterface));
        return "success";
    }

    /**
     * 重新从zookeeper中读取全部缓存信息，并更新本地缓存
     *
     * @return 缓存接口id列表
     * @throws Exception 读取过程中的异常
     */
    public List<String> reloadInterfaces() throws Exception {
        List<String> children = client.getChildren().forPath(path);
        for (String subPath : children) {
            String interfaceJson = null;
            try {
                interfaceJson = new String(client.getData().forPath(path + "/" + subPath), "utf-8");
                interfaceContext.updateCache(JSON.parseObject(interfaceJson, Interface.class));
            } catch (Exception e) {
                e.printStackTrace();
                log.error(e.getMessage());
                log.error("加截接口失败：" + interfaceJson);
            }
        }
        return children;
    }

    /**
     * 从缓存中删除接口
     *
     * @param esbInterface 要删除的接口
     * @return 是否成功
     */
    public boolean deleteInterface(Interface esbInterface) {
        String znodePath = path + "/" + esbInterface.getId() + "";
        try {
            client.delete().forPath(znodePath);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    @PreDestroy
    public void closeClient() {
        client.close();
    }
}
