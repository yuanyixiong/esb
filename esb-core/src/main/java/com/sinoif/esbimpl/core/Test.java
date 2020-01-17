package com.sinoif.esbimpl.core;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Test {
    //    private final static Logger logger = LoggerFactory.getLogger(Test.class);
    public static void main(String[] args) {
//        try {
//            ExecutorService executorService = Executors.newFixedThreadPool(10);
//            List<Future<Boolean>> futures = executorService.invokeAll(Collections.singletonList(() -> {
//                        System.out.println("begin");
//                        Thread.sleep(10 * 1000);
//                        System.out.println("end");
//                        return true;
//                    }),
//                    8, TimeUnit.SECONDS); // 强制保证一个输出消息处理少于50秒
//            if (futures.get(0).get()) {
//                System.out.println("exectued;");
//            }
//            System.out.println(">>>>>>.");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        int count= 1;
        ExecutorService executorService = Executors.newFixedThreadPool(200);
       for(int i=0;i<2000;i++){
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
             final int finali = i;
            executorService.execute(() -> System.out.println(">>>>>>>:"+(test2072(finali)!=null)));
        }

    }

    private static String test2072(int testId){
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost request = new HttpPost("http://localhost:8080/esb-portal/portal/call/2072");
//        HttpGet request = new HttpGet("http://www.douyu.com/");
        try {
            ResponseHandler<String> responseHandler = response -> {
                int status = response.getStatusLine().getStatusCode();
                if (status >= 200 && status < 300) {
                    HttpEntity entity = response.getEntity();
                    return entity != null ? EntityUtils.toString(entity) : null;
                } else {
                    throw new ClientProtocolException("Unexpected response status: " + status);
                }
            };
            String requestData = "{\n" +
                    "  \"data\":{\n" +
                    "    \"id\":\"123456("+testId+")\",\n" +
                    "    \"age\":\"28\",\n" +
                    "    \"name\":\"zhangsan\",\n" +
                    "    \"sex\":\"maile\"\n" +
                    "  },\n" +
                    "  \"service\":\"reactive_input\",\n" +
                    "  \"appCode\":\"hetong\"\n" +
                    "}";
            StringEntity entity = new StringEntity(requestData, "utf-8");
            request.setEntity(entity);
            String responseBody = httpclient.execute(request, responseHandler);
            httpclient.close();
            return responseBody;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                httpclient.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return "failed";
    }

    private static void test2072Print(){

    }

}
