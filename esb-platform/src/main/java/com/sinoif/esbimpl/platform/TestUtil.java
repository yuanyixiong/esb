package com.sinoif.esbimpl.platform;

import com.alibaba.fastjson.JSONObject;
import com.sinoif.esb.enums.*;
import com.sinoif.esb.port.bean.Interface;
//import org.mortbay.util.ajax.JSON;

import java.util.LinkedHashMap;

public class TestUtil {
    public static Interface generateImportInterface() {
        Interface i1 = new Interface();
        i1.setId(1);
//        i1.setServerIp("192.168.2.111");
        i1.setServerIp("111.200.244.194");
        i1.setServerPort(18080);
        i1.setSendUrl("contact/read/selectDepartment");
        i1.setProtocolType(ProtocolTypeEnum.HTTP);
        i1.setRequestType(RequestType.POST);
        i1.setTypeActive(TypeActiveEnum.INITIATIVE);
        i1.setTypeTransfer(TypeTransferEnum.INPUT);
        i1.setAppName("统一结口路由");
        i1.setRequestParamType(RequestParamTypeEnum.FORMDATA);
        i1.setTopic("esb-branch");
        i1.setProtocol(ProtocolEnum.REST_API);
        i1.setKeyProperty("code");
        i1.setAppId(-1);
        LinkedHashMap<String,String> params=new LinkedHashMap<>();
        i1.setParams(params);
        return i1;
    }

    public static Interface generateOutputInterface() {
        Interface i1 = new Interface();
        i1.setServerIp("180.168.195.45");
        i1.setServerPort(18080);
        i1.setSendUrl("uapws/service/com.yonyou.itf.mdm07.sharing.IMdSharingCenterService?wsdl");
        i1.setId(2);
        i1.setNeedApprove(true);
        i1.setTypeTransfer(TypeTransferEnum.OUTPUT);
        i1.setTypeActive(TypeActiveEnum.INITIATIVE);
        i1.setProtocolType(ProtocolTypeEnum.HTTP);
        i1.setProtocol(ProtocolEnum.WEBSERVICE);
        i1.setTargetNameSpace("http://sharing.mdm07.itf.yonyou.com/IMdSharingCenterService");
        i1.setLocalPart("IMdSharingCenterServiceSOAP11Binding");
        i1.setWsMethod("urn:insertMd");
        LinkedHashMap<String, String> params = new LinkedHashMap<>();
        params.put("string", "MDM");
        params.put("string1", "sinoif_org_list");
        params.put("string2", "${data}");
        i1.setAppName("mainDataPlatform");
        i1.setTopic("esb-branch");
        i1.setParams(params);
        i1.setAppId(33);
        return i1;
    }

    public static Interface generateQueryBranchInterface() {
        String j = "{\n" +
                "    \"id\": 3,\n" +
                "    \"typeSync\": \"ASYNC\",\n" +
                "    \"protocolType\": \"HTTP\",\n" +
                "    \"protocol\": \"WEBSERVICE\",\n" +
                "    \"serverIp\": \"180.168.195.45\",\n" +
                "    \"serverPort\": 18080,\n" +
                "    \"sendUrl\": \"uapws/service/com.yonyou.itf.mdm07.sharing.IMdSharingCenterService?wsdl\",\n" +
                "    \"keyProperty\": \"code\",\n" +
                "    \"needApprove\": true,\n" +
                "    \"requestParamType\": \"XML\",\n" +
                "    \"requestType\": null,\n" +
                "    \"responseType\": \"\",\n" +
                "    \"targetNameSpace\": \"http://sharing.mdm07.itf.yonyou.com/IMdSharingCenterService\",\n" +
                "    \"localPart\": \"IMdSharingCenterServiceSOAP11Binding\",\n" +
                "    \"wsMethod\": \"queryMdByCondition\",\n" +
                "    \"typeActive\": \"INITIATIVE\",\n" +
                "    \"typeTransfer\": \"INPUT\",\n" +
                "    \"topic\": \"sinoif_org_list\",\n" +
                "    \"appId\": 1007,\n" +
                "    \"appName\": \"主数据平台\",\n" +
                "    \"params\": {\n" +
                "        \"string\": \"MDM\",\n" +
                "        \"string1\": \"sinoif_org_list\",\n" +
                "        \"string2\": \"1=1\",\n" +
                "        \"boolean\": \"true\",\n" +
                "        \"string4\": \"json\"\n" +
                "    },\n" +
                "    \"processorName\": \"com.sinoif.esbimpl.port.interfaces.processor.DefaultInterfaceResponseProcessor\",\n" +
                "    \"charset\": null,\n" +
                "    \"timeOut\": 0\n" +
                "}";
        return JSONObject.parseObject(j,Interface.class);
    }

