package com.zbcn.demo.thread.lock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 使用Condition实现等待/通知机制
 *
 * @author Administrator
 * @date 2018/11/12 15:36
 */
public class UseSingleConditionWaitNotify {

    public static void main(String[] args) {
        MyService myService = new MyService();

        MytheadA mytheadA = new MytheadA(myService);
        mytheadA.start();
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        myService.signal();
    }

    static class MyService{

        private Lock lock = new ReentrantLock();

        private Condition condition = lock.newCondition();

        public void await(){
            lock.lock();
            try {
                System.out.println("await 的锁定时间为");
                condition.await();
                System.out.println("这是condition.await()方法之后的语句，condition.signal()方法之后我才被执行");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }

        public void signal(){
            lock.lock();
            try {
                System.out.println("signal时间为:" + System.currentTimeMillis());
                condition.signal();
                Thread.sleep(3000);
                System.out.println("condition.signal() 方法之后的语句。");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
    }

    static class MytheadA extends Thread{

        private MyService service;

        public MytheadA(MyService service) {
            this.service = service;
        }

        @Override
        public void run(){
            service.await();
        }
    }
}
