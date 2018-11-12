package com.zbcn.demo.thread.lock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 使用Condition实现顺序执行
 *
 * @author Administrator
 * @date 2018/11/12 19:21
 */
public class ConditionSeqExec {

    private static volatile int nextPointWho = 1;

    private static Lock lock = new ReentrantLock();
    private static Condition conditionA = lock.newCondition();
    private static Condition conditionB = lock.newCondition();
    private static Condition conditionC = lock.newCondition();

    public static void main(String[] args) {

        Thread threadA = new Thread(){
            @Override
            public void run(){
                lock.lock();
                try {
                     while(nextPointWho != 1){
                        conditionA.await();
                    }
                    for(int i = 0; i < 3;i++ ){
                        System.out.println("ThreadA " + (i+1));
                    }
                    nextPointWho = 2;
                    //通知B去运行
                    conditionB.signalAll();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    lock.unlock();
                }
            }
        };

        Thread threadB = new Thread(){
            @Override
            public void run(){
                lock.lock();
                try {
                    while(nextPointWho != 2){
                        conditionB.await();
                    }
                    for(int i = 0; i < 3;i++ ){
                        System.out.println("ThreadB " + (i+1));
                    }
                    nextPointWho = 3;
                    //通知C去运行
                    conditionC.signalAll();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    lock.unlock();
                }
            }
        };


        Thread threadC = new Thread(){
            @Override
            public void run(){
                lock.lock();
                try {
                    while(nextPointWho != 3){
                        conditionC.await();
                    }
                    for(int i = 0; i < 3;i++ ){
                        System.out.println("ThreadC " + (i+1));
                    }
                    nextPointWho = 1;
                    //通知A去运行
                    conditionA.signalAll();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally{
                    lock.unlock();
                }
            }
        };

        // 测试方法
        Thread[] threadsA = new Thread[5];
        Thread[] threadsB = new Thread[5];
        Thread[] threadsC = new Thread[5];
        for (int i = 0; i< 4; i++) {

            threadsA[i] = new Thread(threadA);
            threadsB[i] = new Thread(threadB);
            threadsC[i] = new Thread(threadC);
            threadsA[i].start();
            threadsB[i].start();
            threadsC[i].start();
        }

    }


}
