package com.zbcn.demo.socket.framemessage;

import java.io.IOException;
import java.io.OutputStream;

/**
 * @Description:
 * @Auther: zbcn8
 * @Date: 2019/4/18 17:23
 */
public interface Frame {

    void frameMessage(byte[] message, OutputStream out)throws IOException;

    byte[] nextMessage() throws IOException;
 }
