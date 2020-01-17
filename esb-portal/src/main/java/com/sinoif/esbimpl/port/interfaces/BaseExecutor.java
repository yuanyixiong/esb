package com.sinoif.esbimpl.port.interfaces;

import com.alibaba.fastjson.JSONObject;
import com.call.client.bean.ConnectConfig;
import com.call.client.client.CallResult;
import com.sinoif.esb.constants.CoreConstants;
import com.sinoif.esb.core.service.EsbCoreRemoteService;
import com.sinoif.esb.enums.TypeActiveEnum;
import com.sinoif.esb.enums.TypeTransferEnum;
import com.sinoif.esb.port.bean.Interface;
import com.sinoif.esb.port.bean.InvokeResult;
import com.sinoif.esb.port.service.InterfaceResponseProcessor;
import com.sinoif.uiptrade.sdk.bean.EsbConnectionSetup;
import com.sinoif.uiptrade.sdk.bean.WhiteList;
import com.sinoif.uiptrade.sdk.request.ConnectionSetupReq;
import com.sinoif.uiptrade.sdk.request.WhiteListReq;
import com.sinoif.uiptrade.sdk.service.ConnectSetUpService;
import com.sinoif.uiptrade.sdk.service.WhitelistService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * 基本接口执行类
 */
@Component
public abstract class BaseExecutor {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    @Qualifier("esbCoreRemoteService")
    private EsbCoreRemoteService esbCoreRemoteService;

    @Autowired
    @Qualifier(value = "connectionService")
    ConnectSetUpService connectSetUpService;

    @Autowired
    @Qualifier(value = "whiteListService")
    WhitelistService whitelistService;

    abstract InvokeResult invoke(Interface esbInterface, ConnectConfig config);

    /**
     * 写入参数之前对参数进行过滤，这里将增量更新策略参数删除
     *
     * @param params 接口参数
     */
    LinkedHashMap<String, String> filterParam(LinkedHashMap<String, String> params) {
        LinkedHashMap<String, String> clonedParams = (LinkedHashMap<String, String>) params.clone();
        clonedParams.remove(CoreConstants.INCREMENTAL_POLICY);
        return clonedParams;
    }

    /**
     * 调用接口后，对接口返回结果进行处理
     *
     * @param requestResult 接口调用返回值
     * @param esbInterface 调用的接口
     * @return 接口调用的最终结果
     */
    InvokeResult handleResponse(CallResult requestResult, Interface esbInterface) {
        try {
            Class<?> processor = this.getClass().getClassLoader().loadClass(esbInterface.getProcessorName());
            return ((InterfaceResponseProcessor) processor.newInstance()).process(requestResult, esbInterface);
        } catch (Exception e) {
            e.printStackTrace();
            return InvokeResult.fail(esbInterface, "加载处理器失败：" + e.getMessage());
        }
    }

    /**
     * 执行接口，并相对接口执行结果进行处理
     *
     * @param esbInterface 要执行的接口
     * @return 接口执行结果，对于异步接口，结果中只包含了"发送消息成功"等信息。
     */
    public InvokeResult invokeInterface(Interface esbInterface, boolean sync) {
        return invokeInterfaceWithSyncType(esbInterface, sync);
    }

