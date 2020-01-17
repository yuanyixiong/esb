package com.sinoif.esbimpl.port.controller;

import com.alibaba.fastjson.JSON;
import com.sinoif.esb.constants.PortalConstants;
import com.sinoif.esb.core.service.EsbCoreRemoteService;
import com.sinoif.esb.port.bean.Interface;
import com.sinoif.esb.port.bean.InvokeResult;
import com.sinoif.esb.utils.InvokeParamHolder;
import com.sinoif.esbimpl.port.annotation.InvokeLog;
import com.sinoif.esbimpl.port.interfaces.EsbPortalService;
import com.sinoif.esbimpl.port.interfaces.InterfaceContext;
import com.sinoif.esbimpl.port.service.ICallService;
import com.sinoif.esbimpl.port.service.bean.CallRequest;
import com.sinoif.esbimpl.port.service.router.ServiceRouterUtil;
import com.sinoif.esbimpl.port.utils.HttpUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


/**
 * 被动接口Controller, 接收第三方系统的请求。
 *
 * @author chenxj
 */
@Controller
@RequestMapping("/")
public class EsbController implements PortalConstants {
    private Logger logger = LoggerFactory.getLogger(EsbController.class);

    @Autowired
    private ServiceRouterUtil serviceRouterUtil;

    @Autowired
    private InterfaceContext interfaceContext;

    @Autowired
    @Qualifier(value = "esbCoreRemoteService")
    private EsbCoreRemoteService esbCoreRemoteService;

    @Autowired
    @Qualifier(value = "esbPortalService")
    private EsbPortalService esbPortEndpoint;

    /**
     * 监听外部请求, 只支持post。参数为json CallRequest
     *
     * @param request
     * @return
     */
    @InvokeLog
    @ResponseBody
    @RequestMapping(value = "portal/call/{interfaceId}", method = RequestMethod.POST)
    public InvokeResult call(@PathVariable String interfaceId, HttpServletRequest request) {
        Interface itf=null;
        InvokeResult result;
        try {
            String json = HttpUtils.getRequestPostJson(request);
            logger.info("[APP->PORTAL]接口接入外部请求, json={}", json);
            CallRequest req = JSON.parseObject(json, CallRequest.class);
            if (req == null) {
                return InvokeResult.fail(itf,PARAM_ERROR);
            }
            if (StringUtils.isBlank(interfaceId)) {
                return InvokeResult.fail(itf,ITF_ID_IS_EMPTY);
            }
            long itfId = Long.parseLong(interfaceId);
             itf = interfaceContext.getInterfaceById(itfId);
            if (itf == null) {
                return InvokeResult.fail(itf,ITF_NOT_EXITS);
            }
            itf.setRequestIp(request.getRemoteAddr());
            req.setInterfaceId(itfId);
            req.setItf(itf);
            ICallService callService = (ICallService) serviceRouterUtil.getServiceRoute(req.getService());
            if (callService == null) {
                result = InvokeResult.fail(itf,SERVICE_NOT_SUPPORT);
            }else{
                result = callService.process(req);
            }
        } catch (Exception e) {
            logger.error("[APP<--PORTAL]接口接入外部请求异常", e);
            result =  InvokeResult.fail(itf,UNKNOWN_ERROR+e.getMessage());
        } finally {
            InvokeParamHolder.clear();
        }
        return result;
    }

    /**
     * 查询当前esb缓存的接口信息
     *
     * @return json格式的接口信息
     */
    @RequestMapping("/interfaces")
    @ResponseBody
    public List<Interface> testOnly(){
        return interfaceContext.getAll();
    }

    /**
     * 查询当系统中缓存的关联接口信息
     *
     * @param topic 接口关联的topic
     * @return 与topic关联的接口数据json
     */
    @RequestMapping("/interfaces/topic/{topic}")
    @ResponseBody
    public List<Interface> getTopicRelatedInterface(@PathVariable String topic){
        return esbPortEndpoint.getInterfacesByTopics(topic);
    }

    /**
     * 查看系统中缓存的特点接口的信息
     *
     * @param id 接口id
     * @return 接口信息json接口
     */
    @RequestMapping("/interfaces/{id}")
    @ResponseBody
    public Interface testOnly(@PathVariable String id){
        return interfaceContext.getInterfaceById(Long.parseLong(id));
    }

    /**
     * 查看esb当前的kafka消息监器
     *
     * @return kafka消息监听器
     */
    @RequestMapping("/listeners")
    @ResponseBody
    public Object getListeners(){
        return esbCoreRemoteService.getListeners();
    }

    /**
     * 模拟输出接口，用于测试
     *
     * @param json 请求数据
     * @return 模拟接口收到的数据
     */
    @RequestMapping("/test")
    @ResponseBody
    public String mokeOutputInterface(@RequestBody String json){
        logger.debug("模拟程序接收到数据："+json);
        return "模拟程序接收到数据："+json;
    }

}
