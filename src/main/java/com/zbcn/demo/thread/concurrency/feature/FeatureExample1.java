package com.zbcn.demo.thread.concurrency.feature;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

/**
 * @Description:
 * @Auther: zbcn
 * @Date: 2/28/19 21:23
 */
@Slf4j
public class FeatureExample1 {

    public static void main(String[] args) throws InterruptedException, ExecutionException {

        ExecutorService executor = Executors.newCachedThreadPool();

        Future<String> submit = executor.submit(new MyCallable());

        log.info("do something in main");
        Thread.sleep(1000);
        String s = submit.get();
        log.info("result->{}",s);
    }

    static class MyCallable implements Callable {

        @Override
        public Object call() throws Exception {
            log.info("do something callable ");
            Thread.sleep(5000);
            return "Done";
        }
    }
}
