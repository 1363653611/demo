package com.zbcn.demo.base.proxy.cglib;

public class CGLIBTest {

    public static void main(String[] args) {

        //testInterCeptor();
        //
        testFilter();
    }

    /**
     * 测试拦截器
     */
    private static void testInterCeptor(){
        CglibInterceptor cglibInterceptor = new CglibInterceptor();
        CglibHelloImpl cglibHello = (CglibHelloImpl)cglibInterceptor.newProxyInstance(CglibHelloImpl.class);
        cglibHello.sayHello("张三");
        cglibHello.sayBye("李四");
    }

    /**
     * 测试过去器
     */
    private static void testFilter(){
        TargetMethodCallbackFilter filter =  new TargetMethodCallbackFilter();

        CglibHelloImpl cglibHello2 = (CglibHelloImpl)filter.newCallBackProxyInstance(CglibHelloImpl.class);

        cglibHello2.sayHello("张三");
        cglibHello2.sayBye("李四");
        int i = cglibHello2.saySeeYou(12);
        System.out.println(i);
    }
}
