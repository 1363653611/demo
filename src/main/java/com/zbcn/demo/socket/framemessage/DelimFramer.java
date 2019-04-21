package com.zbcn.demo.socket.framemessage;

import java.io.*;

/**
 * @Description: 基于定界符的成帧方法
 * @Auther: zbcn8
 * @Date: 2019/4/18 17:26
 */
public class DelimFramer implements Frame {
    /**
     * 数据来源
     */
    private InputStream in;
    /**
     * 定界符
     */
    private static final byte DELIMITER= '\n';

    public DelimFramer(InputStream in) {
        this.in = in;
    }
    @Override
    public void frameMessage(byte[] message, OutputStream out) throws IOException {
        for (byte b: message) {
            if(b == DELIMITER){
                //如果在消息中检查到界定符，则抛出异常
                throw new IOException("Message contains delimiter");
            }
        }
        out.write(message);
        out.write(DELIMITER);
        out.flush();
    }

    @Override
    public byte[] nextMessage() throws IOException {

        ByteArrayOutputStream messageBuffer = new ByteArrayOutputStream();
        int nextByte;
        while((nextByte = in.read()) != DELIMITER){

            if(nextByte == -1){
                //如果读取到的流为空，则返回null
                if (messageBuffer.size() == 0) {
                    return null;
                } else {
                    //如果读取到的流不为空，则抛出异常
                    throw new EOFException("Non-empty message without delimiter");
                }
            }
            messageBuffer.write(nextByte);
        }
        return messageBuffer.toByteArray();
    }
}
