package com.zbcn.demo.java8.sync;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

/**
 * ForkJoinPool
 * fork/join 并行功能借助并行流的机制变得更加具有可用性。但是，不是所有的问题都适合这种风格的并行处理：所处理的元素必须是独立的，数据集要足够大，
 * 并且在并行加速方面，每个元素的处理成本要足够高，这样才能补偿建立 fork/join 框架所消耗的成本
 */
public class CompletableFutureExamples {

    /**
     * 使用固定的线程池完成异步执行动作示例
     */
    static ExecutorService executor = Executors.newFixedThreadPool(3, new ThreadFactory() {
        int count = 1;

        @Override
        public Thread newThread(Runnable runnable) {
            return new Thread(runnable, "custom-executor-" + count++);
        }
    });

    static Random random = new Random();

    public static void main(String[] args) {
//        try {
////            allOfAsyncExample();
//        } finally {
//            executor.shutdown();
//        }
//        completedFutureExample();
//        runAsyncExample();
//        thenApplyExample();
//        thenApplyAsyncWithExecutorExample();
        completeExceptionallyExample();
        cancelExample();
    }


    /**
     * 创建完整的 CompletableFuture
     * 以下代码一般来说被用于启动异步计算，getNow(null)返回计算结果或者 null
     */
    static void completedFutureExample() {
        CompletableFuture<String> cf = CompletableFuture.completedFuture("message");
        assertTrue(cf.isDone());
        assertEquals("message", cf.getNow(null));
    }

    /**
     * 运行简单的异步场景
     * CompletableFuture的方法如果以Async结尾，它会异步的执行(没有指定executor的情况下)， 异步执行通过ForkJoinPool实现，
     * 它使用守护线程去执行任务。注意这是CompletableFuture的特性， 其它CompletionStage可以override这个默认的行为。
     */
    static void runAsyncExample() {
        CompletableFuture<Void> cf = CompletableFuture.runAsync(() -> {
            assertTrue(Thread.currentThread().isDaemon());
            randomSleep();
        });
        assertFalse(cf.isDone());
        sleepEnough();
        assertTrue(cf.isDone());
    }

    /**
     * 同步执行动作示例:
     *
     * 在前一个阶段上应用函数(注意thenApply方法名称代表的行为。)
     * then意味着这个阶段的动作发生当前的阶段正常完成之后。本例中，当前节点完成，返回字符串message。
     * Apply意味着返回的阶段将会对结果前一阶段的结果应用一个函数。
     * 函数的执行会被阻塞，这意味着getNow()只有打斜操作被完成后才返回。
     */
    static void thenApplyExample() {
        CompletableFuture<String> cf = CompletableFuture.completedFuture("message").thenApply(s -> {
            assertFalse(Thread.currentThread().isDaemon());
            return s.toUpperCase();
        });
        System.out.println(cf.getNow(null));
        assertEquals("MESSAGE", cf.getNow(null));
    }

    /**
     *异步执行动作示例
     */
    static void thenApplyAsyncExample() {
        CompletableFuture<String> cf = CompletableFuture.completedFuture("message").thenApplyAsync(s -> {
            assertTrue(Thread.currentThread().isDaemon());
            randomSleep();
            return s.toUpperCase();
        });
        assertNull(cf.getNow(null));
        assertEquals("MESSAGE", cf.join());
    }

    /**
     * 使用固定的线程池完成异步执行动作示例
     */
    static void thenApplyAsyncWithExecutorExample() {
        CompletableFuture<String> cf = CompletableFuture.completedFuture("message").thenApplyAsync(s -> {
            assertTrue(Thread.currentThread().getName().startsWith("custom-executor-"));
            assertFalse(Thread.currentThread().isDaemon());
            randomSleep();
            return s.toUpperCase();
        }, executor);

        assertNull(cf.getNow(null));
        assertEquals("MESSAGE", cf.join());
    }

    /**
     * 作为消费者消费计算结果示例
     * 假设我们本次计算只需要前一次的计算结果，而不需要返回本次计算结果，那就有点类似于生产者（前一次计算）-消费者（本次计算）模式了
     */
    static void thenAcceptExample() {
        StringBuilder result = new StringBuilder();
        CompletableFuture.completedFuture("thenAccept message")
                         .thenAccept(s -> result.append(s));
        assertTrue("Result was empty", result.length() > 0);
    }

    /**
     * 异步消费示例
     */
    static void thenAcceptAsyncExample() {
        StringBuilder result = new StringBuilder();
        CompletableFuture<Void> cf = CompletableFuture.completedFuture("thenAcceptAsync message")
                                                      .thenAcceptAsync(s -> result.append(s));
        cf.join();
        assertTrue("Result was empty", result.length() > 0);
    }

