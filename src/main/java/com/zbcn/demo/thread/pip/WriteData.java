package com.zbcn.demo.thread.pip;

import java.io.IOException;
import java.io.PipedOutputStream;

/**
 * TODO
 *
 * @author Administrator
 * @date 2018/11/9 0:50
 */
public class WriteData {

    public  void writeData(PipedOutputStream out){

        System.out.println("write :");

        try {
            for(int i = 0; i< 300; i++){
                String outData = "写数据" + (i + 1);
                out.write(outData.getBytes());
            }
            System.out.println();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
