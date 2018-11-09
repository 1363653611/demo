package com.zbcn.demo.thread.pip;

import java.io.IOException;
import java.io.PipedInputStream;

/**
 * 读取数据
 *
 * @author Administrator
 * @date 2018/11/9 0:42
 */
public class ReadData {

    public void readDate(PipedInputStream in){

        System.out.println("read  :");
        byte[] array = new byte[20];

        try {
            int readLength = in.read(array);
            while (readLength != -1){
                String data = new String(array,0,readLength);
                System.out.print("读数据"+data);
                readLength = in.read(array);
            }
            System.out.println();
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
