package com.zbcn.demo.thread.pip;

import java.io.PipedOutputStream;

/**
 * 写数据线程
 *
 * @author Administrator
 * @date 2018/11/9 1:03
 */
public class ThreadWrite extends Thread {

    private WriteData writeData;

    private PipedOutputStream outputStream;

    public ThreadWrite(WriteData writeData,PipedOutputStream outputStream) {
        this.writeData = writeData;
        this.outputStream = outputStream;
    }

    @Override
    public void run(){
        writeData.writeData(outputStream);
    }
}
