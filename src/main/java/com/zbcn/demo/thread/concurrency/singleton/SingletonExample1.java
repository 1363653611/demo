package com.zbcn.demo.thread.concurrency.singleton;

import com.zbcn.demo.thread.concurrency.UnThreadSafe;

/**
 * @Description: 懒汉模式
 * @Auther: zbcn
 * @Date: 2/27/19 15:57
 */
@UnThreadSafe
public class SingletonExample1 {

    //单利对象
    private static SingletonExample1 instance = null;
    //私有化构造函数
    private SingletonExample1(){

    }
    
    //静态的工厂方法
    public static SingletonExample1 getInstance(){
        if(instance != null){
            instance = new SingletonExample1();
        }
        return instance;
    }

    public static void main(String[] args) {
        SingletonExample1.getInstance();


    }
}
