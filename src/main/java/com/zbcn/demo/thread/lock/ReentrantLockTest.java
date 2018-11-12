package com.zbcn.demo.thread.lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 锁的测试
 *
 * @author Administrator
 * @date 2018/11/12 15:10
 */
public class ReentrantLockTest {

    public static void main(String[] args) {

        MyService myService = new MyService();

        MyThread a1 = new MyThread(myService);
        MyThread a2 = new MyThread(myService);
        MyThread a3 = new MyThread(myService);
        MyThread a4 = new MyThread(myService);
        a1.start();
        a2.start();
        a3.start();
        a4.start();
    }

    static class MyThread extends Thread{

        private MyService service;

        public MyThread(MyService service) {
            this.service = service;
        }

        @Override
        public void run(){
            service.testLock();
        }

    }

}


class MyService {
    //创建锁
    private Lock lock = new ReentrantLock();

    public void testLock(){
        lock.lock();
        try {
            for(int i = 0;i <= 5; i++){
                System.out.println("ThreadName="+Thread.currentThread().getName() + "   " +(i+1));
            }
        } finally {
            lock.unlock();
        }
    }
}
