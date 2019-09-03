package com.zbcn.demo.base.serialize;

import java.io.*;

/**
 * transient关键字不序列化某个变量，追读取的时候，对去数据的顺序和存放的顺序要保持一致
 *
 * @author
 * @create 2018-06-11 15:03
 **/
public class TransientTest {

    public static void main(String[] args) {

        User user = new User();
        user.setUsername("Alexia");
        user.setPwd("123456");
        System.out.println("read before Serializable: ");
        System.out.println("username: " + user.getUsername());
        System.err.println("password: " + user.getPwd());

        try {
            ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream("F:\\zbcn\\test\\user.txt"));
            os.writeObject(user);
            os.flush();
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            ObjectInputStream is = new ObjectInputStream(new FileInputStream("F:\\zbcn\\test\\user.txt"));
            user = (User) is.readObject();
            is.close();
            System.out.println("\nread after Serializable: ");
            System.out.println("username: " + user.getUsername());
            System.err.println("password: " + user.getPwd());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}
