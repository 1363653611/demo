package com.zbcn.demo.thread.concurrency.aqs;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

/**
 * @Description: 信号量 semaphore 控制同时并发线程的数量
 * @Auther: zbcn
 * @Date: 2/27/19 21:42
 */
@Slf4j
public class SemaphoreExample1 {
    private static final Integer threadNumber = 200;
    private static Semaphore semaphore;
    private static int num;

    public static void main(String[] args) throws InterruptedException {
        ExecutorService exec = Executors.newCachedThreadPool();

        Semaphore semaphore = new Semaphore(3);

        for (int i = 0; i < threadNumber; i++) {
            final int num = i;
            exec.execute(() ->{
                semaphore4(semaphore, num);
                //semaphore2(semaphore, num);
            });
        }

        log.info("finish");
        exec.shutdown();

    }

    private static void semaphore(Semaphore semaphore, int num) {
        try {
            semaphore.acquire();
            test(num);
            semaphore.release();
        } catch (InterruptedException e) {
            log.error("execption:",e);
        }
    }

    private static void semaphore2(Semaphore semaphore, int num) {
        try {
            semaphore.acquire(3);
            test(num);
            semaphore.release(3);
        } catch (InterruptedException e) {
            log.error("execption:",e);
        }
    }

    private static void semaphore3(Semaphore semaphore, int num) {
        try {
            if(semaphore.tryAcquire()){
                test(num);
            }
            //semaphore.release(3);
        } catch (InterruptedException e) {
            log.error("execption:",e);
        }
    }

    private static void semaphore4(Semaphore semaphore, int num) {
        SemaphoreExample1.semaphore = semaphore;
        SemaphoreExample1.num = num;
        try {
            if(semaphore.tryAcquire(1000,TimeUnit.SECONDS)){
                test(num);
            }
            semaphore.release();
        } catch (InterruptedException e) {
            log.error("execption:",e);
        }
    }

    public static void test(int number) throws InterruptedException {
        Thread.sleep(1000);
        log.info("{} ->{}",Thread.currentThread().getName(),number);

    }
}
