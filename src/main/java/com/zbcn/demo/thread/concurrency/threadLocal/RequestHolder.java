package com.zbcn.demo.thread.concurrency.threadLocal;

/**
 * @Description:
 * @Auther: zbcn
 * @Date: 2/27/19 17:54
 */
public class RequestHolder {

    public final static ThreadLocal<Long> requestHolder = new ThreadLocal<>();

    public static void add(Long value){
        requestHolder.set(value);
    }

    public static Long get(){
        return requestHolder.get();
    }

    public static void remove(){
        requestHolder.remove();
    }
}
