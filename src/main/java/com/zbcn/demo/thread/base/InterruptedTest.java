package com.zbcn.demo.thread.base;

/**
 * 打断测试
 *
 * @author Administrator
 * @date 2018/11/8 17:56
 */
public class InterruptedTest extends Thread {

    @Override
    public void run(){
        for (int i = 0; i < 5000000; i++) {
            if(this.isInterrupted()){
                System.out.println("已经是停止状态了!我要退出了!");
                break;
            }
            System.out.println("i=" + (i + 1));
        }
        System.out.println("看到这句话说明线程并未终止------");
    }

    public static void main(String[] args) {

        InterruptedTest thread = new InterruptedTest();
        thread.start();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        thread.interrupt();

    }


}
