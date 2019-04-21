package com.zbcn.demo.socket.tcp;

import org.apache.commons.lang.StringUtils;

import java.io.*;
import java.net.Socket;
import java.net.SocketTimeoutException;

/**
 * socket 客户端
 *
 * 创建一个 Socket 实例：构造函数向指定的远程主机和端口建立一个 TCP 连接；
 * 通过套接字的 I/O 流与服务端通信；
 * 使用 Socket 类的 close 方法关闭连接
 *
 */
public class Client {

    public static void main(String[] args) throws IOException {
        //客户端请求与本机的 20006端口建立连接
        Socket socket = new Socket("127.0.0.1", 20006);
        socket.setSoTimeout(2000);
        //获取键盘的输入
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        //获取Socket的输出流，用来发送数据到服务端
        PrintStream out = new PrintStream(socket.getOutputStream());
        
        //获取socket的输入流来接受服务端发送的数据
        BufferedReader serverReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        boolean flag = true;
        while(flag){
            System.out.println("请输入信息");
            String s = reader.readLine();
            //发送数据到服务端
            out.println(s);
            if(StringUtils.equals("bye",s)){
                flag = false;
            }else{
                try {
                    //从服务器端接收数据有个时间限制（系统自设，也可以自己设置），超过了这个时间，便会抛出该异常
                    String s1 = serverReader.readLine();
                    System.out.println(s1);
                } catch (SocketTimeoutException e) {
                    System.out.println("Time out, No response");
                    e.printStackTrace();
                }

            }
        }
        reader.close();
        serverReader.close();
        if(socket !=  null){
            //如果构造函数建立起了连接，则关闭套接字，如果没有建立起连接，自然不用关闭
            socket.close();
        }
    }
}
