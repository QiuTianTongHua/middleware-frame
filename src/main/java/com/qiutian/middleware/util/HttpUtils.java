package com.qiutian.middleware.util;

import com.alibaba.fastjson.JSON;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 说明
 *
 * @author qiutian
 * @since 2020/11/26
 */
public class HttpUtils {
    public static void sendPost(String url, String character) {
        Map<String, String> dataMap = new HashMap<>(8);
        dataMap.put("name", "test");
        dataMap.put("age", "20");
        HttpPost httpPost = new HttpPost(url);
        httpPost.setEntity(new StringEntity(JSON.toJSONString(dataMap), character));
        httpPost.addHeader("Content-Type", "application/json");
        CloseableHttpClient httpClient = HttpClients.createDefault();
        try {
            HttpResponse response = httpClient.execute(httpPost);
            if (Objects.nonNull(response) && response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                // 请求成功
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
