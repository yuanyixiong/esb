package com.sinoif.esb.query.resource;

import com.sinoif.commonaip.util.DateFormatUtils;
import com.sinoif.esb.constants.PortalConstants;
import com.sinoif.esb.port.bean.Interface;
import com.sinoif.esb.port.service.EsbPortalRemoteService;
import com.sinoif.esb.query.config.monodb.MongoService;
import com.sinoif.esb.query.model.dto.InterfaceInvokeAlarmDTO;
import com.sinoif.esb.query.model.dto.InterfaceInvokeExceptionDto;
import com.sinoif.esb.query.model.dto.InterfaceInvokeLogDTO;
import com.sinoif.esb.query.model.param.BasicQueryParam;
import com.sinoif.esb.query.model.param.InterfaceInvokeAlarmParam;
import com.sinoif.esb.query.model.param.InterfaceInvokeLogParam;
import com.sinoif.esb.query.page.PageData;
import com.sinoif.esb.query.remote.InterfaceInvokeLogRemoteService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;

import static com.sinoif.commonaip.util.DateFormatUtils.date2String;

/**
 * @author 袁毅雄
 * @description 远程接口的实现-接口调用日志
 * @date 2019/11/1
 */
public class InterfaceInvokeLogRemoteResource implements InterfaceInvokeLogRemoteService {

    @Autowired
    MongoService mongoService;

    @Autowired
    EsbPortalRemoteService esbPortalRemoteService;

    /**
     * 接口调用记录查询
     *
     * @param param 接口调用记录查询参数
     * @return 接口调用记录
     */
    @Override
    public PageData<InterfaceInvokeLogDTO> queryInterfaceLog(InterfaceInvokeLogParam param) {
        HashMap<String, Object> queryMap = new HashMap<>();
        if (param.getInvokeTime() != null && param.getCompleteTime() != null) {
            String begin = date2String(param.getInvokeTime(), PortalConstants.TIME_FORMATTER);
            String end = date2String(param.getCompleteTime(), PortalConstants.TIME_FORMATTER);
            queryMap.put("invoke_time", String.format("between %s and %s", begin, end));
        }
        if (StringUtils.isNotEmpty(param.getInterfaceName())) {
            queryMap.put("interface_name", "like" + param.getInterfaceName());
        }
        Object[] queryResult = mongoService.queryByCondition(PortalConstants.COLLECTION_INVOKE_LOG, queryMap, param.getPageSize(), param.getPage(), true);
        return PageData.getPageData(queryResult, InterfaceInvokeLogDTO.class);
    }

    /**
     * 接口的数据分析
     *
     * @param param
     * @return
     */
    @Override
    public PageData<InterfaceInvokeLogDTO> interfaceAggregation(InterfaceInvokeLogParam param) {
        return interfaceAggregationType(param, null);
    }

    /**
     * 接口统计主表查询
     *
     * @param param 接口统计查询参数
     * @return 接口调用记录
     */
    @Override
    public PageData<InterfaceInvokeLogDTO> interfaceStatisticAggregation(InterfaceInvokeLogParam param) {
        return interfaceAggregationType(param, "cnt");
    }

    /**
     * 接口调用记录明细查询
     *
     * @param param 参数
     * @param countField 计数字段
     * @return 接口调用记录明细数据
     */
    private PageData<InterfaceInvokeLogDTO> interfaceAggregationType(InterfaceInvokeLogParam param, String countField) {
        HashMap<String, Object> paramMap = new HashMap<>();
        if (StringUtils.isNotEmpty(param.getInterfaceName())) {
            paramMap.put("interface_name", "like" + param.getInterfaceName());
        }
        if (param.getResponseStatus() != null) {
            paramMap.put("response_status", "=" + param.getResponseStatus());
        }
        if (param.getInvokeTime() != null && param.getCompleteTime() != null) {
            String begin = date2String(param.getInvokeTime(), PortalConstants.TIME_FORMATTER);
            String end = date2String(param.getCompleteTime(), PortalConstants.TIME_FORMATTER);
            paramMap.put("invoke_time", String.format("between %s and %s", begin, end));
        }
        HashMap<String, String> groupMap = new HashMap<>();
        groupMap.put("interface_name", "interfaceName");
        groupMap.put("input_system", "");
        groupMap.put("output_system", "");
        groupMap.put("interface_address", "");
        groupMap.put("interface_id", "");
        Object[] queryResult = mongoService.aggregateQuery(PortalConstants.COLLECTION_INVOKE_LOG,
                "interface_id", paramMap, groupMap, param.getPage(), param.getPageSize(), countField);
        return PageData.getPageData(queryResult, InterfaceInvokeLogDTO.class);
    }

