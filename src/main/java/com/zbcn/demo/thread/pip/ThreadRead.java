package com.zbcn.demo.thread.pip;

import java.io.PipedInputStream;

/**
 * TODO
 *
 * @author Administrator
 * @date 2018/11/9 0:59
 */
public class ThreadRead extends Thread{

    private ReadData readData;

    private PipedInputStream inputStream;

    public ThreadRead(ReadData readData, PipedInputStream inputStream) {
        super();
        this.readData = readData;
        this.inputStream = inputStream;
    }

    @Override
    public void run(){
        readData.readDate(inputStream);
    }
}
