package com.zbcn.demo.base.proxy.cglib;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
/**
 * 增强逻辑的实际方法类，增强逻辑在 重写的 intercept 方法中实现的
 */
public class CglibInterceptor implements MethodInterceptor {
    /**
     * CGLIB 增强类对象，代理类对象是由 Enhancer 类创建的，
     * Enhancer 是 CGLIB 的字节码增强器，可以很方便的对类进行拓展
     */
    private static final Enhancer enhancer = new Enhancer();

    /**
     * 生成代理对象
     *
     * @param o 被代理的对象
     * @param method 代理的方法（原始方法）
     * @param objects 方法参数
     * @param methodProxy CGLIB方法代理对象
     * @return cglib生成用来代替Method对象的一个对象，使用MethodProxy比调用JDK自身的Method直接执行方法效率会有提升
     * @throws Throwable
     */
    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {

        System.out.println("方法调用之前");
        Object o1 = methodProxy.invokeSuper(o, objects);
        System.out.println("方法调用之后");
        return o1;
    }

    /**
     * 使用动态代理创建一个代理对象
     * @return
     */
    public Object newProxyInstance(Class<?> clasz){
        /**
         * 设置产生的代理对象的父类,增强类型
         */
        enhancer.setSuperclass(clasz);

        /**
         * 定义代理逻辑对象为当前对象，要求当前对象实现 MethodInterceptor 接口
         */
        enhancer.setCallback(this);
        /**
         * 使用默认无参数的构造函数创建目标对象,这是一个前提,被代理的类要提供无参构造方法
         */
        return enhancer.create();
    }



}
