package com.zbcn.demo.http.httpdemo.post;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * @Description: post 请求测试
 * @Auther: zbcn
 * @Date: 2/23/19 16:40
 */
public class PostDemo {

    public static void main(String[] args) {

        new PostReader().start();
    }

    static class PostReader extends Thread {
        @Override
        public void run() {
            try {
                URL url = new URL("https://www.baidu.com/s");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.addRequestProperty("encoding","utf-8");
                connection.setDoInput(true);
                connection.setDoOutput(true);
                connection.setRequestMethod("POST");

                //发送请求
                OutputStream outputStream = connection.getOutputStream();
                OutputStreamWriter ups = new OutputStreamWriter(outputStream);
                BufferedWriter bufferWriter = new BufferedWriter(ups);
                bufferWriter.write("q=ie%3Dutf-8%26f%3D8%26rsv_bp%3D1%26rsv_idx%3D1%26tn%3Dbaidu%26wd%3D张三%26oq%3Dqqwe%26rsv_pq%3Dee3471b70007c1f5%26rsv_t%3D&oq=ie%3Dutf-8%26f%3D8%26rsv_bp%3D1%26rsv_idx%3D1%26tn%3Dbaidu%26wd%3D张三%26oq%3Dqqwe%26rsv_pq%3Dee3471b70007c1f5%26rsv_t%3D&aqs=chrome..69i57j69i60&sourceid=chrome&ie=UTF-8");
                bufferWriter.flush();

                InputStream inputStream = connection.getInputStream();
                InputStreamReader ips = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(ips);
                String line = null;
                StringBuilder stringBuilder = new StringBuilder();
                while((line = bufferedReader.readLine()) != null){
                    stringBuilder.append(line);
                }
                bufferWriter.close();
                ups.close();
                outputStream.close();
                bufferedReader.close();
                ips.close();
                inputStream.close();

                System.out.println(stringBuilder.toString());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
