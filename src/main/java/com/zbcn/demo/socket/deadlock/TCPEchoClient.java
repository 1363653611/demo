package com.zbcn.demo.socket.deadlock;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * @Description:
 * @Auther: zbcn8
 * @Date: 2019/4/19 11:56
 */
public class TCPEchoClient {

    /**
     * 死锁问题的原因：因为套接字中数据的末尾并没有所谓的结束标记，无法通过其自身表示传输的数据已经结束，那么究竟什么时候 read()会返回 -1 呢？
     * 答案是：当 TCP 通信连接的一方关闭了套接字时
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        if ((args.length < 2) || (args.length > 3)) { // Test for correct # of args
            throw new IllegalArgumentException("Parameter(s): <Server> <Word> [<Port>]");
        }
        String server = args[0];       // Server name or IP address
        // Convert argument String to bytes using the default character encoding
        byte[] data = args[1].getBytes();

        int servPort = (args.length == 3) ? Integer.parseInt(args[2]) : 7;

        // Create socket that is connected to server on specified port
        Socket socket = new Socket(server, servPort);
        System.out.println("Connected to server...sending echo string");

        InputStream in = socket.getInputStream();
        OutputStream out = socket.getOutputStream();

        out.write(data);  // Send the encoded string to the server
        //调用 shutdownOutput()方法将套接字的输出流关闭，这样，服务端的 read()方法便会返回 -1
        socket.shutdownOutput();
        // Receive the same string back from the server
        int totalBytesRcvd = 0;  // Total bytes received so far
        int bytesRcvd;           // Bytes received in last read
//        while (totalBytesRcvd < data.length) {
//            if ((bytesRcvd = in.read(data, totalBytesRcvd,data.length - totalBytesRcvd)) == -1){
//                throw new SocketException("Connection closed prematurely");
//            }
//            totalBytesRcvd += bytesRcvd;
//        }  // data array is full

        while((bytesRcvd = in.read())!= -1){
            data[totalBytesRcvd] = (byte)bytesRcvd;
            totalBytesRcvd++;
        }
        System.out.println("Received: " + new String(data));

        socket.close();  // Close the socket and its streams

    }
}
