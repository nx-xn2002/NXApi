package com.nx.demoapi.controller;

import com.nx.demoapisdk.model.User;
import com.nx.demoapisdk.utils.SignUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

/**
 * 查询名称接口
 *
 * @author nx-xn2002
 */
@RestController
@RequestMapping("/name")
public class NameController {
    @GetMapping("/get")
    public String getNameByGet(String name, HttpServletRequest request) {
//        String accessKey = request.getHeader("accessKey");
//        String secretKey = request.getHeader("secretKey");
//        if (!accessKey.equals("123456") || !secretKey.equals("123456")) {
//            throw new RuntimeException("无权限");
//        }
        return "GET 你的名字是" + name;
    }

    @PostMapping("/post")
    public String getNameByPost(@RequestParam String name, HttpServletRequest request) {
        String accessKey = request.getHeader("accessKey");
        String secretKey = request.getHeader("secretKey");
        if (!accessKey.equals("123456") || !secretKey.equals("123456")) {
            throw new RuntimeException("无权限");
        }
        return "POST 你的名字是" + name;
    }

    @PostMapping("/user")
    public String getUserNameByPost(@RequestBody User user, HttpServletRequest request) {
        String accessKey = request.getHeader("accessKey");
        String nonce = request.getHeader("nonce");
        String timestamp = request.getHeader("timestamp");
        String sign = request.getHeader("sign");
        String body = request.getHeader("body");
        //TODO: 查数据库这里是否已经分配给用户
        if (!accessKey.equals("123456")) {
            throw new RuntimeException("无权限");
        }
        if (Long.parseLong(nonce) > 10000) {
            throw new RuntimeException("无权限");
        }
        //时间不超过5分钟
        if (Long.parseLong(timestamp) + 5 * 60 * 1000 < System.currentTimeMillis()) {
            throw new RuntimeException("请求超时");
        }
        //TODO: 这里要查数据库获取secretKey
        String gennedSign = SignUtils.genSign(body, "123456");
        if (!sign.equals(gennedSign)) {
            throw new RuntimeException("无权限");
        }
        return "POST 用户的名字是" + user.getName();
    }

}
