package com.zbcn.demo.thread.join;

import org.junit.runner.RunWith;

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
        System.out.println("我想当JoinThread对象执行完毕后我再执行");
        test();
    }

    static class JoinThread extends Thread {

        @Override
        public void run(){
            System.out.println("JoinThread 执行");
        }

    }

    private static void test(){
        A a = new A();
        Thread threadA = new Thread(a);
        B b = new B(threadA);
        Thread threadB = new Thread(b);
        threadA.start();
        threadB.start();
    }


}


class A  implements Runnable{

    /**
     * When an object implementing interface <code>Runnable</code> is used
     * to create a thread, starting the thread causes the object's
     * <code>run</code> method to be called in that separately executing
     * thread.
     * <p>
     * The general contract of the method <code>run</code> is that it may
     * take any action whatsoever.
     *
     * @see Thread#run()
     */
    @Override
    public void run() {
        try {
            Thread.sleep(1000);
            System.out.println("A线程执行！");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class B implements Runnable {

    private Thread a;
    public B(){}

    public B(Thread a){
        this.a = a;
    }


    /**
     * When an object implementing interface <code>Runnable</code> is used
     * to create a thread, starting the thread causes the object's
     * <code>run</code> method to be called in that separately executing
     * thread.
     * <p>
     * The general contract of the method <code>run</code> is that it may
     * take any action whatsoever.
     *
     * @see Thread#run()
     */
    @Override
    public void run() {
        try {
            a.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("b 等 a 结束后再执行");
    }
}