    /**
     * todo 延时执行线程有问题，需要处理
     * 计算过程中的异常示例
     */
    static void completeExceptionallyExample() {
        //延时执行的方法
        CompletableFuture<String> cf = CompletableFuture.completedFuture("message").thenApplyAsync(String :: toUpperCase,
                                                                                                   delayedExecutor(3, TimeUnit.SECONDS));

        CompletableFuture<String> exceptionHandler = cf.handle((s, th) -> { return (th != null) ? "message upon cancel" : ""; });
        cf.completeExceptionally(new RuntimeException("completed exceptionally"));
        assertTrue("Was not completed exceptionally", cf.isCompletedExceptionally());
        try {
            cf.join();
            fail("Should have thrown an exception");
        } catch (CompletionException ex) { // just for testing
            assertEquals("completed exceptionally", ex.getCause().getMessage());
        }

        assertEquals("message upon cancel", exceptionHandler.join());
    }


    /**
     * 取消计算任务
     */
    static void cancelExample() {
        CompletableFuture<String> cf = CompletableFuture.completedFuture("message").thenApplyAsync(String::toUpperCase,
                                                                                                   delayedExecutor(1, TimeUnit.SECONDS));
        CompletableFuture<String> cf2 = cf.exceptionally(throwable -> "canceled message");
        assertTrue("Was not canceled", cf.cancel(true));
        assertTrue("Was not completed exceptionally", cf.isCompletedExceptionally());
        assertEquals("canceled message", cf2.join());
    }

    /**
     * 一个 CompletableFuture VS 两个异步计算
     * 我们可以创建一个 CompletableFuture 接收两个异步计算的结果，下面代码首先创建了一个 String 对象，接下来分别创建了两个
     * CompletableFuture 对象 cf1 和 cf2，cf2 通过调用 applyToEither 方法实现我们的需求。
     */
    static void applyToEitherExample() {
        String original = "Message";
        CompletableFuture<String> cf1 = CompletableFuture.completedFuture(original)
                                                         .thenApplyAsync(s -> delayedUpperCase(s));
        CompletableFuture<String> cf2 = cf1.applyToEither(
                CompletableFuture.completedFuture(original).thenApplyAsync(s -> delayedLowerCase(s)),
                s -> s + " from applyToEither");
        assertTrue(cf2.join().endsWith(" from applyToEither"));
    }

    /**
     * 想要使用消费者替换清单 14 的方法方式用于处理异步计算结果
     */
    static void acceptEitherExample() {
        String original = "Message";
        StringBuilder result = new StringBuilder();
        CompletableFuture<Void> cf = CompletableFuture.completedFuture(original)
                                                      .thenApplyAsync(s -> delayedUpperCase(s))
                                                      .acceptEither(CompletableFuture.completedFuture(original).thenApplyAsync(s -> delayedLowerCase(s)),
                                                                    s -> result.append(s).append("acceptEither"));
        cf.join();
        assertTrue("Result was empty", result.toString().endsWith("acceptEither"));
    }

    /**
     * 运行两个阶段后执行:
     * 下面这个示例程序两个阶段执行完毕后返回结果，首先将字符转为大写，然后将字符转为小写，在两个计算阶段都结束之后触发 CompletableFuture
     */
    static void runAfterBothExample() {
        String original = "Message";
        StringBuilder result = new StringBuilder();
        CompletableFuture.completedFuture(original).thenApply(String::toUpperCase).runAfterBoth(
                CompletableFuture.completedFuture(original).thenApply(String::toLowerCase),
                () -> result.append("done"));
        assertTrue("Result was empty", result.length() > 0);
    }

    /**
     * 也可以通过以下方式处理异步计算结果，
     */
    static void thenAcceptBothExample() {
        String original = "Message";
        StringBuilder result = new StringBuilder();
        CompletableFuture.completedFuture(original).thenApply(String::toUpperCase).thenAcceptBoth(
                CompletableFuture.completedFuture(original).thenApply(String::toLowerCase),
                (s1, s2) -> result.append(s1 + s2));
        assertEquals("MESSAGEmessage", result.toString());
    }

    /**
     * 整合两个计算结果(同步方式执行两个方法后再合成字符串)
     * 我们可以通过 thenCombine()方法整合两个异步计算的结果，注意，以下代码的整个程序过程是同步的，
     *  getNow()方法最终会输出整合后的结果，也就是说大写字符和小写字符的串联值。
     */
    static void thenCombineExample() {
        String original = "Message";
        CompletableFuture<String> cf = CompletableFuture.completedFuture(original).thenApply(s -> delayedUpperCase(s))
                                                        .thenCombine(CompletableFuture.completedFuture(original).thenApply(s -> delayedLowerCase(s)),
                                                                     (s1, s2) -> s1 + s2);
        assertEquals("MESSAGEmessage", cf.getNow(null));
    }

