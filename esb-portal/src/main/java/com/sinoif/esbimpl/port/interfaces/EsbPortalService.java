package com.sinoif.esbimpl.port.interfaces;

import com.alibaba.fastjson.JSON;
import com.sinoif.commonaip.util.DateFormatUtils;
import com.sinoif.esb.constants.PortalConstants;
import com.sinoif.esb.core.service.EsbCoreRemoteService;
import com.sinoif.esb.enums.ProtocolEnum;
import com.sinoif.esb.enums.TypeTransferEnum;
import com.sinoif.esb.platform.PlatformService;
import com.sinoif.esb.port.bean.Interface;
import com.sinoif.esb.port.bean.InvokeResult;
import com.sinoif.esb.port.service.EsbPortalRemoteService;
import com.sinoif.esb.utils.InvokeParamHolder;
import com.sinoif.esb.utils.sequence.SequenceUtil;
import com.sinoif.esbimpl.port.aspect.InvokeLogAspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * esb-portal模块对外提供服务统的统一接口
 */
@Component(value = "esbPortalService")
public class EsbPortalService implements EsbPortalRemoteService {

    private final Logger logger = LoggerFactory.getLogger(EsbPortalService.class);

    @Autowired
    private RestApiExecutor restApiExecutor;
    @Autowired
    private WsdlExtractor wsdlExtractor;
    @Autowired
    private WsPortExecutor wsPortExecutor;

    @Autowired
    private InterfaceContext interfaceContext;

    @Autowired
    private PlatformService platformService;

    @Autowired
    InvokeLogAspect logAspect;

    @Autowired
    EsbCoreRemoteService esbCoreService;

    /**
     * 通过wsdl地址，提取wsdl中包含的所有的方法，参数等信息
     *
     * @param wsdlUrl web service 的 wsdl 地址
     * @return
     */
    @Override
    public String getWsdlProperty(String wsdlUrl) {
        return wsdlExtractor.getWsdlProperty(wsdlUrl);
    }

    /**
     * 接口执行，供esb平台模块调用主动输入接口。
     *
     * @param interfaceId 注册的Interface的id
     * @return 接口执行返回值
     * @throws Exception 执行接口过程中产生的异常
     */
    @Override
    public InvokeResult invokeInterfaceById(long interfaceId) {
        InvokeResult result = invokeInterfaceDirectly(getInterface(interfaceId), false, 0);
        // 不用向调用方返回接口信息, 避免dubbo序列化出错
        result.setIfe(null);
        return result;
    }

    /**
     * 直接调用接口，用于重试等。直接将执口参数放到interface里面执行
     *
     * @param itf 接口
     * @param sync 是否同步
     * @param invokeNumber 调用编号 100：审核，0：接接口id调用，其它异常重试，异常编号
     * @return 接口执行结果
     */
    public InvokeResult invokeInterfaceDirectly(Interface itf, boolean sync, long invokeNumber) {
        LocalDateTime invokeTime = LocalDateTime.now();
        if (logger.isInfoEnabled()) {
            logger.info("执行{}{}接口， Interface={}", itf.getTypeActive().getMessage(), itf.getTypeTransfer().getMessage(), itf);
        }
        InvokeResult result = null;
        if (itf == null) {
            return InvokeResult.fail(itf, "接口{}未注册，请选注册接口");
        }
        if (itf.getTypeTransfer() == TypeTransferEnum.INPUT) {
            if (StringUtils.isEmpty(itf.getKeyProperty())) {
                return InvokeResult.fail(itf, "接口KeyProperty不能为空");
            }
        }
        LinkedHashMap<String, String> invokeParam = InvokeParamHolder.get();
        try {
            if (itf.getProtocol() == ProtocolEnum.WEBSERVICE) {
                result = wsPortExecutor.invokeInterface(itf, sync);
            } else if (itf.getProtocol() == ProtocolEnum.REST_API) {
                result = restApiExecutor.invokeInterface(itf, sync);
            } else {
                result = InvokeResult.fail(itf, "不支持的传输方式!");
            }
            if (logger.isInfoEnabled()) {
                logger.info("执行{}{}接口响应，id={},result={}", itf.getTypeActive().getMessage(), itf.getTypeTransfer().getMessage(), itf.getId(), result);
            }
        } catch (Exception exception) {
            logger.info("执行接口发生异常: 异常接口{}", itf);
            exception.printStackTrace();
            result = InvokeResult.fail(itf, exception.getMessage());
            return result;
        } finally {
            result.setIfe(itf);
            result.setInvokeNumber(invokeNumber);
            if (!result.isSuccess()) {// 对于执行失败的接口将参数保存下来，以便可以重试。
                result.setResponse(JSON.toJSONString(invokeParam == null ? itf.getParams() : invokeParam));
            }
            logAspect.saveInvokeLog(result, invokeTime);
            InvokeParamHolder.clear();
        }
        if(invokeNumber >0){
            // 对于重试记录，去掉iterface
            result.setIfe(null);
        }
        return result;
    }

