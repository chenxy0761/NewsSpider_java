package com.ideal.spiders;

import cn.edu.hfut.dmic.contentextractor.ContentExtractor;
import org.apache.hc.core5.util.TextUtils;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.GZIPInputStream;

public class contentToken {
    public static String getNewtext(String url) {
        String content = null;
        try {
            content = ContentExtractor.getContentByUrl(url);
        } catch (Exception e) {
            return null;
        }
        return content;
    }

    public static String jsonPost(String params) {
        String strURL = "https://aip.baidubce.com/rpc/2.0/nlp/v1/sentiment_classify?charset=UTF-8&access_token=24.0a5cfd84db329d3ff26af08885eac866.2592000.1526720122.282335-11126907";
        HttpURLConnection connection = null;
        try {
            URL url = new URL(strURL);// 创建连接
            connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setUseCaches(false);
            connection.setInstanceFollowRedirects(true);
            connection.setRequestMethod("POST"); // 设置请求方式
            connection.setRequestProperty("Accept", "application/json"); // 设置接收数据的格式
            connection.setRequestProperty("Content-Type", "application/json"); // 设置发送数据的格式
            connection.connect();
            OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream(), "utf-8"); // utf-8编码
            ObjectMapper mapper = new ObjectMapper();
            String json = "";
            Map<String, String> map = new HashMap<String, String>();
            map.put("text", params);
            json = mapper.writeValueAsString(map);
//            System.out.println(json);
            out.append(json);
            out.flush();
            out.close();

            int code = connection.getResponseCode();
            String is = null;
            String content = null;
            if (200 == connection.getResponseCode()) {
                InputStream inputStream = null;
                if (!TextUtils.isEmpty(connection.getContentEncoding())) {
                    String encode = connection.getContentEncoding().toLowerCase();
                    if (!TextUtils.isEmpty(encode) && encode.indexOf("gzip") >= 0) {
                        inputStream = new GZIPInputStream(connection.getInputStream());
                    }
                }

                if (null == inputStream) {
                    inputStream = connection.getInputStream();
                }

                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "gbk"));
                StringBuilder builder = new StringBuilder();
                String line = null;
                while ((line = reader.readLine()) != null) {
                    builder.append(line).append("\n");
                }
                content = builder.toString();
            }
            return content.substring(content.indexOf("\"items\"") + 10, content.length() - 3);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
        return "error"; // 自定义错误信息
    }
}
