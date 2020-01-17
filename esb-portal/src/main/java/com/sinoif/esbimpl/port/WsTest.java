package com.sinoif.esbimpl.port;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.call.client.bean.Context;
import com.call.client.bean.Protocol;
import com.call.client.bean.WebServiceProtocol;
import com.call.client.client.CallResult;
import com.call.client.client.ClientProxy;
import com.sinoif.esbimpl.port.interfaces.WsPortExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 执行webservice测试类
 */
public class WsTest {
    private final static Logger logger = LoggerFactory.getLogger(WsTest.class);
    public static void main(String[] args) {

        String test = "abc|cde";
        System.out.println(test.replace("|",","));

//        Map<String, String> params = new LinkedHashMap<>();
//        params.put("string", "MDM");
//        params.put("string1", "org_list");
//        params.put("string2", "1=1");
//        params.put("boolean", "true");
//        params.put("string4", "json");
//        new WsPortExecutor().invokeWsPort("http://180.168.195.45:18080/uapws/service/com.yonyou.itf.mdm07.sharing.IMdSharingCenterService?wsdl",
//                "http://sharing.mdm07.itf.yonyou.com/IMdSharingCenterService", "IMdSharingCenterServiceSOAP11Binding",
//                "urn:queryMdByCondition", params);
//
//
//        Map<String, String> params = new LinkedHashMap<>();
//        params.put("string", "MDM");
//        params.put("string1", "org_list");
//        params.put("string2", "[{\"id\":\"2\",\"currency\":\"C0001\",\"name\":\"江苏璇众贸易有限公司\",\"money\":\"35000万\",\"dr\":0,\"groupCoreCompany\":\"0000000002\",\"code\":\"2\",\"principal\":\"杨柳青\",\"countryZone\":\"CT3116\",\"majorBusiness\":\"贸易\"}]");
//        new WsTest().invokeWsPort("http://180.168.195.45:18080/uapws/service/com.yonyou.itf.mdm07.sharing.IMdSharingCenterService?wsdl",
//                "http://sharing.mdm07.itf.yonyou.com/IMdSharingCenterService", "IMdSharingCenterServiceSOAP11Binding",
//                "urn:insertMd", params);
        String json = "{\n" +
                "  \"datas\": [\n" +
                "    {\n" +
                "      \"cnt\": 133,\n" +
                "      \"handled\": \"false\",\n" +
                "      \"input_system\": \"主数据平台\",\n" +
                "      \"interface_address\": \"HTTP://180.168.195.45:18080/uapws/service/com.yonyou.itf.mdm07.sharing.IMdSharingCenterService?wsdl\",\n" +
                "      \"interface_id\": \"2074\",\n" +
                "      \"interface_name\": \"2069测试\",\n" +
                "      \"output_system\": \"主数据平台\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cnt\": 133,\n" +
                "      \"handled\": \"false\",\n" +
                "      \"input_system\": \"主数据平台\",\n" +
                "      \"interface_address\": \"HTTP://192.168.2.125:7777/mock/output/成功输出222222222222222\",\n" +
                "      \"interface_id\": \"2067\",\n" +
                "      \"interface_name\": \"mock/output/成功输出22222222222222222222\",\n" +
                "      \"output_system\": \"贺伟开发环境\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"cnt\": 133,\n" +
                "      \"handled\": \"false\",\n" +
                "      \"input_system\": \"主数据平台\",\n" +
                "      \"interface_address\": \"HTTP://192.168.2.125:7777/mock/output/成功输出111111111111\",\n" +
                "      \"interface_id\": \"2065\",\n" +
                "      \"interface_name\": \"mock/output/成功输出1111111111111\",\n" +
                "      \"output_system\": \"财务系统\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"page\": 3,\n" +
                "  \"totalCount\": 1\n" +
                "}\n";
        JSONObject jsonObject = JSONObject.parseObject(json);
        System.out.println(jsonObject.getString("xxxxx"));

    }

    public String invokeWsPort(String wsdlUrl, String nameSpace, String localPart, String soapAction, Map<String, String> params) {
        Context<Protocol> context = new Context<Protocol>(){{
            setParams(params);
        }};
        WebServiceProtocol protocol = new WebServiceProtocol(wsdlUrl, localPart);
        context.setProtocol(protocol);

        CallResult call = ClientProxy.call(context);

        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder xmlBuilder = factory.newDocumentBuilder();
            Document document = xmlBuilder.parse(call.getMessage());
            Element rootElement = document.getDocumentElement();
            return getString("ns0:data", rootElement);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private String getString(String tagName, Element element) {
        NodeList list = element.getElementsByTagName(tagName);
        if (list != null && list.getLength() > 0) {
            NodeList subList = list.item(0).getChildNodes();
            if (subList != null && subList.getLength() > 0) {
                return subList.item(0).getNodeValue();
            }
        }
        return null;
    }
}
