package com.zbcn.demo.socket.tcp;

import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

/**
 * @Description: 服务线程
 * @Auther: zbcn8
 * @Date: 2019/4/18 15:01
 */
public class ServerThread implements Runnable{

    private Socket client;

    public ServerThread(Socket client) {
        this.client = client;
    }

    @Override
    public void run() {
        //获取Socket的输出流，用来向客户端发送数据
        try {
            PrintStream printStream = new PrintStream(client.getOutputStream());
            //获取Socket的输入流，用来接收从客户端发送过来的数据
            BufferedReader reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
            boolean flag = true;
            while(flag){
                String s = reader.readLine();
                if(StringUtils.isEmpty(s) || StringUtils.equals("bye",s)){
                    flag = false;
                }else {
                    //将接收到的字符串前面加上echo，发送到对应的客户端
                    printStream.println("echo:" + s);
                }
            }
            printStream.close();
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