    /**
     * 整合两个计算结果
     * 以下代码采用异步方式同步执行两个方法，由于异步方式情况下不能够确定哪一个方法最终执行完毕，所以我们需要调用 join()方法等待后一个方法结束后再合成字符串，这一点和线程的 join()方法是一致的，主线程生成并起动了子线程，如果子线程里要进行大量的耗时的运算，主线程往往将于子线程之前结束，但是如果主线程处理完其他的事务后，需要用到子线程的处理结果，
     * 也就是主线程需要等待子线程执行完成之后再结束，这个时候就要用到 join()方法了，即 join()的作用是："等待该线程终止"。
     */
    static void thenCombineAsyncExample() {
        String original = "Message";
        CompletableFuture<String> cf = CompletableFuture.completedFuture(original)
                                                        .thenApplyAsync(s -> delayedUpperCase(s))
                                                        .thenCombine(CompletableFuture.completedFuture(original).thenApplyAsync(s -> delayedLowerCase(s)),
                                                                     (s1, s2) -> s1 + s2);
        assertEquals("MESSAGEmessage", cf.join());
    }

    /**
     * 这个方法也会实现两个方法执行后的返回结果的连接。
     */
    static void thenComposeExample() {
        String original = "Message";
        CompletableFuture<String> cf = CompletableFuture.completedFuture(original).thenApply(s -> delayedUpperCase(s))
                                                        .thenCompose(upper -> CompletableFuture.completedFuture(original).thenApply(s -> delayedLowerCase(s))
                                                                                               .thenApply(s -> upper + s));
        assertEquals("MESSAGEmessage", cf.join());
    }

    /**
     * anyOf()方法
     */
    static void anyOfExample() {
        StringBuilder result = new StringBuilder();
        List<String> messages = Arrays.asList("a", "b", "c");
        List<CompletableFuture<String>> futures = messages.stream()
                                                          .map(msg -> CompletableFuture.completedFuture(msg).thenApply(s -> delayedUpperCase(s)))
                                                          .collect(Collectors.toList());
        CompletableFuture.anyOf(futures.toArray(new CompletableFuture[futures.size()])).whenComplete((res, th) -> {
            if(th == null) {
                assertTrue(isUpperCase((String) res));
                result.append(res);
            }
        });
        assertTrue("Result was empty", result.length() > 0);
    }

    /**
     * 当所有的 CompletableFuture 完成后创建 CompletableFutur
     */
    static void allOfExample() {
        StringBuilder result = new StringBuilder();
        List<String> messages = Arrays.asList("a", "b", "c");
        List<CompletableFuture<String>> futures = messages.stream()
                                                          .map(msg -> CompletableFuture.completedFuture(msg).thenApply(s -> delayedUpperCase(s)))
                                                          .collect(Collectors.toList());
        CompletableFuture.allOf(futures.toArray(new CompletableFuture[futures.size()])).whenComplete((v, th) -> {
            futures.forEach(cf -> assertTrue(isUpperCase(cf.getNow(null))));
            result.append("done");
        });
        assertTrue("Result was empty", result.length() > 0);
    }

    /**
     * 相较于前一个同步示例，我们也可以异步执行
     */
    static void allOfAsyncExample() {
        StringBuilder result = new StringBuilder();
        List<String> messages = Arrays.asList("a", "b", "c");
        List<CompletableFuture<String>> futures = messages.stream()
                                                          .map(msg -> CompletableFuture.completedFuture(msg).thenApplyAsync(s -> delayedUpperCase(s)))
                                                          .collect(Collectors.toList());
        CompletableFuture<Void> allOf = CompletableFuture.allOf(futures.toArray(new CompletableFuture[futures.size()]))
                                                         .whenComplete((v, th) -> {
                                                             futures.forEach(cf -> assertTrue(isUpperCase(cf.getNow(null))));
                                                             result.append("done");
                                                         });
        allOf.join();
        assertTrue("Result was empty", result.length() > 0);
    }

    private static boolean isUpperCase(String s) {
        for (int i = 0; i < s.length(); i++) {
            if (Character.isLowerCase(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    private static String delayedUpperCase(String s) {
        randomSleep();
        return s.toUpperCase();
    }

    private static String delayedLowerCase(String s) {
        randomSleep();
        return s.toLowerCase();
    }

    private static void randomSleep() {
        try {
            Thread.sleep(random.nextInt(1000));
        } catch (InterruptedException e) {
            // ...
        }
    }

    private static void sleepEnough() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            // ...
        }
    }

    private static ExecutorService delayedExecutor(Integer delay, TimeUnit timeUnit){

        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        executor.schedule(new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                System.out.println(2);
                return null;
            }
        }, delay, timeUnit);
        return executor;
    }

}
