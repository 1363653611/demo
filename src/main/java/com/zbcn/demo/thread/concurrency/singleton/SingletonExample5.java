package com.zbcn.demo.thread.concurrency.singleton;

import com.zbcn.demo.thread.concurrency.Recommend;
import com.zbcn.demo.thread.concurrency.ThreadSafe;
import lombok.Getter;

/**
 * @Description: 枚举模式:最安全的
 * @Auther: zbcn
 * @Date: 2/27/19 15:57
 */
@ThreadSafe
@Recommend
public class SingletonExample5 {

    //1.单利对象
    private static SingletonExample5 instance = null;
    //2.私有化构造函数
    private SingletonExample5(){

    }

    public static SingletonExample5 getInstance(){
        return Singleton.INSTANCE.getSingleton();
    }
    
    //3.定义私有的枚举类
    @Getter
    private enum Singleton{
        INSTANCE;

        private SingletonExample5 singleton;
        //JVM保证这个方法绝对只调用一次
        Singleton(){
            singleton = new SingletonExample5();
        }

    }

    public static void main(String[] args) {

    }
}
