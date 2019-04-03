package com.zbcn.demo.thread.concurrency.aqs;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CyclicBarrierDemon {

    public static void main(String[] args) {

        int total = 10;
        ExecutorService executor = Executors.newCachedThreadPool();

        CyclicBarrier cyclicBarrier = new CyclicBarrier(total);

        for (int i = 0; i < 10; i++) {
            final int count = i;
            executor.execute(() ->{
                System.out.println("执行线程thread - " + count);
                try {
                    cyclicBarrier.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
                System.out.println("到达屏障后执行");
            } );
        }
        executor.shutdown();
    }
}
