package com.sinoif.esbimpl.port.aspect;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Indexes;
import com.sinoif.commonaip.util.DateFormatUtils;
import com.sinoif.esb.constants.CoreConstants;
import com.sinoif.esb.constants.PortalConstants;
import com.sinoif.esb.core.service.EsbCoreRemoteService;
import com.sinoif.esb.enums.ProtocolEnum;
import com.sinoif.esb.enums.ResponseState;
import com.sinoif.esb.enums.TypeActiveEnum;
import com.sinoif.esb.enums.TypeTransferEnum;
import com.sinoif.esb.port.bean.Interface;
import com.sinoif.esb.port.bean.InvokeResult;
import com.sinoif.esb.utils.DateUtils;
import com.sinoif.esb.utils.InvokeParamHolder;
import com.sinoif.esb.utils.sequence.SequenceUtil;
import com.sinoif.esbimpl.port.bean.InterfaceInvokeLog;
import com.sinoif.esbimpl.port.interfaces.InterfaceContext;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.LinkedHashMap;
import java.util.Objects;


/***
 * @author yuanyixiong
 * @date 2019/10/29
 * @describe 接口调用, 记录接口信息
 */
@Aspect
@Component
@Order(1)
public class InvokeLogAspect {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    InterfaceContext interfaceContext;

    @Autowired
    private EsbCoreRemoteService esbCoreRemoteService;

    /**
     * 切片记录请求信息
     *
     * @param joinPoint
     * @return
     * @throws Throwable
     */
    @Around("@annotation(com.sinoif.esbimpl.port.annotation.InvokeLog)")
    public Object invokeAspect(ProceedingJoinPoint joinPoint) throws Throwable {
        logger.info("InvokeLogAspect starting ......");

//        /**没按照约定的情况一:无参数**/
//        if (joinPoint.getArgs().length == 0) {
//            return joinPoint.proceed();
//        }
//
//        /**没按照约定的情况二:不存在Interface参数,或者有多个Interface参数**/
//        List<Interface> interfaceList = Arrays.stream(joinPoint.getArgs()).filter(param -> param instanceof Interface).map(param -> (Interface) param).collect(Collectors.toList());
//        if (CollectionUtils.isEmpty(interfaceList) || interfaceList.size() != 1) {
//            return joinPoint.proceed(joinPoint.getArgs());
//        }

        /**处理调用信息**/
//        Interface param = null;
        Object result = null;
        InvokeResult invokeResult = null;
        LocalDateTime interfaceInvokeTime = null, completeTime = null;
        try {
//            /**请求参数**/
//            param = interfaceList.get(NumberUtils.INTEGER_ZERO);
            /**执行结果**/
            interfaceInvokeTime = LocalDateTime.now();
            result = joinPoint.proceed(joinPoint.getArgs());

            //处理结果
            if (Objects.nonNull(result) && result instanceof InvokeResult) {
                invokeResult = (InvokeResult) result;
            }
        } catch (Exception e) {
            logger.error("invokeAspect:{}", e);
        } finally {
            saveInvokeLog(invokeResult, interfaceInvokeTime);
            return result;
        }
    }

