package com.zbcn.demo.http;

import org.apache.http.Header;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Http 请求测试
 *
 * @author Administrator
 * @date 2018/11/13 13:46
 */
public class HttpclientTest {

    public static void main(String[] args) {
        post();
    }

    private static void post(){
        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost("https://www.baidu.com");
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("scope","project"));
        params.add(new BasicNameValuePair("q","java"));
        try {
            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(params);
            httpPost.setEntity(entity);
            //head
            Header head = new BasicHeader("User-Agent","Mozilla/5.0 (windows NT 6.3; Win64;64)");
            httpPost.setHeader(head);

            CloseableHttpResponse response = client.execute(httpPost);
            StatusLine statusLine = response.getStatusLine();
            if(statusLine.getStatusCode() == 200){
                String s = EntityUtils.toString(response.getEntity(), "UTF-8");
                System.out.println(s);
            }
            if(response != null){
                response.close();
            }

            client.close();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
