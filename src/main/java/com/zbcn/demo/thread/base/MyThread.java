package com.zbcn.demo.thread.base;

/**
 * 多个线程之间不共享线程安全的数据
 *
 * @author Administrator
 * @date 2018/11/8 16:59
 */
public class MyThread extends Thread {

    private volatile int cout = 10;

    public MyThread(String name) {
        super(name);
        this.setName(name);
    }

    @Override
    public void run(){
        //super.run();
        while (cout > 0) {
            System.out.println("MyThread 运行:"+MyThread.currentThread().getName()+"运行次数为："+cout);
            cout --;
        }

    }
    public static void main(String[] args) {
        MyThread mythread = new MyThread("a");
        MyThread b = new MyThread("b");
        MyThread c = new MyThread("c");
        mythread.start();
        b.start();
        c.start();
        System.out.println("运行结束");
    }

}
