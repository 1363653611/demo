package com.zbcn.demo.thread.concurrency.aqs;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

/**
 * @Description: CyclicBarrier 同步辅助类，某一组线程调用await后，互相等待，当某一组线程同时到达了 cyclicBarray的屏障点后，同时执行
 * @Auther: zbcn
 * @Date: 2/27/19 22:21
 */
@Slf4j
public class CyclicBarrierExample {

    private final static Integer threadNum = 10;

   //private final static CyclicBarrier cyclicBarrier = new CyclicBarrier(5);

    private final static CyclicBarrier cyclicBarrier = new CyclicBarrier(5, () ->{
        log.error("ceshizhixing");
    });


    public static void main(String[] args) throws Exception{

        ExecutorService exec = Executors.newCachedThreadPool();

        for (int i = 0; i < threadNum; i++) {
            final int num = i;
            Thread.sleep(1000);
            exec.execute(()->{
                try {
                    test(num);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
        exec.shutdown();

    }

    public static void test(int number) throws InterruptedException, BrokenBarrierException {
        Thread.sleep(1000);
        log.info("{} ->{}",Thread.currentThread().getName(),number);
        cyclicBarrier.await();
        log.info("continue ->{} ---->{}",Thread.currentThread().getName(),number);

    }

    public static void test2(int number) throws InterruptedException, BrokenBarrierException {
        Thread.sleep(1000);
        log.info("{} ->{}",Thread.currentThread().getName(),number);
        try {
            cyclicBarrier.await(1000,TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            e.printStackTrace();
        }
        log.info("continue ->{} ---->{}",Thread.currentThread().getName(),number);

    }
}
