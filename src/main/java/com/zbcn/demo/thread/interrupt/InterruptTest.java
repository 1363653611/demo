package com.zbcn.demo.thread.interrupt;

/**
 * 当方法wait()被执行后，锁自动被释放，但执行完notify()方法后，锁不会自动释放。
 * 必须执行完notify()方法所在的synchronized代码块后才释放
 * 当线程呈wait状态时，对线程对象调用interrupt方法会出现InterrupedException异常
 *
 * @author Administrator
 * @date 2018/11/9 0:26
 */
public class InterruptTest {

    public static void main(String[] args) {
        try {
            Object lock = new Object();
            ThreadA a = new ThreadA(lock);
            a.start();
            Thread.sleep(5000);
            a.interrupt();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class ThreadA extends Thread {

    private Object lock;

    public ThreadA(Object lock) {
        super();
        this.lock = lock;
    }

    @Override
    public void run() {
        Service service = new Service();
        service.testMethod(lock);
    }

}

class Service {
    public void testMethod(Object lock) {
        try {
            synchronized (lock) {
                System.out.println("begin wait()");
                lock.wait();
                System.out.println("  end wait()");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            System.out.println("出现异常了，因为呈wait状态的线程被interrupt了！");
        }
    }
}

