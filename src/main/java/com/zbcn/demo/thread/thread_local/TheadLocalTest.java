package com.zbcn.demo.thread.thread_local;

/**
 * ThreadLocal类主要解决的就是让每个线程绑定自己的值，
 * 可以将ThreadLocal类形象的比喻成存放数据的盒子，盒子中可以存储每个线程的私有数据。
 *
 * @author Administrator
 * @date 2018/11/9 15:35
 */
public class TheadLocalTest {

    public static ThreadLocal<String> t1 = new ThreadLocal<>();

    public static void main(String[] args) {
        test1();
    }

    private static void test1() {
        if(t1.get() == null){
            System.out.println("为ThreadLocal 存值：");
            t1.set("aaa");
        }else{
            System.out.println("初始化不为空的,初始化值为：" + t1.getClass());
        }

        System.out.println(t1.get());//aaa
    }

    //可以使用initialValue方法位ThreadLocal付初始化值
    static class ThreadLocalExt extends ThreadLocal {

        @Override
        public Object initialValue(){
            return "我是默认值 第一次get不再为null";
        }
    }
}
