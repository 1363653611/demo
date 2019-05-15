package com.zbcn.demo.base.proxy.jdk;

/**
 * JDK 动态代理测试
 */
public class JdkTest {

    public static void main(String[] args) {
        MyInvocationHandler myInvocationHandler = new MyInvocationHandler(new HelloServiceImpl());
        IHelloService proxyInstance = (IHelloService)myInvocationHandler.newProxyInstance();
        proxyInstance.sayHello("张三");
        proxyInstance.sayBye("李四");
    }
}
