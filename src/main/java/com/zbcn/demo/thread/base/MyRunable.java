package com.zbcn.demo.thread.base;

/**
 * 继承Runnable 接口
 *
 * @author Administrator
 * @date 2018/11/8 17:14
 */
public class MyRunable implements Runnable  {

    private volatile int count = 10;

    @Override
    public synchronized void run() {
        System.out.println("MyRunnable 运行 运行次数="+count);
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        count --;
    }

    public static void main(String[] args) {
        // 多个线程共享资源
        MyRunable myRunable = new MyRunable();
        Thread thread = new Thread(myRunable,"a");
        Thread b = new Thread(myRunable,"b");
        Thread c = new Thread(myRunable,"b");
        thread.start();
        b.start();
        c.start();
        System.out.println("测试完毕");
    }
}
