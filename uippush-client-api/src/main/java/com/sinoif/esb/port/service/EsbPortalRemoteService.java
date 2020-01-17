package com.sinoif.esb.port.service;

import com.sinoif.esb.port.bean.Interface;
import com.sinoif.esb.port.bean.InvokeResult;

import java.util.LinkedHashMap;
import java.util.List;

public interface EsbPortalRemoteService {
    /**
     * 获取webService服务的属性信息，包含 targetNameSpace, wsMethod
     *
     * @param wsdlUrl web service 的 wsdl 地址
     * @return web service属性的json信息（数组）
     */
    String getWsdlProperty(String wsdlUrl);

    /**
     * 主动输入接口调用，由esb平台调用
     *
     * @param interfaceId 注册的Interface的id
     */
    InvokeResult invokeInterfaceById(long interfaceId) throws Exception;
    /**
     * 接口执行，供esb-core模块调用主动输出接口
     *
     * @param topic interface 关联的topic
     * @param data  数据（消息）
     * @param group 消息所属的组（应用名）
     * @return 执行结果
     */
    InvokeResult invokeInterfaceByKafka(String topic, String data, String group);

    /**
     * 向esb-port模块注接口，注册后就可以调用（主动接口）接口；接收第方系统发送的请求（被动接口）
     * 重复注册（id相同代表重复），会覆盖之前注册的接口
     * esb-core模块会根据此方法创建 topic counsumer group等。
     *
     * @param esbInterface 向esb-port模块注册的接口
     * @return true：注册成功，其它：注册失败的原因
     */
    String registerInterfaces(Interface esbInterface);


    /**
     * Esb-core在启动的时候会调用些方法，要求重新注册缓存的结口
     */
    void requestInterfaceRegister();

    /**
     * 执行同步接口
     *
     * @param interfaceId 同步接口缓存中的ID
     * @param params 执行参数
     * @return 执行结果
     */
    InvokeResult invokeSyncInterface(long interfaceId, LinkedHashMap<String,String> params) throws Exception;

    /**
     * 删除接口
     *
     * @param interfaceId 要删除接口的id
     * @return 删除操作是否成功
     */
    boolean deleteInterface(long interfaceId);

    /**
     * 根据topic获取关联接口
     *
     * @param topic
     * @return
     */
    List<Interface> getInterfacesByTopics(String topic);

    Interface getInterfaceById(long id);
}
