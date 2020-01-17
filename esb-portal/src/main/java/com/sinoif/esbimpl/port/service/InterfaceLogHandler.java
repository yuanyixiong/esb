package com.sinoif.esbimpl.port.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sinoif.esb.constants.PortalConstants;
import com.sinoif.esb.core.service.EsbCoreRemoteService;
import com.sinoif.esb.port.bean.Interface;
import com.sinoif.esb.port.bean.InvokeResult;
import com.sinoif.esb.port.service.InterfaceLogRemoteHandler;
import com.sinoif.esb.utils.InvokeParamHolder;
import com.sinoif.esbimpl.port.interfaces.EsbPortalService;
import com.sinoif.esbimpl.port.interfaces.InterfaceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * 接口消息处理服务，审核、异常数据重发
 */
public class InterfaceLogHandler implements InterfaceLogRemoteHandler {

    @Autowired
    EsbCoreRemoteService esbCoreRemoteService;

    @Autowired
    @Qualifier(value = "esbPortalService")
    EsbPortalService esbPortEndpoint;

    InterfaceContext interfaceContext;

    @Autowired
    @Qualifier(value = "esbPortalService")
    EsbPortalService esbPortalService;

    /**
     * 将异常标记为已处理
     *
     * @param id 异常记录id
     */
    @Override
    public void markComplete(long id) {
        HashMap<String, String> updateFields = new HashMap<>();
        updateFields.put("handled", "true");
        esbCoreRemoteService.mongoDbUpdate(PortalConstants.COLLECTION_INVOKE_EXCEPTION, updateFields, PortalConstants.ID_INVOKE_LOG, id);
        markComplete(id);
    }

    /**
     * 接口异常重试
     *
     * @param id 异常记录id
     */
    @Override
    public InvokeResult invokeRetry(long id) {
        try {
            HashMap<String, String> queryParms = new HashMap<>();
            queryParms.put(PortalConstants.ID_INVOKE_LOG, "=" + id);
            String result = esbCoreRemoteService.mongoDbQuery(PortalConstants.COLLECTION_INVOKE_EXCEPTION, queryParms);
            JSONArray objects = JSONObject.parseArray(result);
            if (objects.size() > 0) {
                JSONObject interfaceLogObject = (JSONObject) objects.get(0);
                boolean handled = interfaceLogObject.getBoolean("handled");
                if(handled){
                    return InvokeResult.fail(null, "该条数据已处理");
                }
                long interfaceId = Long.parseLong(interfaceLogObject.getString("interface_id"));
                Interface esbInterface = interfaceContext.getInterfaceById(interfaceId);
                String executeParam = interfaceLogObject.getString("interface_params");
                LinkedHashMap paramMap = JSON.parseObject(executeParam, LinkedHashMap.class);
                InvokeParamHolder.set(paramMap);
                InvokeResult invokeResult = esbPortEndpoint.invokeInterfaceDirectly(esbInterface, false, id);
                if (invokeResult.isSuccess()) {
                    markComplete(id);
                }
                return invokeResult;
            } else {
                return InvokeResult.fail(null, "该数据不存在");
            }
        } catch (Exception e) {
            return InvokeResult.fail(null, e.getMessage());
        }
    }

    /**
     * 审核数据
     *
     * @param dataId   待审核数据id
     * @param approved 是否通过审核，true通过 false拒绝
     * @return 审核通过后执行输出的结果
     */
    @Override
    public InvokeResult approveInterfaceData(long dataId, boolean approved) {
        JSONObject object = esbCoreRemoteService.findDataById(PortalConstants.COLLECTION_APPROVE_INFO_DATA, "_id", dataId + "");
        if (object == null) {
            return InvokeResult.fail(null, PortalConstants.DATA_APPROVED);
        }
        long interfaceId = object.getLong("_interface_id");
        long approveDataId = object.getLong("_id");
        Interface esbInterface = interfaceContext.getInterfaceById(interfaceId);
        LinkedHashMap invokeParam = new LinkedHashMap();
        InvokeResult result;
        // 更新审核字段值。
        HashMap<String, String> updateMap = new HashMap<>();
        String message;
        if (approved) {
            JSONObject approveDetail = esbCoreRemoteService.findDataById(PortalConstants.COLLECTION_APPROVE_INFO_DATA, "_id", approveDataId + "");
            if (!PortalConstants.NOT_PROCESSED.equals(approveDetail.getString(PortalConstants.COL_APPROVE))) {
                message = "当前记录已经处理";
                return InvokeResult.fail(null, "{\"updatedCount\":\"" + 0 + "\",\"message:\"" + message + "\"}");
            }
            object.remove("_interface_id");
            object.remove("_parent");
            object.remove(PortalConstants.COL_APPROVE);
            object.remove("_time");
            object.remove("_id");
            object.remove("_topic");
            JSONArray arrayWrapper = new JSONArray();
            arrayWrapper.add(object);
            InvokeParamHolder.set(invokeParam);
            invokeParam.put("data", arrayWrapper.toJSONString());
            message = esbPortalService.invokeInterfaceDirectly(esbInterface, false, 100).getMessage();
            updateMap.put(PortalConstants.COL_APPROVE, PortalConstants.APPROVED);
        } else {
            message = "审核拒绝";
            updateMap.put(PortalConstants.COL_APPROVE, PortalConstants.REJECTED);
        }
        updateMap.put(PortalConstants.COL_APPROVE_TIME, LocalDateTime.now().format(DateTimeFormatter.ofPattern(PortalConstants.TIME_FORMATTER)));
        long updateCount = esbCoreRemoteService.mongoDbUpdate(PortalConstants.COLLECTION_APPROVE_INFO_DATA, updateMap, "_id", approveDataId);
        return InvokeResult.success(null, "{\"updatedCount\":\"" + updateCount + "\",\"message:\"" + message + "\"}");
    }

    @Override
    public void approveReject(long id) {

    }

    public void setInterfaceContext(InterfaceContext interfaceContext) {
        this.interfaceContext = interfaceContext;
    }
}
