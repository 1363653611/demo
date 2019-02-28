package com.zbcn.demo.thread.concurrency.singleton;

import com.zbcn.demo.thread.concurrency.ThreadSafe;

/**
 * @Description: 懒汉模式 --双层同步锁单利模式
 * @Auther: zbcn
 * @Date: 2/27/19 15:57
 */
@ThreadSafe
public class SingletonExample4 {

    //单利对象
    //volatile + 双层检测机制 ——————》可以禁止指令重排
    private static volatile SingletonExample4 instance = null;
    //私有化构造函数
    private SingletonExample4(){

    }
    
    //静态的工厂方法
    public static SingletonExample4 getInstance(){
        if(instance == null){//双层检测机制
            synchronized (SingletonExample4.class){//同步锁
                if(instance == null){
                    instance = new SingletonExample4();
                }
            }

        }
        return instance;
    }

    public static void main(String[] args) {
        SingletonExample4 instance = SingletonExample4.getInstance();
        System.out.println(instance.hashCode());
    }
}
