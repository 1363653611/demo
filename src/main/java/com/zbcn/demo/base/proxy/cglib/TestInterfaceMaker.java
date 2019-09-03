package com.zbcn.demo.base.proxy.cglib;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.InterfaceMaker;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 接口生成器InterfaceMaker
 * InterfaceMaker会动态生成一个接口，该接口包含指定类定义的所有方法。
 */
public class TestInterfaceMaker {

    public static void main(String[] args) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        InterfaceMaker interfaceMaker = new InterfaceMaker();
        //抽取某个类的方法生成接口方法
        interfaceMaker.add(CglibHelloImpl.class);
        Class<?> aClass = interfaceMaker.create();
        for (Method method : aClass.getMethods()) {
            System.out.println(method.getName());
        }
        Object o = Enhancer.create(Object.class, new Class[]{aClass}, new MethodInterceptor() {
            @Override
            public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
                if (method.getName().equals("sayHello")) {
                    System.out.println("filter sayHello ");
                    return "sayHello";
                }
                if (method.getName().equals("sayBye")) {
                    System.out.println("filter sayBye ");
                    return "sayBye";
                }
                if (method.getName().equals("saySeeYou")) {
                    System.out.println("filter saySeeYou ");
                    return 2000;
                }

                return "default";
            }
        });

       Method targetMethod1=o.getClass().getMethod("saySeeYou",new Class[]{int.class});
        int i=(int)targetMethod1.invoke(o, new Object[]{33});
        Method targetMethod=o.getClass().getMethod("sayHello",new Class[]{String.class});
        System.out.println(targetMethod.invoke(o, new Object[]{"sdfs"}));


    }
}
