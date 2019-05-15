package com.zbcn.demo.base.proxy.jdk;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 实际的中间类，完成具体的方法的加强
 */
public class MyInvocationHandler implements InvocationHandler {

    /**
     * 委托类
     */
    private Object obj;

    public MyInvocationHandler(Object obj) {
        this.obj = obj;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        System.out.println("方法加强！！！");
        Object invoke = method.invoke(obj, args);
        System.out.println("方法加强后。。。。。");
        return invoke;
    }

    /**
     * 生成代理类
     * @return
     */
    public Object newProxyInstance(){
        //指定代理对象的类加载器
        ClassLoader classLoader = obj.getClass().getClassLoader();
        //代理类还要实现的接口
        Class<?>[] interfaces = obj.getClass().getInterfaces();
        //方法调用的实际处理者 MyInvocationHandler
        return Proxy.newProxyInstance(classLoader, interfaces, this);

    }


}
