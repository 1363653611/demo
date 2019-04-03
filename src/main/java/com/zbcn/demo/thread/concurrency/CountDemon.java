package com.zbcn.demo.thread.concurrency;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

@UnThreadSafe
@Slf4j
public class CountDemon {

    private final static int  threadLocal = 200;

    private final static int clientLocal = 5000;

    private static volatile  long count;



    public static void main(String[] args) {

        ExecutorService executor = Executors.newCachedThreadPool();

        final Semaphore semaphore = new Semaphore(threadLocal);

        for (int i = 0; i < clientLocal; i++) {

            executor.execute(() -> {
                try {
                    semaphore.acquire();
                    count();
                    semaphore.release();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });

        }
        executor.shutdown();
        log.info("计数："+ count);
    }

    private static void count(){
        count ++;
    }
}