    public static Interface passiveOutputNeedApprove() {
        Interface i1 = new Interface();
        i1.setId(30);
//        i1.setServerIp("192.168.2.111");
        i1.setServerIp("localhost");
        i1.setServerPort(8080);
        i1.setSendUrl("esb-portal/test");
        i1.setProtocolType(ProtocolTypeEnum.HTTP);
        i1.setRequestType(RequestType.POST);
        i1.setTypeActive(TypeActiveEnum.INITIATIVE);
        i1.setTypeTransfer(TypeTransferEnum.OUTPUT);
        i1.setAppName("统一结口路由");
        i1.setRequestParamType(RequestParamTypeEnum.JSON);
        i1.setTopic("sinoif_org_list");
        i1.setProtocol(ProtocolEnum.REST_API);
        i1.setKeyProperty("code");
        i1.setAppId(3);
        i1.setAppName("test");
        i1.setNeedApprove(true);
        LinkedHashMap<String,String> params=new LinkedHashMap<>();
        params.put("xxx","${data}");
        i1.setParams(params);
        return i1;
    }

    public static Interface passiveOutputNoArrove() {
        Interface i1 = new Interface();
        i1.setId(31);
//        i1.setServerIp("192.168.2.111");
        i1.setServerIp("localhost");
        i1.setServerPort(8080);
        i1.setSendUrl("esb-portal/test");
        i1.setProtocolType(ProtocolTypeEnum.HTTP);
        i1.setRequestType(RequestType.POST);
        i1.setTypeActive(TypeActiveEnum.INITIATIVE);
        i1.setTypeTransfer(TypeTransferEnum.OUTPUT);
        i1.setAppName("统一结口路由");
        i1.setRequestParamType(RequestParamTypeEnum.JSON);
        i1.setTopic("sinoif_org_list");
        i1.setProtocol(ProtocolEnum.REST_API);
        i1.setKeyProperty("code");
        i1.setAppId(33);
        i1.setAppName("test");
        i1.setNeedApprove(false);
        LinkedHashMap<String,String> params=new LinkedHashMap<>();
        params.put("data","${data}");
        i1.setParams(params);
        return i1;
    }

    public static Interface generateRestApiOutputInterface() {
        Interface i1 = new Interface();
        i1.setId(4);
        i1.setServerIp("localhost");
        i1.setServerPort(8080);
        i1.setSendUrl("esb-portal/portal/testBranch");
        i1.setProtocolType(ProtocolTypeEnum.HTTP);
        i1.setRequestType(RequestType.POST);
        i1.setTypeActive(TypeActiveEnum.INITIATIVE);
        i1.setTypeTransfer(TypeTransferEnum.OUTPUT);
        i1.setAppName("app1");
        i1.setRequestParamType(RequestParamTypeEnum.JSON);
        i1.setTopic("esb-branch-query");
        i1.setProtocol(ProtocolEnum.REST_API);
        i1.setKeyProperty("");
        LinkedHashMap<String,String> params = new LinkedHashMap<>();
        params.put("anynameyoulikeit","${data}");
        i1.setParams(params);
        i1.setAppId(5);
        return i1;
    }

