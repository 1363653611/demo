package com.zbcn.demo.socket.pool;

import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

/**
 * @Description: 该类为多线程类，用于服务端
 * @Auther: zbcn8
 * @Date: 2019/4/18 20:44
 */
public class ServerThread  implements Runnable{

    private Socket client;

    public ServerThread(Socket client) {
        this.client = client;
    }

    private void execute(Socket client) throws IOException {
        //获取socket的输出流
        PrintStream printStream = new PrintStream(client.getOutputStream());
        //获取Socket的输入流，用来接收从客户端发送过来的数据
        BufferedReader reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
        boolean flag = true;

        while (flag) {
            String s = reader.readLine();
            if(StringUtils.isBlank(s)){

                flag = false;
            }else{
                //将接收到的字符串前面加上echo，发送到对应的客户端
                System.out.println("echo:" + s);
                printStream.println(s);
            }
        }
        reader.close();
        printStream.close();
        client.close();
    }


    /**
     * When an object implementing interface <code>Runnable</code> is used
     * to create a thread, starting the thread causes the object's
     * <code>run</code> method to be called in that separately executing
     * thread.
     * <p>
     * The general contract of the method <code>run</code> is that it may
     * take any action whatsoever.
     *
     * @see Thread#run()
     */
    @Override
    public void run() {
        try {
            execute(client);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
