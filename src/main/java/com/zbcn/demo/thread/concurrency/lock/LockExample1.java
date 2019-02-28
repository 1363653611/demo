package com.zbcn.demo.thread.concurrency.lock;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Description:
 * @Auther: zbcn
 * @Date: 2/28/19 19:50
 */
@Slf4j
public class LockExample1 {

    //请求总数
    private final static int clientTotal = 5000;

    //同时并发的线程数
    private final static int threadTotal = 200;

    //计数
    private static int count = 0;

    //锁
    private static Lock lock = new ReentrantLock();

    public static void main(String[] args) throws InterruptedException {
        ExecutorService executor =
                Executors.newCachedThreadPool();
        final Semaphore semaphore = new Semaphore(threadTotal);

        final CountDownLatch countDownLatch = new CountDownLatch(clientTotal);

        for (int i = 0; i < clientTotal; i++) {
            executor.execute(()->{
                try {
                    semaphore.acquire();
                    add();
                    semaphore.release();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                countDownLatch.countDown();

            });
        }

        countDownLatch.await();
        executor.shutdown();
        log.info("count :{}",count);

    }


    private static void add(){
        lock.lock();
        try {
            count ++;
        } finally {
            lock.unlock();
        }

    }




}
