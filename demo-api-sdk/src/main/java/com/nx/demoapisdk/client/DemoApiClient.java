package com.nx.demoapisdk.client;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.nx.demoapisdk.model.User;
import com.nx.demoapisdk.utils.SignUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 调用第三方接口的客户端
 *
 * @author nx-xn2002
 */
public class DemoApiClient {
    /**
     * access key
     */
    private final String accessKey;
    /**
     * secret key
     */
    private final String secretKey;

    /**
     * demo api client
     *
     * @param accessKey access key
     * @param secretKey secret key
     */
    public DemoApiClient(String accessKey, String secretKey) {
        this.accessKey = accessKey;
        this.secretKey = secretKey;
    }

    private final static String URL = "http://localhost:8090/api/name/";

    /**
     * get name by get
     *
     * @param name name
     * @return {@link String }
     */
    public String getNameByGet(String name) {
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("name", name);
        String result = HttpUtil.get(URL, paramMap);
        System.out.println(result);
        return result;
    }

    /**
     * get name by post
     *
     * @param name name
     * @return {@link String }
     */
    public String getNameByPost(String name) {
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("name", name);
        String result = HttpUtil.post(URL, paramMap);
        System.out.println(result);
        return result;
    }

    /**
     * get user name by post
     *
     * @param user user
     * @return {@link String }
     */
    public String getUserNameByPost(User user) {
        String json = JSONUtil.toJsonStr(user);
        System.out.println(json);
        HttpResponse httpResponse = HttpRequest.post(URL + "user")
                .addHeaders(getHeaderMap(json))
                .body(json)
                .execute();
        System.out.println(httpResponse.getStatus());
        String result = httpResponse.body();
        System.out.println(result);
        return result;
    }

    /**
     * get header map
     *
     * @param body body
     * @return {@link Map }<{@link String }, {@link String }>
     */
    private Map<String, String> getHeaderMap(String body) {
        HashMap<String, String> hashMap = new HashMap<>(16);
        hashMap.put("accessKey", accessKey);
        //一定不能发送给后端
        //hashMap.put("secretKey", secretKey);
        hashMap.put("nonce", RandomUtil.randomNumbers(4));
        hashMap.put("body", body);
        hashMap.put("timestamp", String.valueOf(System.currentTimeMillis()));
        hashMap.put("sign", SignUtils.genSign(body, secretKey));
        return hashMap;
    }
}
