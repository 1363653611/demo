package com.zbcn.demo.http.httpdemo.get;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * @Description: get 请求测试
 * @Auther: zbcn
 * @Date: 2/23/19 17:09
 */
public class GetDemo {
    public static void main(String[] args) {
        new GetReader().start();
    }
}

class GetReader extends Thread {
    @Override
    public void run() {
        try {
            URL url = new URL("https://www.baidu.com/s?ie=utf-8&f=8&rsv_bp=1&rsv_idx=1&tn=baidu&wd=%E5%BC%A0%E4%B8%89&oq=qqwe&rsv_pq=ee3471b70007c1f5&rsv_t=2b2berzv1lWMv2ORt6uamE5SJQ9EN3nkRrD81WqhB5Pr9gmWITfkPRCVRXE&rqlang=cn&rsv_enter=1&inputT=8773&rsv_sug3=24&rsv_sug1=20&rsv_sug7=101&rsv_sug2=0&rsv_sug4=8773");
            URLConnection urlConnection = url.openConnection();
            InputStream inputStream = urlConnection.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line;
            StringBuilder stringBuilder = new StringBuilder();
            while ((line = bufferedReader.readLine()) != null){
                stringBuilder.append(line);
            }
            bufferedReader.close();
            inputStreamReader.close();
            inputStream.close();
            System.out.println(stringBuilder.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