    /**
     * 接口执行，供esb-core模块调用主动输出接口
     *
     * @param topic interface 关联的topic
     * @param data  数据（消息）
     * @param group 消息所属的组（应用名）
     * @return 执行结果
     */
    @Override
    public InvokeResult invokeInterfaceByKafka(String topic, String data, String group) {
        if (logger.isInfoEnabled()) {
            logger.info("[esb_core-->portal]执行输出， topic={}, group={}", topic, group);
        }
        if (data == null) {
            return new InvokeResult(null, false, "data不能为空", null);
        }
        Interface i = interfaceContext.getUniqueInterface(topic, group);
        if (i == null) {
            return new InvokeResult(i, false, "对应topic的地动接口不存,可能是注册错误。", null);
        }
        // 将接对象中的参数表达式转换成具体参数值
//        data = InvokeParamHolder.get().get("data");
        LinkedHashMap evaluatedMap = new LinkedHashMap<>();
        if (i.getParams()!= null) {
            for(Map.Entry<String,String> ent :i.getParams().entrySet()){
                evaluatedMap.put(ent.getKey(),ParameterEvaluator.evaluateParameterValue(ent.getValue(),data));
            }
        }
        // 解析出参数值后，将其放到与线程绑定的参数当中
        InvokeParamHolder.set(evaluatedMap);
        InvokeResult result;
        if (i.isNeedApprove()) {
            logger.info("缓存数据：等待审核");
            String inputSystem = interfaceContext.getInputInterface(i.getTopic()).getAppName();
            String outputSystem = i.getAppName();
            String interfaceName = i.getName();
            String time = DateFormatUtils.getCurrentTime(PortalConstants.TIME_FORMATTER);
            long id = SequenceUtil.getId();
            esbCoreService.saveApproveInfo(id, i.getId(), i.getTopic(), inputSystem, outputSystem, interfaceName, time, data);
            //   esbCoreService.saveApproveInfoData(id,i.getTopic(),data,time,i.getId());
            result = InvokeResult.success(i, "缓存数据：等待审核");
        } else {
            result = invokeInterfaceDirectly(i, false, 0);
        }
        if (logger.isInfoEnabled()) {
            logger.info("APP<<ESB-PORTAL，执行输出接口{},{}， topic={}，result={}", i.getId(), i.getSendUrl(), topic, result.isSuccess());
        }
        // kafka不需要知道接口信息，且解决dubbo接口序列化失败的问题。
        result.setIfe(null);
        return result;
    }


    /**
     * 根据id获取接口对象，如果内存中有则从内存中获取，否则通过esb平台模块服务获取。
     * 第二种情况需同时向esb-core模块注册接口信息。
     *
     * @param interfaceId 接口id
     * @return 接口对象
     */
    private Interface getInterface(long interfaceId) {
        if (logger.isInfoEnabled()) {
            logger.info("获取接口信息， interfaceId={}", interfaceId);
        }
        Interface esbInterface = interfaceContext.getInterfaceById(interfaceId);
        if (esbInterface == null) {
            logger.info("接口缓存不存在，远程获取接口信息。 interfaceId={}", interfaceId);
            esbInterface = platformService.getInterfaceInfo(interfaceId);
        }
        if (logger.isInfoEnabled()) {
            logger.info("获取接口信息结果， esbInterface={}", esbInterface);
        }
        return esbInterface;
    }


    /**
     * 接口注册
     *
     * @param esbInterface 向esb-port模块注册的接口
     * @return 成功："success", 失败：失败的原因
     */
    @Override
    public String registerInterfaces(Interface esbInterface) {
        if (logger.isInfoEnabled()) {
            logger.info("注册接口信息， esbInterface={}", esbInterface);
        }
        String result = interfaceContext.registerInterfaces(esbInterface);
        if (logger.isInfoEnabled()) {
            logger.info("注册接口信息响应， result={}", result);
        }
        return result;
    }

    /**
     * 请求重新注册
     */
    @Deprecated
    @Override
    public void requestInterfaceRegister() {
        try {
            interfaceContext.coordinator.reloadInterfaces();
        } catch (Exception e) {
            logger.error("esb-core启动，要求重新注册接口时发生异常：" + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * 执行同步接口
     *
     * @param interfaceId 同步接口
     * @return 执行结果
     */
    @Override
    public InvokeResult invokeSyncInterface(long interfaceId, LinkedHashMap<String, String> params) throws Exception {
        Interface esbInterface = getInterface(interfaceId);
        esbInterface.setParams(params);
        return invokeInterfaceDirectly(esbInterface, true, 0);
    }

    /**
     * 接口删除
     *
     * @param interfaceId 要删除接口的id
     * @return 是否成功
     */
    @Override
    public boolean deleteInterface(long interfaceId) {
        interfaceContext.deleteInterface(interfaceId);
        return true;
    }

    /**
     * 通过topic查找接口列表
     *
     * @param topic topic
     * @return 接口列表
     */
    @Override
    public List<Interface> getInterfacesByTopics(String topic) {
        return interfaceContext.getAll().stream().filter(inf -> topic.equals(inf.getTopic())).collect(Collectors.toList());
    }

    /**
     * 要据接口id查找接口信息
     *
     * @param id 接口id
     * @return 接口信息
     */
    @Override
    public Interface getInterfaceById(long id) {
        return getInterface(id);
    }

}
