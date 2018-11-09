package com.zbcn.demo.thread.synchronize;

/**
 * 重入锁测试
 *
 * @author Administrator
 * @date 2018/11/8 19:55
 */
public class Reenter {

    synchronized public void service1() {
        System.out.println("service1");
        service2();
    }

    synchronized public void service2() {
        System.out.println("service2");
        service3();
    }

    synchronized public void service3() {
        System.out.println("service3");
    }

    static class MyThread_1 extends Thread {
        @Override
        public void run() {
            Reenter service = new Reenter();
            service.service1();
        }

    }

    public static void main(String[] args) {
        MyThread_1 t = new MyThread_1();
        t.start();
    }


}
