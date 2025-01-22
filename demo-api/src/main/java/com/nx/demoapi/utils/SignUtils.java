package com.nx.demoapi.utils;

import cn.hutool.crypto.digest.DigestAlgorithm;
import cn.hutool.crypto.digest.Digester;
import cn.hutool.json.JSONUtil;

import java.util.Map;

/**
 * 签名工具类
 *
 * @author nx-xn2002
 * @date 2025-01-22
 */
public class SignUtils {
    /**
     * gen sign
     *
     * @param body      body
     * @param secretKey secret key
     * @return {@link String }
     */
    public static String genSign(String body, String secretKey) {
        Digester digester = new Digester(DigestAlgorithm.SHA256);
        String content = JSONUtil.toJsonStr(body) + "-" + secretKey;
        return digester.digestHex(content);
    }
}
