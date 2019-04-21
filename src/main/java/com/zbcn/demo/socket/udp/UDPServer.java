package com.zbcn.demo.socket.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

/**
 * @Description:
 * @Auther: zbcn8
 * @Date: 2019/4/18 15:57
 */
public class UDPServer {

    /**
     * 创建一个 DatagramSocket 实例，指定本地端口号，并可以有选择地指定本地地址，此时，服务器已经准备好从任何客户端接收数据报文；
     使用 DatagramSocket 实例的 receive()方法接收一个 DatagramPacket 实例，当 receive()方法返回时，数据报文就包含了客户端的地址，这样就知道了回复信息应该发送到什么地方；
     使用 DatagramSocket 实例的 send()方法向服务器端返回 DatagramPacket 实例。
     * @param args
     */
    public static void main(String[] args) throws IOException {

        String str_send = "Hello UDPclient";
        byte[] buf = new byte[1024];
        //服务端在3000端口监听接收到的数据
        DatagramSocket client = new DatagramSocket(3000);
        //接收客户端发送过来的数据
        DatagramPacket dp_receive = new DatagramPacket(buf, 1024);
        System.out.println("server is on，waiting for client to send data......");

        boolean f = true;
        while(f){
            client.receive(dp_receive);
            System.out.println("server received data from client：");
            String str_receive = new String(dp_receive.getData(),0,dp_receive.getLength()) +
                    " from " + dp_receive.getAddress().getHostAddress() + ":" + dp_receive.getPort();
            System.out.println(str_receive);
            DatagramPacket dp_send = new DatagramPacket(str_send.getBytes(), str_send.length(), dp_receive.getAddress(), 9000);
            client.send(dp_send);
            //由于dp_receive在接收了数据之后，其内部消息长度值会变为实际接收的消息的字节数，
            //所以这里要将dp_receive的内部消息长度重新置为1024
            dp_receive.setLength(1024);
        }
        client.close();
    }
}
