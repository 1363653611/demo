package com.zbcn.demo.thread.join;

/**
 * 在很多情况下，主线程生成并起动了子线程，如果子线程里要进行大量的耗时的运算，
 * 主线程往往将于子线程之前结束，但是如果主线程处理完其他的事务后，需要用到子线程的处理结果，
 * 也就是主线程需要等待子线程执行完成之后再结束，这个时候就要用到join()方法了。另外，一个线程需要等待另一个线程也需要用到join()方法。

 * @author Administrator
 * @date 2018/11/9 13:51
 */
public class JoinTest {

    public static void main(String[] args) {

        JoinThread joinThread = new JoinThread();
        joinThread.start();
        //睡眠时间不确定，应为不知道joinThread需要执行多长时间
        try {
            joinThread.join();
            //Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("我想当threadTest对象执行完毕后我再执行");

    }

    static class JoinThread extends Thread {

        @Override
        public void run(){
            System.out.printf("JoinThread 执行");
        }

    }
}
