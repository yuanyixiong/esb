package com.sinoif.esbimpl.port;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 测试类，第三方系统可参考此示例调用esb被动接口
 */
public class PassiveInterfaceTest {
    public static void main(String[] args) {
        testFormData();
        testJson();
    }

    public static void testJson() {
        try {
            CloseableHttpClient client = HttpClients.createDefault();
            HttpPost httpPost = new HttpPost("http://localhost:9999/esb/portal/inserMd");
            String json = "{\"id\":1,\"name\":\"John\"}";
            StringEntity entity = new StringEntity(json);
            httpPost.setEntity(entity);
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");
            executeRequest(httpPost);
            client.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void testFormData() {
        try {
            CloseableHttpClient client = HttpClients.createDefault();
            HttpPost httpPost = new HttpPost("http://localhost:9999/esb/portal/queryMd");
            HashMap<String, String> params = new HashMap<>();
            params.put("data", "8");
            httpPost.setHeader("content-type", "application/x-www-form-urlencoded");
            List<NameValuePair> pairs = new ArrayList<>();
            params.forEach((key, value) -> {
                pairs.add(new BasicNameValuePair(key, value));
            });
            httpPost.setEntity(new UrlEncodedFormEntity(pairs));
            executeRequest(httpPost);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void executeRequest(HttpRequestBase request) {
        try {
            CloseableHttpClient client = HttpClients.createDefault();
            ResponseHandler<String> responseHandler = response -> {
                int status = response.getStatusLine().getStatusCode();
                if (status >= 200 && status < 300) {
                    HttpEntity entity = response.getEntity();
                    return entity != null ? EntityUtils.toString(entity) : null;
                } else {
                    throw new ClientProtocolException("Unexpected response status: " + status);
                }
            };
            String responseBody = client.execute(request, responseHandler);
            System.out.println("response:" + responseBody);
            client.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
