package com.sinoif.esbimpl.port.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sinoif.esb.port.bean.Interface;
import com.sinoif.esbimpl.port.interfaces.EsbPortalService;
import com.sinoif.esbimpl.port.interfaces.InterfaceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * 测试类，方便地使用json数组直接向Esb系统中注册接口
 */
//@RequestMapping("/")
//@Controller
public class TestController {

    @Autowired
    InterfaceContext interfaceContext;

    @GetMapping("/register")
    @ResponseBody
    public String installInterfaces() {
        try {
            Path p = Paths.get("/home/shukuan/Documents/beijin/port/esb-portal/src/main/resources/interfaces.json");
            List<String> interfaces = Files.readAllLines(p, Charset.forName("utf-8"));
            StringBuilder sb = new StringBuilder();
            for (String s : interfaces) {
                sb.append(s);
            }
            JSONArray array = JSONArray.parseArray(sb.toString());
            array.forEach(o -> interfaceContext.registerInterfaces(JSONObject.parseObject(o.toString(), Interface.class)));
        } catch (Exception e) {
            e.printStackTrace();
            return "failed";
        }
        return "success";
    }

    @Autowired
    EsbPortalService esbPortalService;

}
