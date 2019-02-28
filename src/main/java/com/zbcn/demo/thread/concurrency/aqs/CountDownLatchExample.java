package com.zbcn.demo.thread.concurrency.aqs;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @Description: 同步辅助类，类似于阻塞当前线程
 *  1. 同时只有一个线程可以操作改计数器，调用await方法的类一直会处于阻塞状态，直到其他线程调用countDown方法，使
 *  countDownLanch 中的计数为 0 时才可以使当前线程执行
 *  2. countDownLanch 的计数只能初始化一次，初始化之后，再不能修改了
 * @Auther: zbcn
 * @Date: 2/27/19 21:01
 */
@Slf4j
public class CountDownLatchExample {
    
    private static final Integer threadNumber = 200;

    public static void main(String[] args) throws InterruptedException {
        ExecutorService exec = Executors.newCachedThreadPool();

        CountDownLatch countDownLatch = new CountDownLatch(threadNumber);

        for (int i = 0; i < threadNumber; i++) {
            final int num = i;
            exec.execute(() ->{
                try {
                    test(num);
                } catch (InterruptedException e) {
                    log.error("execption:",e);
                }finally {
                    countDownLatch.countDown();
                }
            });
        }

        //countDownLatch.await();
        countDownLatch.await(10,TimeUnit.MILLISECONDS);
        log.info("finish");
        exec.shutdown();

    }

    public static void test(int number) throws InterruptedException {
        Thread.sleep(100);
        log.info("{} ->{}",Thread.currentThread().getName(),number);

    }
}
