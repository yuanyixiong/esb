package com.sinoif.esb.query.remote;

import com.alibaba.fastjson.JSONArray;
import com.sinoif.esb.query.model.dto.*;
import com.sinoif.esb.query.model.param.*;
import com.sinoif.esb.query.page.PageData;

import java.util.List;

/**
 * @author 袁毅雄
 * @description 远程接口-接口调用日志
 * @date 2019/11/1
 */
public interface InterfaceInvokeLogRemoteService {

    /**
     * 接口日志
     * @param param
     * @return
     */
    PageData<InterfaceInvokeLogDTO> queryInterfaceLog(InterfaceInvokeLogParam param);

    /**
     * 接口的数据分析
     *
     * @param param
     * @return
     */
    PageData<InterfaceInvokeLogDTO> interfaceAggregation(InterfaceInvokeLogParam param);

    PageData<InterfaceInvokeLogDTO> interfaceStatisticAggregation(InterfaceInvokeLogParam param);

    PageData<InterfaceInvokeExceptionDto> queryInterfaceInvokeExceptionAgg(InterfaceInvokeLogParam param);

    PageData<InterfaceInvokeLogDTO> queryInterfaceInvokeException(InterfaceInvokeLogParam param);

    PageData<String> queryInterfaceInvokeDataLog(BasicQueryParam param);

    PageData<InterfaceInvokeAlarmDTO> queryInterfaceInvokeAlarm(InterfaceInvokeAlarmParam param);
}