    /**
     * 保存记录
     *
     * @param invokeResult
     */
    public void saveInvokeLog(InvokeResult invokeResult, LocalDateTime interfaceInvokeTime) {
        LocalDateTime completeTime = LocalDateTime.now();
        Long executeTime = completeTime.toInstant(ZoneOffset.of("+8")).toEpochMilli() - interfaceInvokeTime.toInstant(ZoneOffset.of("+8")).toEpochMilli();

        /**初始保存数据**/
        InterfaceInvokeLog interfaceInvokeLog = new InterfaceInvokeLog();
        interfaceInvokeLog.setInvokeResult(StringUtils.EMPTY);
        interfaceInvokeLog.setResponseStatus(ResponseState.FAIL);
        interfaceInvokeLog.setFailMessage(StringUtils.EMPTY);
        interfaceInvokeLog.setInvokeTime(DateUtils.formatLocalDateTimeToString(interfaceInvokeTime, DateUtils.Format.FORMAT_YMDHMS));
        interfaceInvokeLog.setExecuteDuration(executeTime);
        Interface ief = invokeResult.getIfe();
        interfaceInvokeLog.setAppId(ief.getAppId() + "");
        interfaceInvokeLog.setOutputSystem(ief.getAppName());
        interfaceInvokeLog.setTypeTransfer(ief.getTypeTransfer().getCode());
        interfaceInvokeLog.setTypeActive(ief.getTypeActive().getCode());
        interfaceInvokeLog.setInterfaceName(StringUtils.isEmpty(ief.getName()) ? (ief.getId() + "") : ief.getName());
        Interface inputInterface = interfaceContext.getInputInterface(ief.getTopic());
        if (inputInterface == null) {
            interfaceInvokeLog.setInputSystem(ief.getAppName());
        } else {
            interfaceInvokeLog.setInputSystem(inputInterface.getAppName());
        }
        String requestType = (ief.getProtocol() == ProtocolEnum.REST_API) ? ief.getRequestType().getRequestTypeName() : "webservice";
        String url = ief.getProtocolType() + "://" + ief.getServerIp() + ":" + ief.getServerPort() + "/" + ief.getSendUrl();
        interfaceInvokeLog.setRequestType(requestType);
        interfaceInvokeLog.setInterfaceAddress(url);
        interfaceInvokeLog.setCompleteTime(DateUtils.formatLocalDateTimeToString(completeTime, DateUtils.Format.FORMAT_YMDHMS));
        interfaceInvokeLog.setInterfaceId(ief.getId() + "");
        /**接口响应结果**/
        if (Objects.nonNull(invokeResult)) {

            interfaceInvokeLog.setInvokeResult(invokeResult.getResponse());
            interfaceInvokeLog.setResponseStatus(invokeResult.isSuccess() ? ResponseState.SUCCESS : ResponseState.FAIL);
            interfaceInvokeLog.setFailMessage(invokeResult.getMessage());
            interfaceInvokeLog.setId(SequenceUtil.getId());

            /**接口调用参数**/
            Interface esbInterface = invokeResult.getIfe();
            if (Objects.nonNull(esbInterface) && Objects.nonNull(esbInterface.getId())) {
                interfaceInvokeLog.setInterfaceId(String.valueOf(esbInterface.getId()));
                LinkedHashMap<String, String> invokeParam = InvokeParamHolder.get();
                if (invokeParam != null) {
                    interfaceInvokeLog.setInterfaceParams(invokeParam);
                } else {
                    interfaceInvokeLog.setInterfaceParams(esbInterface.getParams());
                }
                interfaceInvokeLog.setInterfaceCharset(esbInterface.getCharset());
            }
        }

        /**保存**/
//        JSONObject json = JSONObject.parseObject(JSON.toJSONString(interfaceInvokeLog, SerializerFeature.WriteNullStringAsEmpty, SerializerFeature.WriteMapNullValue));
        String json = JSON.toJSONString(interfaceInvokeLog);
        if (Objects.nonNull(interfaceInvokeLog.getInterfaceId())) {
            int dataCount = 0;
            String dataString = DateFormatUtils.getCurrentTime(PortalConstants.TIME_FORMATTER);
            if (invokeResult.isSuccess() && ief.getTypeActive() != TypeActiveEnum.SYNC_CALL) {//成功，则记录数据表
                if (ief.getTypeTransfer() == TypeTransferEnum.INPUT) {
                    interfaceInvokeLog.setData(invokeResult.getResponse());
                } else {
                    LinkedHashMap<String, String> invokeParam = InvokeParamHolder.get();
                    if (invokeParam == null) {
                        invokeParam = ief.getParams();
                    }
                    if (invokeParam != null) {
                        interfaceInvokeLog.setData(invokeParam.get("data"));
                    }
                }
                dataCount = getDataSize(interfaceInvokeLog.getData());
                if (interfaceInvokeLog.getData() != null) {
                    esbCoreRemoteService.saveLogData(interfaceInvokeLog.getId(), ief.getId() + "", dataString,
                            PortalConstants.COLLECTION_INVOKE_LOG_DATA + "_" + ief.getTopic(),
                            interfaceInvokeLog.getData(), null);
                }
            }
            interfaceInvokeLog.setData(null);// 单独记录数据之后，日中不再记录数据
            interfaceInvokeLog.setInvokeResult(null);
            json = JSON.toJSONString(interfaceInvokeLog);
            if (!invokeResult.isSuccess() && invokeResult.getInvokeNumber() <= 100 && ief.getTypeTransfer() == TypeTransferEnum.OUTPUT) {// 接口执行成功或者重试时产生的异常不记录在异常表中
                // 记录异常数据
                esbCoreRemoteService.saveLog(PortalConstants.COLLECTION_INVOKE_EXCEPTION, json, "_id", dataString, 0);
            }
            // 任何情况都记录日志主表
            esbCoreRemoteService.saveLog(PortalConstants.COLLECTION_INVOKE_LOG, json, "_id", dataString, dataCount);
            logger.info("保存请求记录:{}", json);
        } else {
            logger.info("保存请求记录失败,数据异常:{}", json);
        }
    }

    /**
     * 获取json数据的记录数量
     *
     * @param data json数据
     * @return 数据记录数量
     */
    private int getDataSize(String data) {
        String trimData = StringUtils.trim(data);
        if (StringUtils.isEmpty(trimData)) {
            return 0;
        }
        if (trimData.startsWith("[")) {
            return JSONArray.parseArray(data).size();
        } else {
            return 1;
        }
    }

}
