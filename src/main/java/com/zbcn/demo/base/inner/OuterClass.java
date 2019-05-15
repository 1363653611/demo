package com.zbcn.demo.base.inner;

import java.util.Random;

/**
 * 内部类测试
 *
 * @author zbcn
 * @date 2018/10/19 15:08
 */
public class OuterClass {

    public static long OUTER_DATE = System.currentTimeMillis();
    static{
        System.out.println("外部静态块 OuterClass 加载时间："+System.currentTimeMillis());
    }

    public OuterClass() {
        timeElapsed();
        System.out.println("外部类 OuterClass 构造函数时间：" + System.currentTimeMillis());
    }

    static class InnerStaticClass {
        public static long INNER_STATIC_DATE = System.currentTimeMillis();
        static {
            System.out.println("内部静态类 InnerStaticClass 静态方法时间 ：" + System.currentTimeMillis());
        }

        public InnerStaticClass() {
            System.out.println("内部静态类 InnerStaticClass 构造函数时间：" + System.currentTimeMillis());
        }
    }

    /**
     * 内部类
     */
    class InnerClass {
        public long INNER_DATE = 0;
        public InnerClass() {
            timeElapsed();
            INNER_DATE = System.currentTimeMillis();
            System.out.println("内部类 InnerClass 构造函数时间："+INNER_DATE);
        }
    }

    /**
     * result :1. 静态内部类和非静态内部类一样，都是在被调用时才会被加载
     * @param args
     */
    public static void main(String[] args) {
        OuterClass outer = new OuterClass();
        //System.out.println("静态内部类加载时间："+InnerStaticClass.INNER_STATIC_DATE);
        //System.out.println("外部类静态变量加载时间：" + outer.OUTER_DATE);
        System.out.println("非静态内部类加载时间"+outer.new InnerClass().INNER_DATE);

    }


    //单纯的为了耗时，来扩大时间差异
    private void timeElapsed() {
        for (int i = 0; i < 10000000; i++) {
            int a = new Random(100).nextInt(), b = new Random(100).nextInt();
            a = a + b;
        }
    }
}