    public static Interface generateRestApiOutputInterface2() {
        Interface i1 = new Interface();
        i1.setId(5);
        i1.setServerIp("localhost");
        i1.setServerPort(8080);
        i1.setSendUrl("esb-portal/portal/testBranch");
        i1.setProtocolType(ProtocolTypeEnum.HTTP);
        i1.setRequestType(RequestType.POST);
        i1.setTypeActive(TypeActiveEnum.INITIATIVE);
        i1.setTypeTransfer(TypeTransferEnum.OUTPUT);
        i1.setAppName("app2");
        i1.setRequestParamType(RequestParamTypeEnum.JSON);
        i1.setTopic("esb-branch-partial");
        i1.setProtocol(ProtocolEnum.REST_API);
        i1.setKeyProperty("");
        LinkedHashMap<String,String> params = new LinkedHashMap<>();
        params.put("anynameyoulikeit","${data}");
        i1.setParams(params);
        i1.setAppId(6);
        return i1;
    }

    /**
     * 生成测试被动输入接口
     * 1. url为固定值（可以不配）
     * 2. params 仅填map的键，值可以不填。用于检验提交过来的参数
     * 3. topic 接口数具对应的kafka 队列，与mondodb的collection
     * @return 接口对象
     */
    public static Interface generatePassiveInterfaceInput(){
        Interface i5 = new Interface();
        i5.setId(6);
        i5.setSendUrl("passiveInputInterfaceTest");
        LinkedHashMap<String,String> parames = new LinkedHashMap<>();
        parames.put("contryZone","zh-CN");
        parames.put("code","86");
        parames.put("mdm_createdby_type","auto created");
        i5.setParams(parames);

        i5.setTopic("esb-branch-query");
        i5.setProtocol(ProtocolEnum.REST_API);
        i5.setProtocolType(ProtocolTypeEnum.HTTP);
        i5.setTypeTransfer(TypeTransferEnum.INPUT);
        i5.setTypeActive(TypeActiveEnum.REACTIVE);
        i5.setKeyProperty("code");//不能为空，不然不能注册
        i5.setAppId(7);
        return i5;
    }

    // 生成被动输出接口
    public static Interface generatePassiveInterfaceOutput(){
        Interface i5 = new Interface();
        i5.setId(7);
        i5.setAppId(123);
        i5.setAppName("test7");
        i5.setSendUrl("passiveOutInterfaceTest");
        i5.setTopic("sinoif_org_list");
        i5.setTypeTransfer(TypeTransferEnum.OUTPUT);
        i5.setTypeActive(TypeActiveEnum.REACTIVE);
        i5.setAppId(8);

        LinkedHashMap<String,String> params = new LinkedHashMap<>();
        params.put("string","MDM");
        params.put("string1","org_list");
        params.put("string2","code = 'G00002'");
        params.put("boolean","true");
        params.put("string4","json");
        i5.setParams(params);

        return i5;
    }

    // 生成同步接口, 全主数据查询分支机构接口的同步版本,但是需要参数同步调用的时候提供
    public static Interface generateSynInterface(){
        Interface i1 = new Interface();
        i1.setTypeSync(TypeSyncEnum.SYNC);
        i1.setTypeTransfer(TypeTransferEnum.OUTPUT);
        i1.setServerIp("180.168.195.45");
        i1.setServerPort(18080);
        i1.setSendUrl("uapws/service/com.yonyou.itf.mdm07.sharing.IMdSharingCenterService?wsdl");
        i1.setId(10);
        i1.setNeedApprove(false);
        i1.setTypeActive(TypeActiveEnum.INITIATIVE);
        i1.setProtocolType(ProtocolTypeEnum.HTTP);
        i1.setProtocol(ProtocolEnum.WEBSERVICE);
        i1.setTargetNameSpace("http://sharing.mdm07.itf.yonyou.com/IMdSharingCenterService");
        i1.setLocalPart("IMdSharingCenterServiceSOAP11Binding");
        i1.setWsMethod("urn:queryMdByCondition");
        i1.setAppName("mainDataPlatform");
        i1.setTopic("esb-branch-query");
        i1.setKeyProperty("code");
        i1.setAppId(9);
        return i1;
    }

