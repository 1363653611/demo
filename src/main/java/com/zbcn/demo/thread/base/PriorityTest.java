package com.zbcn.demo.thread.base;

/**
 * 线程优先级测试
 *
 * @author Administrator
 * @date 2018/11/8 18:27
 */
public class PriorityTest {

    //线程优先级具有继承特性比如A线程启动B线程，则B线程的优先级和A是一样的。
    //
    //线程优先级具有随机性也就是说线程优先级高的不一定每一次都先执行完。
    public static void main(String[] args) {

        System.out.println("main thread begin priority="
                           + Thread.currentThread().getPriority());
        Thread.currentThread().setPriority(6);

        System.out.println("main thread end   priority="
                           + Thread.currentThread().getPriority());
        MyThread1 thread1 = new MyThread1();
        thread1.start();
    }

    static class MyThread1 extends Thread {

        @Override
        public void run(){
            System.out.println("MyThread1 run priority=" + this.getPriority());
            MyThread2 thread2 = new MyThread2();
            thread2.start();
        }
    }

    static class MyThread2 extends Thread{
        @Override
        public void run(){
            this.setPriority(1);
            System.out.println("MyThread2 run priority=" + this.getPriority());
        }
    }
}

