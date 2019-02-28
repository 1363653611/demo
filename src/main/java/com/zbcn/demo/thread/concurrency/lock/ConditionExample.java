package com.zbcn.demo.thread.concurrency.lock;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Description:
 * @Auther: zbcn
 * @Date: 2/28/19 20:21
 */
@Slf4j
public class ConditionExample {

    public static void main(String[] args) {
        ReentrantLock lock = new ReentrantLock();
        Condition condition = lock.newCondition();
        new Thread(() ->{
            try {
                lock.lock();
                log.info("wait singal !");//1
                condition.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.info("get singal ！");//4
            lock.unlock();
        }).start();

        new Thread(() ->{
            try {
                lock.lock();
                log.info("get lock !");//2
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            condition.signalAll();
            log.info("send singal ！");//3
            lock.unlock();
        }).start();

    }
}
