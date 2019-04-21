package com.zbcn.demo.socket.self;

import java.io.IOException;

/**
 * @Description: 对投票消息进行序列化和反序列化的方法
 * @Auther: zbcn8
 * @Date: 2019/4/18 18:59
 */
public interface VoteMsgCoder {

    byte[] toWire(VoteMsg msg) throws IOException;

    VoteMsg fromWire(byte[] input) throws  IOException;
}
