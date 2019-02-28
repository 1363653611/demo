package com.zbcn.demo.thread.concurrency.singleton;

import com.zbcn.demo.thread.concurrency.ThreadSafe;

/**
 * @Description: 饿汉模式
 * @Auther: zbcn
 * @Date: 2/27/19 15:57
 */
@ThreadSafe
public class SingletonExample2 {

    //单利对象
    private static SingletonExample2 instance = new SingletonExample2();
    //私有化构造函数
    private SingletonExample2(){

    }
    
    //静态的工厂方法
    public static SingletonExample2 getInstance(){
        return instance;
    }

    public static void main(String[] args) {
        SingletonExample2 instance = SingletonExample2.getInstance();
    }
}
