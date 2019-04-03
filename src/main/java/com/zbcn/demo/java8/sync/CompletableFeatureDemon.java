package com.zbcn.demo.java8.sync;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;

import java.util.concurrent.CompletableFuture;

/**
 * 既然CompletableFuture类实现了CompletionStage接口，首先我们需要理解这个接口的契约。
 * 它代表了一个特定的计算的阶段，可以同步或者异步的被完成。
 * 你可以把它看成一个计算流水线上的一个单元，最终会产生一个最终结果，
 * 这意味着几个CompletionStage可以串联起来，一个完成的阶段可以触发下一阶段的执行，
 * 接着触发下一次，接着……
 *
 * @author Administrator
 * @date 2019/1/14 14:03
 */
@Slf4j
public class CompletableFeatureDemon {

    /**
     * getNow(null)方法在future完成的情况下会返回结果，就比如上面这个例子，否则返回null (传入的参数)。
     */
    public static void runcompletableExample(){
        CompletableFuture cf = CompletableFuture.completedFuture("message");
        log.info("{}",cf.isDone());
        log.info("message:{}",cf.getNow(null));
    }

    public static void runAsyncExample(){
        CompletableFuture.runAsync(() ->{
            Assert.isTrue(Thread.currentThread().isDaemon());
           // randomSleep();
        });
    }

    public static void main(String[] args) {
        runAsyncExample();
    }

}
