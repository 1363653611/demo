package com.zbcn.demo.http.httpdemo;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description:
 * @Auther: zbcn
 * @Date: 2/23/19 17:24
 */
public class TestHttpClient {

    public static void main(String[] args) {
        //new Get().start();
        new Post().start();
    }
}

class Get extends Thread {

    CloseableHttpClient client = HttpClients.createDefault();
    @Override
    public void run() {
        HttpGet httpGet = new HttpGet("https://www.baidu.com/");
        try {
            CloseableHttpResponse execute = client.execute(httpGet);
            HttpEntity entity = execute.getEntity();
            String s = EntityUtils.toString(entity, "utf-8");
            System.out.println(s);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class Post extends Thread {
    CloseableHttpClient client = HttpClients.createDefault();

    @Override
    public void run() {
        HttpPost httpPost = new HttpPost("https://www.baidu.com/");
        List<BasicNameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("test","124"));
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(params));
            CloseableHttpResponse execute = client.execute(httpPost);
            HttpEntity entity = execute.getEntity();
            String s = EntityUtils.toString(entity, "utf-8");
            System.out.println(s);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
