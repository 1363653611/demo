package com.zbcn.demo.base.proxy.cglib;

public class CGLIBTest {

    public static void main(String[] args) {
        CglibInterceptor cglibInterceptor = new CglibInterceptor();
        CglibHelloImpl cglibHello = (CglibHelloImpl)cglibInterceptor.newProxyInstance(CglibHelloImpl.class);
        cglibHello.sayHello("张三");
        cglibHello.sayBye("李四");
    }
}
