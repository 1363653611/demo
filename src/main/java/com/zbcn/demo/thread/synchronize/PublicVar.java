package com.zbcn.demo.thread.synchronize;

/**
 * 同一个锁的同步问题
 *
 * @author Administrator
 * @date 2018/11/8 19:40
 */
public class PublicVar {
    public String username = "A";
    public String password = "AA";

    synchronized public void setValue(String name,String pwd){

        try {
            this.username = name;
            Thread.sleep(5000);
            this.password = pwd;
            System.out.println("setValue method threadName ="
                               +Thread.currentThread().getName()+",username = "+username + ",pwd="+ pwd);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    synchronized public void getValue() {
        System.out.println("getValue method thread name="
                           + Thread.currentThread().getName() + " username=" + username
                           + " password=" + password);
    }


    public static void main(String[] args) {
        try {
            PublicVar publicVarRef = new PublicVar();
            ThreadC thread = new ThreadC(publicVarRef);
            thread.start();

            Thread.sleep(200);//打印结果受此值大小影响

            publicVarRef.getValue();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }



}

class ThreadC extends Thread {

    private PublicVar publicVar;

    public ThreadC(PublicVar publicVar) {
        super();
        this.publicVar = publicVar;
    }

    @Override
    public void run() {
        super.run();
        publicVar.setValue("B", "BB");
    }
}
