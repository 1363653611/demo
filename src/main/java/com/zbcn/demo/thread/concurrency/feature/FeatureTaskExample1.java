package com.zbcn.demo.thread.concurrency.feature;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

/**
 * @Description:
 * @Auther: zbcn
 * @Date: 2/28/19 21:23
 */
@Slf4j
public class FeatureTaskExample1 {

    public static void main(String[] args) throws InterruptedException, ExecutionException {

        FutureTask<String> futureTask = new FutureTask<String>(new MyCallable());

        new Thread(futureTask).start();

        log.info("do something in main ");

        Thread.sleep(1000);
        String s = futureTask.get();
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
