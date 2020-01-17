package com.sinoif.esbimpl.platform;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sinoif.esb.constants.PortalConstants;
import com.sinoif.esb.core.remote.CoreHeartbeatRemoteService;
import com.sinoif.esb.enums.ResponseState;
import com.sinoif.esb.port.bean.InvokeResult;
import com.sinoif.esb.port.dto.InterfaceApproveDTO;
import com.sinoif.esb.port.param.InterfaceApproveParam;
import com.sinoif.esb.port.remote.PortalHeartbeatRemoteService;
import com.sinoif.esb.port.service.EsbPortalRemoteService;
import com.sinoif.esb.port.service.InterfaceLogRemoteHandler;
import com.sinoif.esb.query.model.dto.*;
import com.sinoif.esb.query.model.param.ApproveDetailParam;
import com.sinoif.esb.query.model.param.InterfaceInvokeAlarmParam;
import com.sinoif.esb.query.model.param.InterfaceInvokeLogParam;
import com.sinoif.esb.query.page.PageData;
import com.sinoif.esb.query.remote.*;
import com.sinoif.esb.utils.Heartbeat;
import org.apache.commons.codec.Charsets;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import reactor.util.function.Tuple2;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 单元测试类，提供了esb-portal 模块对外提供的所有服务的单元测功能
 */