    /**
     * 接口调用
     *
     * @param esbInterface 被调用的接口
     * @param sync 是否是同步调用
     * @return 调用的结终结果
     */
    public InvokeResult invokeInterfaceWithSyncType(Interface esbInterface, boolean sync) {
        // 被动接口先检查ip是否在白名单中
        if (esbInterface.getTypeActive() == TypeActiveEnum.REACTIVE || esbInterface.getTypeActive() == TypeActiveEnum.SYNC_CALL) {
            if (!isIpAllowed(esbInterface)) {
                return InvokeResult.fail(esbInterface, "请求ip:" + esbInterface.getRequestIp() + "不在白名单内");
            }
        }
        // 如果主动输入接口则要检查增量更新配置，如果检查到增量更新配置，就更新接口参数列表
        if (esbInterface.getTypeActive() == TypeActiveEnum.INITIATIVE && esbInterface.getTypeTransfer() == TypeTransferEnum.INPUT) {

            LinkedHashMap<String, String> params = esbInterface.getParams();
            if (params.containsKey(CoreConstants.INCREMENTAL_POLICY)) {
                JSONObject obj = (JSONObject) JSONObject.parse(params.get(CoreConstants.INCREMENTAL_POLICY));
                String field = obj.get(CoreConstants.INCREMENTAL_POLICY_FIELD).toString();
                String valueExpression = obj.get(CoreConstants.INCREMENTAL_POLICY_PARAM_VALUE_EXPRESSION).toString();
                String paramName = obj.get(CoreConstants.INCREMENTAL_POLICY_PARAM_NAME).toString();
                String lastUpdateTime = esbCoreRemoteService.getMaxDateOfFetchedData(esbInterface.getTopic(), field);
                if (lastUpdateTime == null) {//未获取过接口数据，查询全量获取数据
                    String valueExpressionAll = obj.get(CoreConstants.INCREMENTAL_POLICY_PARAM_VALUE_EXPRESSION_ALL).toString();
                    params.put(paramName, valueExpressionAll);
                } else {
                    params.put(paramName, String.format(valueExpression, lastUpdateTime));
                }
            }
        }
        ConnectConfig connectConfig = buildConnectCfg(esbInterface);
        logger.info("请求连接设置接口ID={}-{}", esbInterface.getId(), connectConfig);
        InvokeResult invokeResult = invoke(esbInterface, connectConfig);

        // 同步接口直接返回接口执行结果
        // 接口调用失败，直接通回结果
        if (sync || !invokeResult.isSuccess()) {
            return invokeResult;
        }
        // 异步输入接口向kafka消息对列发送消息
        if (TypeTransferEnum.INPUT.equals(esbInterface.getTypeTransfer())) {
            String key = System.currentTimeMillis() + "";
            logger.info("输入接口向esb-core输入数据，topic={}, key={}", esbInterface.getTopic(), key);
            if (!"{}".equals(invokeResult.getResponse()) && !"[]".equals(invokeResult.getResponse())) {
                boolean b = esbCoreRemoteService.inputData(esbInterface.getTopic(), key, invokeResult.getResponse());
                if (b) {
                    return InvokeResult.success(esbInterface, "success", invokeResult.getResponse());
                }
                return InvokeResult.fail(esbInterface, "向kafka发送消息失败");
            } else {
                return InvokeResult.success(esbInterface, "消息为空");
            }
        } else {// 异步输出接口返回执行结果
            return invokeResult;
        }
    }

    /**
     * 从平台提供的微服务查询接口调用配置，例如超时、coding等
     *
     * @param esbInterface 被调用的接口
     * @return 调用接口的配置信息
     */
    private ConnectConfig buildConnectCfg(Interface esbInterface) {
        ConnectConfig connectConfig = new ConnectConfig();
        if (esbInterface.getTimeOut() > 0) {
            connectConfig.setConnectTimeout(esbInterface.getTimeOut());
            return connectConfig;
        }
        EsbConnectionSetup connectionParam = getConnectParam(esbInterface);
        if (connectionParam != null) {
            connectConfig.setConnectTimeout(connectionParam.getTimeout());
            return connectConfig;
        }
        return null;
    }

    /**
     * 对于被动接口，检查调用者的ip是否白明单内
     *
     * @param esbInterface 调用的被动接口
     * @return 调用ip是否在接口白明单里面
     */
    protected boolean isIpAllowed(Interface esbInterface) {
        WhiteListReq whiteListReq = new WhiteListReq();
        whiteListReq.setIp(esbInterface.getRequestIp());
        List<WhiteList> whiteLists = whitelistService.getWhiteList(whiteListReq);
        return whiteLists != null && whiteLists.size() > 0;
    }

    /**
     * 调用平台提供的微服务，找到接口配置信息。
     *
     * @param esbInterface 接口
     * @return 配置信息
     */
    private EsbConnectionSetup getConnectParam(Interface esbInterface) {
        ConnectionSetupReq req = new ConnectionSetupReq();
        req.setProtocolType(esbInterface.getProtocol().getCode());
        List<EsbConnectionSetup> setupByParams = connectSetUpService.getConnectionSetupByParam(req);
        if (setupByParams.size() > 0) {
            return setupByParams.get(0);
        }
        return null;
    }
}
