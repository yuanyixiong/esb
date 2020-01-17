package com.sinoif.esbimpl.port.interfaces.processor;

import com.call.client.client.CallResult;
import com.sinoif.esb.enums.ProtocolEnum;
import com.sinoif.esb.enums.TypeActiveEnum;
import com.sinoif.esb.port.bean.Interface;
import com.sinoif.esb.port.bean.InvokeResult;
import com.sinoif.esb.port.service.InterfaceResponseProcessor;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;

/**
 * 主数据平台接口返回值处理器
 */
public class DefaultInterfaceResponseProcessor implements InterfaceResponseProcessor {

    Logger logger = LogManager.getLogger(DefaultInterfaceResponseProcessor.class);

    /**
     * 处理接口调用的返回结果
     *
     * @param callResponse 接口返回结果
     * @param esbInterface 调用的接口
     * @return 处理结果
     */
    @Override
    public InvokeResult process(CallResult callResponse, Interface esbInterface) {
        if (esbInterface.getProtocol() == ProtocolEnum.REST_API) {
            return handleRestResponse(callResponse, esbInterface);
        } else if (esbInterface.getProtocol() == ProtocolEnum.WEBSERVICE) {
            return handleWsResponse(callResponse, esbInterface);
        } else {
            return InvokeResult.fail(esbInterface, String.format("消息处理错误：不支持的接口类型：?", esbInterface.getProtocolType()));
        }
    }

    /**
     * 处理json类型的返回结果
     *
     * @param callResult  接口返结果
     * @param esbInterface 调用的接口
     * @return 处理后的接口调用结果
     */
    private InvokeResult handleWsResponse(CallResult callResult, Interface esbInterface) {
        if (!callResult.isSuccess()) {
            return InvokeResult.fail(esbInterface, callResult.getMessage());
        } else {
            try {
                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                DocumentBuilder xmlBuilder = factory.newDocumentBuilder();
                Document document = xmlBuilder.parse(new InputSource(new StringReader(callResult.getMessage())));
                Element rootElement = document.getDocumentElement();

                String status = getString("ns0:success", rootElement);
                if (Boolean.parseBoolean(status)) {
                    return InvokeResult.success(null, "success", getString("ns0:data", rootElement));
                } else {
                    String message = getString("ns0:message", rootElement);
                    assert message != null;
                    if (message.contains("uapmdm_relationmap") && message.contains("缺失表达式")) {
                        return InvokeResult.success(null, "success", "[]");
                    } else {
                        return InvokeResult.fail(null, message);
                    }
                }
            } catch (Exception e) {
                logger.error("webservice请求异常", e);
                return InvokeResult.fail(esbInterface, "处理返回结果时发生异常：" + e.getMessage() + callResult.getMessage());
            }
        }
    }

    /**
     * 处理xml类型的返回结果
     *
     * @param callResult  接口返结果
     * @param esbInterface 调用的接口
     * @return 处理后的接口调用结果
     */
    private InvokeResult handleRestResponse(CallResult response, Interface esbInterface) {
        if (esbInterface.getTypeActive() == TypeActiveEnum.SYNC_CALL) {
            if (response.isSuccess()) {
                return InvokeResult.success(esbInterface, response.getMessage());
            } else {
                return InvokeResult.fail(esbInterface, response.getMessage());
            }
        } else if (response.isSuccess() && "success".equalsIgnoreCase(response.getMessage())) {
            return InvokeResult.success(esbInterface, response.getMessage());
        } else {
            return InvokeResult.fail(esbInterface, response.getMessage());
        }
    }

    /**
     * 从xml中读取某一个元素的文本值
     *
     * @param tagName 要读取的元素的tagName
     * @param element 根元素
     * @return xml元素的文本值
     */
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
