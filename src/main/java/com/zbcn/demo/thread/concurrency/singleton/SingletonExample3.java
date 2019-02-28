package com.zbcn.demo.thread.concurrency.singleton;

import com.zbcn.demo.thread.concurrency.ThreadSafe;

/**
 * @Description: 懒汉模式
 * @Auther: zbcn
 * @Date: 2/27/19 15:57
 */
@ThreadSafe
public class SingletonExample3 {

    //单利对象
    private static SingletonExample3 instance = null;
    //私有化构造函数
    private SingletonExample3(){

    }
    
    //静态的工厂方法
    public static synchronized SingletonExample3 getInstance(){
        if(instance != null){
            instance = new SingletonExample3();
        }
        return instance;
    }

    public static void main(String[] args) {
        SingletonExample3 instance = SingletonExample3.getInstance();


    }
}
