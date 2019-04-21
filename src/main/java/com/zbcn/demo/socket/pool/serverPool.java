package com.zbcn.demo.socket.pool;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Description: 该类实现基于线程池的服务器
 * @Auther: zbcn8
 * @Date: 2019/4/18 20:53
 */
public class serverPool {

    private static final int THREADPOOLSIZE = 2;

    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(20006);
        ExecutorService executor = Executors.newFixedThreadPool(THREADPOOLSIZE);
        Socket client = null;
        // 在线程池中一共只有THREADPOOLSIZE个线程，
        // 最多有THREADPOOLSIZE个线程在accept()方法上阻塞等待连接请求
        boolean f = true;
        while(f){
            //等待客户端的连接
            client = server.accept();
            System.out.println("与客户端连接成功！");
            //调用execute()方法时，如果必要，会创建一个新的线程来处理任务，但它首先会尝试使用已有的线程，
            //如果一个线程空闲60秒以上，则将其移除线程池；
            //另外，任务是在Executor的内部排队，而不是在网络中排队
            executor.execute(new ServerThread(client));
        }
        server.close();
    }
}
