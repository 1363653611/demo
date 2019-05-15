package com.zbcn.demo.base.proxy.stat;

/**
 * 测试静态代理类
 */
public class StaticTest {

    public static void main(String[] args) {
        StaticProxy staticProxy = new StaticProxy(new CommissionServiceImpl());
        staticProxy.doSomeThing("张三");
    }
}