public class EsbPlatformApplication {
    public static void main(String[] args) throws IOException {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring/esb-platform.xml");
        context.start();
        EsbPortalRemoteService esbPortalRemoteService = (EsbPortalRemoteService) context.getBean("esbPortalRemoteService");
        InterfaceInvokeLogRemoteService interfaceInvokeLogRemoteService = (InterfaceInvokeLogRemoteService) context.getBean("interfaceInvokeLogRemoteService");
        EnvironmentRemoteService environmentRemoteService = (EnvironmentRemoteService) context.getBean("environmentRemoteService");
        InterfaceApproveInfoRemoteService approveInfoRemoteService = (InterfaceApproveInfoRemoteService) context.getBean("interfaceApproveInfoRemoteService");
        InterfaceLogRemoteHandler logRemoteHandler = (InterfaceLogRemoteHandler) context.getBean("invokeLogHandler");
        QueryHeartbeatRemoteService queryHeartbeat = (QueryHeartbeatRemoteService) context.getBean("queryHeartbeatRemoteService");
        PortalHeartbeatRemoteService portalHeartbeat = (PortalHeartbeatRemoteService) context.getBean("portalHeartbeatRemoteService");
        CoreHeartbeatRemoteService coreHeartbeat = (CoreHeartbeatRemoteService) context.getBean("coreHeartbeatRemoteService");
        IndexStatisticsRemoteService indexStatistics = (IndexStatisticsRemoteService) context.getBean("indexStatisticsRemoteService");

        Scanner scanner = new Scanner(System.in);

        PageData<InterfaceApproveDTO> approveData = null;
        PageData<InterfaceInvokeLogDTO> invokeExceptionData = null;
        String approveDatas = "";

        String testMenu = "1：获取webservice接口信息\n2：测试接口注册\n3：测试主动输入+主动输出\n" +
                "4：待审核列表\n5:数据审核\n6:接口异常列表\n7:忽略异常\n8:重试异常9：被动输入\n10:被动输出\n" +
                "11:日志接口调用\n12:接口数据分析\n13:接口统计\n14:节点系统状态";
        System.out.println(testMenu);
        int cmd = scanner.nextInt();
        while (cmd != -1) {
            try {
                System.out.println(testMenu);
                if (cmd == 1) {
                    System.out.println(esbPortalRemoteService.getWsdlProperty("http://180.168.195.45:18080/uapws/service/com.yonyou.itf.mdm07.sharing.IMdSharingCenterService?wsdl"));
                } else if (cmd == 2) {
                    // 注册主动输入接口
                    System.out.println(esbPortalRemoteService.registerInterfaces(TestUtil.generateQueryBranchInterface()));
                    // 注册主动输出接口，本地测试，需要审核
                    System.out.println(esbPortalRemoteService.registerInterfaces(TestUtil.passiveOutputNeedApprove()));
                    // 注册主动输出接口，本地测试，不需要审核
                    System.out.println(esbPortalRemoteService.registerInterfaces(TestUtil.passiveOutputNoArrove()));
                    // 注册一个不可访问地址，测试超时异常
                    System.out.println(esbPortalRemoteService.registerInterfaces(TestUtil.get2061On()));
                    // 注册被动输入接口
                    System.out.println(esbPortalRemoteService.registerInterfaces(TestUtil.generatePassiveInterfaceInput()));
                    // 注册被动输出接口
                    System.out.println(esbPortalRemoteService.registerInterfaces(TestUtil.generatePassiveInterfaceOutput()));
                } else if (cmd == 3) {
                    // 测试主动输入+3个主动输出
                    // 1. 需要审核
                    // 2. 请求超时
                    // 3. 正常输出
                    InvokeResult result = esbPortalRemoteService.invokeInterfaceById(2074);
                    System.out.println("return result:" + result.getResponse());
                } else if (cmd == 4) {
                    // 测试查询待审核列表：忽略接口异常
                    // 获取待审核列表
                    InterfaceApproveParam param = new InterfaceApproveParam();
                    param.setInterfaceId(30);
                    param.setPageSize(2);
                    param.setPage(1);
                    approveData = approveInfoRemoteService.queryApproveData(param);

                    ApproveDetailParam detailParam = new ApproveDetailParam();
                    detailParam.setInterfaceId(30);
                    detailParam.setPage(1);
                    detailParam.setPageSize(2);
                    detailParam.setApprovedStatus(PortalConstants.NOT_PROCESSED);
                    System.out.println("待审核列表：" + JSON.toJSONString(approveData));
                    PageData approveDatas1 = approveInfoRemoteService.queryApproveDetail(detailParam);
                    System.out.println("待审核数据：" + JSON.toJSONString(approveDatas1.getDatas()));
                    logRemoteHandler.approveInterfaceData(373102194821705728l,true);
                    approveDatas=JSON.toJSONString(approveDatas1.getDatas());
                } else if (cmd == 5) {
                    // 测试审核数据
                    if (approveData.getDatas().size() == 0) {
                        System.out.println("输入5查询接口异常列表");
                    } else {
                        JSONArray jsonArray = JSONArray.parseArray(approveDatas);
                        JSONObject object = jsonArray.getJSONObject(0);
                        logRemoteHandler.approveInterfaceData(object.getLong("_id"), true);
                    }
                } else if (cmd == 6) {
                    InterfaceInvokeLogParam logParam = new InterfaceInvokeLogParam();
                    logParam.setPage(1);
                    logParam.setPageSize(2);
                    PageData<InterfaceInvokeExceptionDto> exceptionDtos = interfaceInvokeLogRemoteService.queryInterfaceInvokeExceptionAgg(logParam);
                    System.out.println(JSON.toJSONString(exceptionDtos.getDatas()));
                    // 测试未处理异常查询列表：
                    InterfaceInvokeLogParam invokeLogParam = new InterfaceInvokeLogParam();
                    invokeLogParam.setInterfaceId(Long.parseLong(exceptionDtos.getDatas().get(0).getInterfaceId()));
                    invokeLogParam.setHandled(false);
                    invokeLogParam.setPage(1);
                    invokeLogParam.setPageSize(10);
                    invokeExceptionData = interfaceInvokeLogRemoteService.queryInterfaceInvokeException(invokeLogParam);
                    System.out.println(JSON.toJSONString(invokeExceptionData));
                } else if (cmd == 7) {
                    // 测试异常，忽略
                    logRemoteHandler.markComplete(invokeExceptionData.getDatas().get(0).getId());
                } else if (cmd == 8) {
                    // 测试异常，重试
                    logRemoteHandler.invokeRetry(invokeExceptionData.getDatas().get(0).getId());
                } else if (cmd == 9) {
                    // 测试被动输入
                    testPassiveInput();
                } else if (cmd == 10) {
                    // 测试被动输出
                    testPassiveOutput();
                } else if (cmd == 11) {
                    // 测试接口日志
                    String begin = "2019-10-30 14:51:01", end = "2019-12-01 14:53:51";
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date beginTime = sdf.parse(begin);
                    Date endTime = sdf.parse(end);
                    /***********接口日志v1*************/
                    InterfaceInvokeLogParam param = new InterfaceInvokeLogParam();
                    param.setPage(1);
                    param.setPageSize(10);
                    param.setInvokeTime(beginTime);
                    param.setCompleteTime(endTime);
                    System.out.println(JSON.toJSONString(interfaceInvokeLogRemoteService.queryInterfaceLog(param)));
                } else if (cmd == 12) {
                    // 测试接口统计
                    String begin = "2019-10-30 14:51:01", end = "2019-12-01 14:53:51";
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date beginTime = sdf.parse(begin);
                    Date endTime = sdf.parse(end);
                    InterfaceInvokeLogParam param = new InterfaceInvokeLogParam();
                    param.setResponseStatus(ResponseState.SUCCESS);
                    param.setInvokeTime(beginTime);
                    param.setCompleteTime(endTime);
                    param.setPage(1);
                    param.setPageSize(10);
                    System.out.println(JSON.toJSONString(interfaceInvokeLogRemoteService.interfaceStatisticAggregation(param)));
                    /**统计系统*/

                } else if (cmd == 13) {
                    /**统计系统-接口数据**/
                    InterfaceInvokeLogParam param = new InterfaceInvokeLogParam();
                    param.setInterfaceId(13);
                    param.setPageSize(10);
                    param.setPage(1);
                    System.out.println(JSON.toJSONString(interfaceInvokeLogRemoteService.queryInterfaceInvokeDataLog(param)));

                } else if (cmd == 14) {
                    /**节点监控**/
                    List<EnvironmentInformationDTO> result = environmentRemoteService.environmentInformation();
                    System.out.println(JSON.toJSONString(result));
                    EnvironmentInformationAggregationDTO envAgg = environmentRemoteService.environmentInformationAggregation();
                    System.out.println(JSON.toJSONString(envAgg));
                    System.out.println(JSON.toJSONString(envAgg.aggregationCpuLoad()));
                    System.out.println(JSON.toJSONString(envAgg.aggregationMemory()));
                    System.out.println(JSON.toJSONString(envAgg.aggregationNetwork()));
                    System.out.println(JSON.toJSONString(envAgg.aggregationDisk()));
                } else if (cmd == 15) {
                    // 接口预警
                    InterfaceInvokeAlarmParam param = new InterfaceInvokeAlarmParam();
                    ArrayList<String> ids = new ArrayList<>();
                    ids.add("3");
                    ids.add("2061");
                    String begin = "2019-10-30 14:51:01", end = "2019-12-01 14:53:51";
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    param.setInterfaceIds(ids);
                    param.setBeginTime(sdf.parse(begin));
                    param.setEndTime(sdf.parse(end));
                    param.setPage(1);
                    param.setPageSize(10);
                    PageData<InterfaceInvokeAlarmDTO> page = interfaceInvokeLogRemoteService.queryInterfaceInvokeAlarm(param);
                    List result = page.getDatas().stream().map(x -> {
                        HashMap<String,HashMap<String,String>> interfaceMap = new HashMap<>();
                        HashMap<String,String> countMap = new HashMap<>();
                        countMap.put(x.get_id().getStatus(),x.getSum()+"");
                        interfaceMap.put(x.get_id().getInterfaceId(),countMap);
                        return interfaceMap;
                    }).collect(Collectors.toList());
                    System.out.println("接口预警数据：" + JSON.toJSONString(result));
                } else if (cmd == 19) {
                    //服务存活监控
                    Tuple2<Boolean, List<ServiceInformationDTO>> queryService = Heartbeat.heartbeat(() -> queryHeartbeat.heartbeat(), () -> queryHeartbeat.serviceSurviveState());
                    Tuple2<Boolean, List<ServiceInformationDTO>> portalService = Heartbeat.heartbeat(() -> portalHeartbeat.heartbeat(), () -> portalHeartbeat.serviceSurviveState());
                    Tuple2<Boolean, List<ServiceInformationDTO>> coreService = Heartbeat.heartbeat(() -> coreHeartbeat.heartbeat(), () -> coreHeartbeat.serviceSurviveState());
                    System.out.printf("query 服务是否存活：%s,服务器现有服务状态：%s\n", queryService.getT1(), JSONArray.toJSONString(queryService.getT2()));
                    System.out.printf("portal 服务是否存活：%s,服务器现有服务状态：%s\n", portalService.getT1(), JSONArray.toJSONString(portalService.getT2()));
                    System.out.printf("core 服务是否存活：%s,服务器现有服务状态：%s\n", coreService.getT1(), JSONArray.toJSONString(coreService.getT2()));
                } else if (cmd == 20) {
                    //首页统计
                    String current = "2019-11-23 14:51:01", begin = "2019-11-1 14:51:01", end = "2019-12-01 14:53:51";
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date currentTime = sdf.parse(current);
                    Date beginTime = sdf.parse(begin);
                    Date endTime = sdf.parse(end);
                    IndexStatisticsDTO indexStatisticsDTO = indexStatistics.statistics(currentTime);
                    List<IndexStatisticalFigureDTO> list = indexStatistics.statisticalFigure(beginTime, endTime);
                    System.out.println(JSON.toJSONString(indexStatisticsDTO));
                    System.out.println(JSONArray.toJSONString(list));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            cmd = scanner.nextInt();
        }
    }

    /**
     * 测试被动输入接口
     */
    private static void testPassiveInput() {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost("http://localhost:8080/esb-portal/portal/call/6");
        HashMap<String, Object> requestMap = new HashMap<>();
        // 接口路由参数
        requestMap.put("interfaceId", "6");
        requestMap.put("service", "reactive_input");
        requestMap.put("appCode", "platform_test");
        // 请求接口业务参数
        LinkedHashMap<String, String> dataMap = new LinkedHashMap<>();
        requestMap.put("data", dataMap);
        dataMap.put("code", "86");
        dataMap.put("country_zone", "zh-CN");

        StringEntity entity = new StringEntity(JSON.toJSONString(requestMap), Charsets.UTF_8);
        httpPost.setEntity(entity);
        httpPost.addHeader("Content-Type", "application/json;charset=UTF-8");
        try {
            String result = httpclient.execute(httpPost, handler -> {
                int status = handler.getStatusLine().getStatusCode();
                if (status >= 200 && status < 300) {
                    HttpEntity e1 = handler.getEntity();
                    return e1 != null ? EntityUtils.toString(e1) : "";
                } else {
                    return "";
                }
            });
            System.out.println("result from esb:" + result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 测试被动输出接口
     */
    private static void testPassiveOutput() {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost("http://localhost:8080/esb-portal/portal/call/7");
        HashMap<String, Object> requestMap = new HashMap<>();
        requestMap.put("interfaceId", "7");
        requestMap.put("service", "reactive_output");
        requestMap.put("appCode", "platform_test");
        LinkedHashMap<String, String> paramMap = new LinkedHashMap<>();
        paramMap.put("code", "=G1023");
        // 以下不正确的参数格式，会触发被动接口执行异常
//        paramMap.put("code","G1023");
        requestMap.put("data", paramMap);

        StringEntity entity = new StringEntity(JSON.toJSONString(requestMap), Charsets.UTF_8);
        httpPost.setEntity(entity);
        httpPost.addHeader("Content-Type", "application/json;charset=UTF-8");
        try {
            String result = httpclient.execute(httpPost, handler -> {
                int status = handler.getStatusLine().getStatusCode();
                if (status >= 200 && status < 300) {
                    HttpEntity e1 = handler.getEntity();
                    return e1 != null ? EntityUtils.toString(e1) : "";
                } else {
                    return "";
                }
            });
            System.out.println("result from esb:" + result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}