    public static Interface get2061On(){
        String json ="{" +
                "    \"id\": 2061," +
                "    \"typeSync\": \"SYNC\"," +
                "    \"protocolType\": \"HTTP\"," +
                "    \"protocol\": \"REST_API\"," +
                "    \"serverIp\": \"180.168.195.45\"," +
                "    \"serverPort\": 18080," +
                "    \"sendUrl\": \"mock/output/uapmdm_currency_2061\"," +
                "    \"keyProperty\": \"code\"," +
                "    \"needApprove\": false," +
                "    \"requestParamType\": \"JSON\"," +
                "    \"requestType\": \"POST\"," +
                "    \"responseType\": \"\"," +
                "    \"targetNameSpace\": \"\"," +
                "    \"localPart\": \"\"," +
                "    \"wsMethod\": \"\"," +
                "    \"typeActive\": \"INITIATIVE\"," +
                "    \"typeTransfer\": \"OUTPUT\"," +
                "    \"topic\": \"sinoif_org_list\"," +
                "    \"appId\": 1010," +
                "    \"appName\": \"财务系统\"," +
                "    \"params\": {" +
                "        \"data\": \"[{\\\"ts\\\":\\\"2019-09-04 17:00:37\\\",\\\"mdm_modifiedby\\\":\\\"dailyadmin\\\",\\\"mdm_createdon\\\":\\\"2019-09-04 16:41:49\\\",\\\"name\\\":\\\"澳大利亚元\\\",\\\"dr\\\":0,\\\"currencySign\\\":\\\"A$\\\",\\\"entity_data_status\\\":\\\"UNCHANGED\\\",\\\"mdm_duplicate\\\":0,\\\"mdm_modifiedon\\\":\\\"2019-09-04 17:00:36\\\",\\\"code\\\":\\\"AUD\\\",\\\"mdm_createdby_type\\\":\\\"GroupADM\\\",\\\"mdm_seal\\\":0,\\\"mdm_pk\\\":\\\"0001SS1000000000VQ83\\\",\\\"mdm_modifiedby_type\\\":\\\"GroupADM\\\",\\\"mdm_createdby\\\":\\\"dailyadmin\\\",\\\"mdm_version\\\":0,\\\"mdm_code\\\":\\\"C201909040001\\\"},{\\\"ts\\\":\\\"2014-04-17 15:01:54\\\",\\\"name\\\":\\\"人民币\\\",\\\"dr\\\":0,\\\"currencySign\\\":\\\"￥\\\",\\\"entity_data_status\\\":\\\"UNCHANGED\\\",\\\"mdm_duplicate\\\":0,\\\"code\\\":\\\"CNY\\\",\\\"mdm_seal\\\":0,\\\"mdm_pk\\\":\\\"00015210000000000C8V\\\",\\\"mdm_version\\\":0,\\\"mdm_code\\\":\\\"C0001\\\"},{\\\"ts\\\":\\\"2014-04-17 15:01:54\\\",\\\"name\\\":\\\"欧元\\\",\\\"dr\\\":0,\\\"currencySign\\\":\\\"\\\\u0026\\\",\\\"entity_data_status\\\":\\\"UNCHANGED\\\",\\\"mdm_duplicate\\\":0,\\\"code\\\":\\\"EUR\\\",\\\"mdm_seal\\\":0,\\\"mdm_pk\\\":\\\"00015210000000000C8W\\\",\\\"mdm_version\\\":0,\\\"mdm_code\\\":\\\"C0002\\\"},{\\\"ts\\\":\\\"2014-04-17 15:01:54\\\",\\\"name\\\":\\\"英镑\\\",\\\"dr\\\":0,\\\"currencySign\\\":\\\"￡\\\",\\\"entity_data_status\\\":\\\"UNCHANGED\\\",\\\"mdm_duplicate\\\":0,\\\"code\\\":\\\"GBP\\\",\\\"mdm_seal\\\":0,\\\"mdm_pk\\\":\\\"00015210000000000C8X\\\",\\\"mdm_version\\\":0,\\\"mdm_code\\\":\\\"C0003\\\"},{\\\"ts\\\":\\\"2014-04-17 15:01:54\\\",\\\"name\\\":\\\"港币\\\",\\\"dr\\\":0,\\\"currencySign\\\":\\\"HK$\\\",\\\"entity_data_status\\\":\\\"UNCHANGED\\\",\\\"mdm_duplicate\\\":0,\\\"code\\\":\\\"HKD\\\",\\\"mdm_seal\\\":0,\\\"mdm_pk\\\":\\\"00015210000000000C8Y\\\",\\\"mdm_version\\\":0,\\\"mdm_code\\\":\\\"C0004\\\"},{\\\"ts\\\":\\\"2014-04-17 15:01:54\\\",\\\"name\\\":\\\"日元\\\",\\\"dr\\\":0,\\\"currencySign\\\":\\\"￥\\\",\\\"entity_data_status\\\":\\\"UNCHANGED\\\",\\\"mdm_duplicate\\\":0,\\\"code\\\":\\\"JPY\\\",\\\"mdm_seal\\\":0,\\\"mdm_pk\\\":\\\"00015210000000000C8Z\\\",\\\"mdm_version\\\":0,\\\"mdm_code\\\":\\\"C0005\\\"},{\\\"ts\\\":\\\"2014-04-17 15:01:54\\\",\\\"name\\\":\\\"美元\\\",\\\"dr\\\":0,\\\"currencySign\\\":\\\"$\\\",\\\"entity_data_status\\\":\\\"UNCHANGED\\\",\\\"mdm_duplicate\\\":0,\\\"code\\\":\\\"USD\\\",\\\"mdm_seal\\\":0,\\\"mdm_pk\\\":\\\"00015210000000000C90\\\",\\\"mdm_version\\\":0,\\\"mdm_code\\\":\\\"C0006\\\"},{\\\"ts\\\":\\\"2014-04-17 15:01:54\\\",\\\"name\\\":\\\"澳门币\\\",\\\"dr\\\":0,\\\"currencySign\\\":\\\"MOP$\\\",\\\"entity_data_status\\\":\\\"UNCHANGED\\\",\\\"mdm_duplicate\\\":0,\\\"code\\\":\\\"MOP\\\",\\\"mdm_seal\\\":0,\\\"mdm_pk\\\":\\\"00015210000000000C91\\\",\\\"mdm_version\\\":0,\\\"mdm_code\\\":\\\"C0007\\\"},{\\\"ts\\\":\\\"2014-04-17 15:01:54\\\",\\\"name\\\":\\\"新台币\\\",\\\"dr\\\":0,\\\"currencySign\\\":\\\"NT$\\\",\\\"entity_data_status\\\":\\\"UNCHANGED\\\",\\\"mdm_duplicate\\\":0,\\\"code\\\":\\\"TWD\\\",\\\"mdm_seal\\\":0,\\\"mdm_pk\\\":\\\"00015210000000000C92\\\",\\\"mdm_version\\\":0,\\\"mdm_code\\\":\\\"C0008\\\"},{\\\"ts\\\":\\\"2019-09-04 17:05:43\\\",\\\"mdm_createdon\\\":\\\"2019-09-04 17:05:43\\\",\\\"name\\\":\\\"新加坡元\\\",\\\"dr\\\":0,\\\"currencySign\\\":\\\"S$\\\",\\\"entity_data_status\\\":\\\"UNCHANGED\\\",\\\"mdm_duplicate\\\":0,\\\"code\\\":\\\"SGD\\\",\\\"mdm_createdby_type\\\":\\\"GroupADM\\\",\\\"mdm_seal\\\":0,\\\"mdm_pk\\\":\\\"0001SS1000000000VQ8K\\\",\\\"mdm_createdby\\\":\\\"dailyadmin\\\",\\\"mdm_version\\\":0,\\\"mdm_code\\\":\\\"C201909040002\\\"}]\"" +
                "    }," +
                "    \"processorName\": \"com.sinoif.esbimpl.port.interfaces.processor.DefaultInterfaceResponseProcessor\"," +
                "    \"charset\": \"utf-8\"," +
                "    \"timeOut\": null" +
                "}";
        return JSONObject.parseObject(json,Interface.class);
    }

