package com.ideal.spiders;

import cn.edu.hfut.dmic.contentextractor.ContentExtractor;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

public class newsSpider {
    public static void main(String[] args) {
        newsSpider ns = new newsSpider();
        String url = "http://m.sohu.com/a/198713006_608373";
        String text = ns.getNewtext(url);//获取新闻内容
        System.out.println("新闻内容:" + text);
        String baiduUrl ="https://aip.baidubce.com/rpc/2.0/nlp/v1/sentiment_classify?access_token=24.0a5cfd84db329d3ff26af08885eac866.2592000.1526720122.282335-11126907";
        try {
            ns.sendPost(baiduUrl,"qq");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public String getNewtext(String url) {
        String content = null;
        try {
            content = ContentExtractor.getContentByUrl(url);
        } catch (Exception e) {
            return null;
        }
        return content;
    }


    public static String jsonPost(String strURL, Map<String, String> params) {
        try {
            URL url = new URL(strURL);// 创建连接
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setUseCaches(false);
            connection.setInstanceFollowRedirects(true);
            connection.setRequestMethod("POST"); // 设置请求方式
            connection.setRequestProperty("Accept", "application/json"); // 设置接收数据的格式
            connection.setRequestProperty("Content-Type", "application/json"); // 设置发送数据的格式
            connection.connect();
            OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream(), "UTF-8"); // utf-8编码
            out.append(JSONUtil.object2JsonString(params));
            out.flush();
            out.close();

            int code = connection.getResponseCode();
            InputStream is = null;
            if (code == 200) {
                is = connection.getInputStream();
            } else {
                is = connection.getErrorStream();
            }

            // 读取响应
            int length = (int) connection.getContentLength();// 获取长度
            if (length != -1) {
                byte[] data = new byte[length];
                byte[] temp = new byte[512];
                int readLen = 0;
                int destPos = 0;
                while ((readLen = is.read(temp)) > 0) {
                    System.arraycopy(temp, 0, data, destPos, readLen);
                    destPos += readLen;
                }
                String result = new String(data, "UTF-8"); // utf-8编码
                return result;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return "error"; // 自定义错误信息
    }

}
