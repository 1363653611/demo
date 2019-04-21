package com.zbcn.demo.socket.tcp;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @Description: 服务端
 * @Auther: zbcn8
 * @Date: 2019/4/18 15:14
 */
public class SocketServer {

    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(20006);
        Socket accept = null;
        boolean flag = true;
        while (flag){
            accept = server.accept();
            System.out.println("与客户端连接成功！");
            //为每个客户端连接开启一个线程
            new Thread(new ServerThread(accept)).start();
        }
        server.close();
    }
}