    /**
     * 接口调用异常主表查询
     *
     * @param param 参数
     * @return 接口调用记录主表数据
     */
    @Override
    public PageData<InterfaceInvokeExceptionDto> queryInterfaceInvokeExceptionAgg(InterfaceInvokeLogParam param) {
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("handled", "=" + param.isHandled());
        if (param.getInterfaceName() != null) {
            paramMap.put("interface_name", "like" + param.getInterfaceName());
        }
        HashMap<String, String> groupMap = new HashMap<>();
        groupMap.put("interface_name", "interfaceName");
        groupMap.put("input_system", "");
        groupMap.put("output_system", "");
        groupMap.put("interface_address", "");
        groupMap.put("interface_id", "");
        Object[] queryResult = mongoService.aggregateQuery(PortalConstants.COLLECTION_INVOKE_EXCEPTION,
                "interface_name", paramMap, groupMap, param.getPage(), param.getPageSize(), null);
        return PageData.getPageData(queryResult, InterfaceInvokeExceptionDto.class);
    }

    /**
     * 接口调用异常明细数据查询
     *
     * @param param 参数
     * @return 接口调用异常明细数据
     */
    @Override
    public PageData<InterfaceInvokeLogDTO> queryInterfaceInvokeException(InterfaceInvokeLogParam param) {
        try {
            HashMap<String, Object> bos = new HashMap<>();
            if (param.getInterfaceId() > 0) {
                bos.put("interface_id", "=" + param.getInterfaceId());
            }
            if (param.getInterfaceName() != null) {
                bos.put("interface_name", "like" + param.getInterfaceName());
            }
            bos.put("handled", "=false");
            Object[] result = mongoService.queryByCondition(PortalConstants.COLLECTION_INVOKE_EXCEPTION,
                    bos, param.getPageSize(), param.getPage(), true);
            return PageData.getPageData(result, InterfaceInvokeLogDTO.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        PageData<InterfaceInvokeLogDTO> pageData = new PageData<>();
        pageData.setPage(param.getPage());
        return pageData;
    }

    /**
     * 接口调用日志明细数据（接口统计中的子表）
     *
     * @param param 参数
     * @return 接口统计数据
     */
    @Override
    public PageData<String> queryInterfaceInvokeDataLog(BasicQueryParam param) {
        HashMap<String, Object> queryMap = new HashMap<>();
        if (param.getInterfaceId() > 0) {
            queryMap.put("_interface_id", "=" + param.getInterfaceId());
        }
        Interface esbInterface = esbPortalRemoteService.getInterfaceById(param.getInterfaceId());
        Object[] queryResult = mongoService.queryByCondition(PortalConstants.COLLECTION_INVOKE_LOG_DATA + "_" + esbInterface.getTopic(), queryMap, param.getPageSize(),
                param.getPage(), true);
        return PageData.getPageData(queryResult, String.class);
    }

    /**
     * 接口预警数据查询，即每个接口调用的成功率
     *
     * @param param 参数
     * @return 接口预警数据
     */
    @Override
    public PageData<InterfaceInvokeAlarmDTO> queryInterfaceInvokeAlarm(InterfaceInvokeAlarmParam param) {
        Object[] result = mongoService.queryInvokeStatistic(param.getInterfaceIds(), param.getPage(), param.getPageSize()
                , DateFormatUtils.date2String(param.getBeginTime(), PortalConstants.TIME_FORMATTER),
                DateFormatUtils.date2String(param.getEndTime(), PortalConstants.TIME_FORMATTER));
        return PageData.getPageData(result, InterfaceInvokeAlarmDTO.class);
    }


}
