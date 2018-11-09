package com.zbcn.demo.thread.synchronize;

/**
 * 锁的继承性
 *
 * @author Administrator
 * @date 2018/11/8 20:02
 */
public class FatherAndSub {
    static class MyThread_2 extends Thread {
        @Override
        public void run() {
            Sub sub = new Sub();
            sub.operatorSub();
        }
    }
    //说明当存在父子类继承关系时，子类是完全可以通过“可重入锁”调用父类的同步方法
    //另外出现异常时，其锁持有的锁会自动释放。
    //如果父类有一个带synchronized关键字的方法，子类继承并重写了这个方法。
    //但是同步不能继承，所以还是需要在子类方法中添加synchronized关键字。
    public static void main(String[] args) {
        MyThread_2 myThread_2 = new MyThread_2();
        myThread_2.start();
    }
}


class Main{
    public int i = 10;

    synchronized public void operatorMain(){
        try {
            i--;
            System.out.println("main print i=" + i);
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}


class Sub extends Main{
    synchronized public void operatorSub(){
        try {
            //调用父类
            this.operatorMain();
            i--;
            Thread.sleep(200);
            System.out.println("sub print i=" + i);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
