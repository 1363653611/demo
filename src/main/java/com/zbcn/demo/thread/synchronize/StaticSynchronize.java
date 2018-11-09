package com.zbcn.demo.thread.synchronize;

/**
 * 静态代码块加锁
 *
 * @author Administrator
 * @date 2018/11/8 20:27
 */
public class StaticSynchronize {
    public static void main(String[] args) {
        Servcie service = new Servcie();
        Thread_A a = new Thread_A(service);
        a.setName("A");
        a.start();

        Thread_B b = new Thread_B(service);
        b.setName("B");
        b.start();

        Thread_C c = new Thread_C(service);
        c.setName("C");
        c.start();
    }

}

/**
 * 业务代码
 */
class Servcie{

    public static void printA(){
        synchronized (Servcie.class) {
            try {
                System.out.println(
                        "线程名称为：" + Thread.currentThread().getName() + "在" + System.currentTimeMillis() + "进入printA");
                Thread.sleep(3000);
                System.out.println(
                        "线程名称为：" + Thread.currentThread().getName() + "在" + System.currentTimeMillis() + "离开printA");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    synchronized public static void printB() {
        System.out.println("线程名称为：" + Thread.currentThread().getName() + "在" + System.currentTimeMillis() + "进入printB");
        System.out.println("线程名称为：" + Thread.currentThread().getName() + "在" + System.currentTimeMillis() + "离开printB");
    }

    //费静态方法，用实例锁
    synchronized public void printC() {
        System.out.println("线程名称为：" + Thread.currentThread().getName() + "在" + System.currentTimeMillis() + "进入printC");
        System.out.println("线程名称为：" + Thread.currentThread().getName() + "在" + System.currentTimeMillis() + "离开printC");
    }


}

class Thread_A extends Thread {
    private Servcie service;
    public Thread_A(Servcie service) {
        super();
        this.service = service;
    }
    @Override
    public void run() {
        service.printA();
    }
}

class Thread_B extends Thread {
    private Servcie service;
    public Thread_B(Servcie service) {
        super();
        this.service = service;
    }
    @Override
    public void run() {
        service.printB();
    }
}




class Thread_C extends Thread {
    private Servcie service;
    public Thread_C(Servcie service) {
        super();
        this.service = service;
    }
    @Override
    public void run() {
        service.printC();
    }
}



