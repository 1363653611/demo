package com.zbcn.demo.base.proxy.cglib;

import net.sf.cglib.proxy.Callback;
import net.sf.cglib.proxy.CallbackFilter;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.NoOp;
import org.apache.commons.lang.StringUtils;

import java.lang.reflect.Method;

/**
 * 回调方法过滤
 */
public class TargetMethodCallbackFilter implements CallbackFilter {

    /**
     * CGLIB 增强类对象，代理类对象是由 Enhancer 类创建的，
     * Enhancer 是 CGLIB 的字节码增强器，可以很方便的对类进行拓展
     */
    private static final Enhancer enhancer = new Enhancer();

    /**
     * 过滤方法
     * 返回的值为数字，代表了Callback数组中的索引位置，要到用的Callback
     * @param method
     * @return
     */
    @Override
    public int accept(Method method) {
        if(StringUtils.equals(method.getName(),"sayHello")){
            System.out.println("filter sayHello == 0");
            return 0;
        }
        if(StringUtils.equals(method.getName(),"sayBye")){
            System.out.println("filter sayBye == 1");
            return 1;
        }

        if (StringUtils.equals(method.getName(),"saySeeYou")) {
            System.out.println("filter saySeeYou == 2");
            return 2;
        }


        return 0;
    }

    /**
     * 使用动态代理创建一个代理对象 (添加有callbacks)
     * @return
     */
    public Object newCallBackProxyInstance(Class<?> clasz){
        /**
         * 设置产生的代理对象的父类,增强类型
         */
        enhancer.setSuperclass(clasz);

        CallbackFilter callbackFilter = new TargetMethodCallbackFilter();

        /**
         * 三个方法，东一了三个回调拦截：在TargetMethodCallbackFilter.accept  方法中定义拦截的具体方法。
         * (1)callback1：方法拦截器
         * (2)NoOp.INSTANCE：这个NoOp表示no operator，即什么操作也不做，代理类直接调用被代理的方法不进行拦截。
         * (3)FixedValue：表示锁定方法返回值，无论被代理类的方法返回什么值，回调方法都返回固定值。
         */
        CglibInterceptor callback1 = new CglibInterceptor();
        Callback noopCb = NoOp.INSTANCE;
        Callback fixedValue=new TargetResultFixed();
        Callback[] cbarray = new Callback[]{callback1,noopCb,fixedValue};
        enhancer.setCallbacks(cbarray);
        enhancer.setCallbackFilter(callbackFilter);
        /**
         * 使用默认无参数的构造函数创建目标对象,这是一个前提,被代理的类要提供无参构造方法
         */
        return enhancer.create();
    }
}