    public static Interface get2061Off(){
        String json ="{" +
                "    \"id\": 2061," +
                "    \"typeSync\": \"SYNC\"," +
                "    \"protocolType\": \"HTTP\"," +
                "    \"protocol\": \"REST_API\"," +
                "    \"serverIp\": \"192.168.66.162\"," +
                "    \"serverPort\": 8080," +
                "    \"sendUrl\": \"mock/output/uapmdm_currency_2061\"," +
                "    \"keyProperty\": \"code\"," +
                "    \"needApprove\": false," +
                "    \"requestParamType\": \"JSON\"," +
                "    \"requestType\": \"POST\"," +
                "    \"responseType\": \"\"," +
                "    \"targetNameSpace\": \"\"," +
                "    \"localPart\": \"\"," +
                "    \"wsMethod\": \"\"," +
                "    \"typeActive\": \"REACTIVE\"," +
//                "    \"typeActive\": \"INITIATIVE\"," +
                "    \"typeTransfer\": \"OUTPUT\"," +
                "    \"topic\": \"uapmdm_currency\"," +
                "    \"appId\": 1010," +
                "    \"appName\": \"财务系统\"," +
                "    \"params\": {" +
                "        \"data\": \"[{\\\"ts\\\":\\\"2019-09-04 17:00:37\\\",\\\"mdm_modifiedby\\\":\\\"dailyadmin\\\",\\\"mdm_createdon\\\":\\\"2019-09-04 16:41:49\\\",\\\"name\\\":\\\"澳大利亚元\\\",\\\"dr\\\":0,\\\"currencySign\\\":\\\"A$\\\",\\\"entity_data_status\\\":\\\"UNCHANGED\\\",\\\"mdm_duplicate\\\":0,\\\"mdm_modifiedon\\\":\\\"2019-09-04 17:00:36\\\",\\\"code\\\":\\\"AUD\\\",\\\"mdm_createdby_type\\\":\\\"GroupADM\\\",\\\"mdm_seal\\\":0,\\\"mdm_pk\\\":\\\"0001SS1000000000VQ83\\\",\\\"mdm_modifiedby_type\\\":\\\"GroupADM\\\",\\\"mdm_createdby\\\":\\\"dailyadmin\\\",\\\"mdm_version\\\":0,\\\"mdm_code\\\":\\\"C201909040001\\\"},{\\\"ts\\\":\\\"2014-04-17 15:01:54\\\",\\\"name\\\":\\\"人民币\\\",\\\"dr\\\":0,\\\"currencySign\\\":\\\"￥\\\",\\\"entity_data_status\\\":\\\"UNCHANGED\\\",\\\"mdm_duplicate\\\":0,\\\"code\\\":\\\"CNY\\\",\\\"mdm_seal\\\":0,\\\"mdm_pk\\\":\\\"00015210000000000C8V\\\",\\\"mdm_version\\\":0,\\\"mdm_code\\\":\\\"C0001\\\"},{\\\"ts\\\":\\\"2014-04-17 15:01:54\\\",\\\"name\\\":\\\"欧元\\\",\\\"dr\\\":0,\\\"currencySign\\\":\\\"\\\\u0026\\\",\\\"entity_data_status\\\":\\\"UNCHANGED\\\",\\\"mdm_duplicate\\\":0,\\\"code\\\":\\\"EUR\\\",\\\"mdm_seal\\\":0,\\\"mdm_pk\\\":\\\"00015210000000000C8W\\\",\\\"mdm_version\\\":0,\\\"mdm_code\\\":\\\"C0002\\\"},{\\\"ts\\\":\\\"2014-04-17 15:01:54\\\",\\\"name\\\":\\\"英镑\\\",\\\"dr\\\":0,\\\"currencySign\\\":\\\"￡\\\",\\\"entity_data_status\\\":\\\"UNCHANGED\\\",\\\"mdm_duplicate\\\":0,\\\"code\\\":\\\"GBP\\\",\\\"mdm_seal\\\":0,\\\"mdm_pk\\\":\\\"00015210000000000C8X\\\",\\\"mdm_version\\\":0,\\\"mdm_code\\\":\\\"C0003\\\"},{\\\"ts\\\":\\\"2014-04-17 15:01:54\\\",\\\"name\\\":\\\"港币\\\",\\\"dr\\\":0,\\\"currencySign\\\":\\\"HK$\\\",\\\"entity_data_status\\\":\\\"UNCHANGED\\\",\\\"mdm_duplicate\\\":0,\\\"code\\\":\\\"HKD\\\",\\\"mdm_seal\\\":0,\\\"mdm_pk\\\":\\\"00015210000000000C8Y\\\",\\\"mdm_version\\\":0,\\\"mdm_code\\\":\\\"C0004\\\"},{\\\"ts\\\":\\\"2014-04-17 15:01:54\\\",\\\"name\\\":\\\"日元\\\",\\\"dr\\\":0,\\\"currencySign\\\":\\\"￥\\\",\\\"entity_data_status\\\":\\\"UNCHANGED\\\",\\\"mdm_duplicate\\\":0,\\\"code\\\":\\\"JPY\\\",\\\"mdm_seal\\\":0,\\\"mdm_pk\\\":\\\"00015210000000000C8Z\\\",\\\"mdm_version\\\":0,\\\"mdm_code\\\":\\\"C0005\\\"},{\\\"ts\\\":\\\"2014-04-17 15:01:54\\\",\\\"name\\\":\\\"美元\\\",\\\"dr\\\":0,\\\"currencySign\\\":\\\"$\\\",\\\"entity_data_status\\\":\\\"UNCHANGED\\\",\\\"mdm_duplicate\\\":0,\\\"code\\\":\\\"USD\\\",\\\"mdm_seal\\\":0,\\\"mdm_pk\\\":\\\"00015210000000000C90\\\",\\\"mdm_version\\\":0,\\\"mdm_code\\\":\\\"C0006\\\"},{\\\"ts\\\":\\\"2014-04-17 15:01:54\\\",\\\"name\\\":\\\"澳门币\\\",\\\"dr\\\":0,\\\"currencySign\\\":\\\"MOP$\\\",\\\"entity_data_status\\\":\\\"UNCHANGED\\\",\\\"mdm_duplicate\\\":0,\\\"code\\\":\\\"MOP\\\",\\\"mdm_seal\\\":0,\\\"mdm_pk\\\":\\\"00015210000000000C91\\\",\\\"mdm_version\\\":0,\\\"mdm_code\\\":\\\"C0007\\\"},{\\\"ts\\\":\\\"2014-04-17 15:01:54\\\",\\\"name\\\":\\\"新台币\\\",\\\"dr\\\":0,\\\"currencySign\\\":\\\"NT$\\\",\\\"entity_data_status\\\":\\\"UNCHANGED\\\",\\\"mdm_duplicate\\\":0,\\\"code\\\":\\\"TWD\\\",\\\"mdm_seal\\\":0,\\\"mdm_pk\\\":\\\"00015210000000000C92\\\",\\\"mdm_version\\\":0,\\\"mdm_code\\\":\\\"C0008\\\"},{\\\"ts\\\":\\\"2019-09-04 17:05:43\\\",\\\"mdm_createdon\\\":\\\"2019-09-04 17:05:43\\\",\\\"name\\\":\\\"新加坡元\\\",\\\"dr\\\":0,\\\"currencySign\\\":\\\"S$\\\",\\\"entity_data_status\\\":\\\"UNCHANGED\\\",\\\"mdm_duplicate\\\":0,\\\"code\\\":\\\"SGD\\\",\\\"mdm_createdby_type\\\":\\\"GroupADM\\\",\\\"mdm_seal\\\":0,\\\"mdm_pk\\\":\\\"0001SS1000000000VQ8K\\\",\\\"mdm_createdby\\\":\\\"dailyadmin\\\",\\\"mdm_version\\\":0,\\\"mdm_code\\\":\\\"C201909040002\\\"}]\"" +
                "    }," +
                "    \"processorName\": \"com.sinoif.esbimpl.port.interfaces.processor.DefaultInterfaceResponseProcessor\"," +
                "    \"charset\": \"utf-8\"," +
                "    \"timeOut\": null" +
                "}";
        return JSONObject.parseObject(json,Interface.class);
    }
}
