package com.zbcn.demo.thread.lock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 使用单个Condition实例实现等待/通知机制：
 *
 * @author Administrator
 * @date 2018/11/12 18:27
 */
public class UseMoreSingleConditionWaitNotify {

    public static void main(String[] args) {

        MyserviceMoreCondition myserviceMoreCondition = new MyserviceMoreCondition();

        ThreadA threadA = new ThreadA(myserviceMoreCondition);
        threadA.setName("A");
        threadA.start();
        ThreadB threadB = new ThreadB(myserviceMoreCondition);
        threadB.setName("B");
        threadB.start();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //唤醒线程A
        myserviceMoreCondition.signalAll_A();
        //唤醒线程B
        myserviceMoreCondition.signalAll_B();

    }

    static public class ThreadA extends Thread {

        private MyserviceMoreCondition myserviceMoreCondition;

        public ThreadA(MyserviceMoreCondition myserviceMoreCondition) {
            this.myserviceMoreCondition = myserviceMoreCondition;
        }

        @Override
        public void run(){
            myserviceMoreCondition.awaitA();
        }
    }


    static public class ThreadB extends Thread {

        private MyserviceMoreCondition myserviceMoreCondition;

        public ThreadB(MyserviceMoreCondition myserviceMoreCondition) {
            this.myserviceMoreCondition = myserviceMoreCondition;
        }

        @Override
        public void run(){
            myserviceMoreCondition.awaitB();
        }

    }
}


class  MyserviceMoreCondition {
     private Lock lock = new ReentrantLock();

     private Condition conditionA = lock.newCondition();

    public Condition conditionB =lock.newCondition();

     public void awaitA(){
         lock.lock();
         try {
             System.out.println("awaitA 的时间为：" + System.currentTimeMillis() +" ThreadName="+Thread.currentThread().getName());
             conditionA.await();
             System.out.println("这是 end awaitA 时间为：" + System.currentTimeMillis() + "ThreadName=" + Thread.currentThread().getName());
         } catch (InterruptedException e) {
             e.printStackTrace();
         } finally {
             lock.unlock();
         }
     }


     public void awaitB(){
         lock.lock();
         try {
             System.out.println("awaitB 的时间为：" + System.currentTimeMillis() +" ThreadName="+Thread.currentThread().getName());
             conditionB.await();
             System.out.println("这是 end awaitB 时间为：" + System.currentTimeMillis() + "ThreadName=" + Thread.currentThread().getName());
         } catch (InterruptedException e) {
             e.printStackTrace();
         } finally {
             lock.unlock();
         }
     }



     public void signalAll_A(){
         lock.lock();
         System.out.println("signalAll_A 的时间为：" + System.currentTimeMillis() +" ThreadName="+Thread.currentThread().getName());
         try {
             conditionA.signalAll();
         } finally {
             lock.unlock();
         }
     }

    public void signalAll_B(){
        lock.lock();
        System.out.println("signalAll_B 的时间为：" + System.currentTimeMillis() +" ThreadName="+Thread.currentThread().getName());
        try {
            conditionB.signalAll();
        } finally {
            lock.unlock();
        }
    }
}
