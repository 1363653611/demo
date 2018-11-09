package com.zbcn.demo.thread.notice_wait;

import java.util.ArrayList;
import java.util.List;

/**
 * 通知/等待
 * @author Administrator
 * @date 2018/11/8 22:16
 */
public class MyList {

    private static List<String> list = new ArrayList<String>();

    public static void add() {
        list.add("anyString");
    }

    public static int size() {
        return list.size();
    }

    public static void main(String[] args) {

        try {
            Object lock = new Object();
            ThreadA a = new ThreadA(lock);
            a.start();

            Thread.sleep(50);

            ThreadB b = new ThreadB(lock);
            b.start();
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
    public void run(){
        synchronized (lock) {
            try {
                if(MyList.size() != 4){
                    System.out.println("wait begin "
                                       + System.currentTimeMillis());
                    lock.wait();
                    System.out.println("wait end  "
                                       + System.currentTimeMillis());
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }


}


class ThreadB extends Thread {

    private Object lock;

    public ThreadB(Object lock){
        super();
        this.lock = lock;
    }

    @Override
    public void run(){
        //需要加锁，否则会报IllegalMonitorStateException
        synchronized (lock) {
            try {
                for(int i = 0;i<= 10; i++){
                    MyList.add();
                    if(MyList.size() == 5){
                        lock.notify();
                        System.out.println("已发出通知！");
                    }
                    System.out.println("添加了" + (i + 1) + "个元素!");
                    Thread.sleep(1000);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
