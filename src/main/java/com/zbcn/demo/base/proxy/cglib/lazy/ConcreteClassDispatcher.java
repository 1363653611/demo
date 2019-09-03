package com.zbcn.demo.base.proxy.cglib.lazy;

import net.sf.cglib.proxy.Dispatcher;

/**
 *Dispatcher在每次访问延迟加载属性时都会触发代理类回调方法
 */
public class ConcreteClassDispatcher implements Dispatcher {
    @Override
    public Object loadObject() throws Exception {
        System.out.println("before Dispatcher...");
        PropertyBean propertyBean = new PropertyBean();
        propertyBean.setKey("xxx");
        propertyBean.setValue("dispatcher test");
        System.out.println("after Dispatcher...");
        return propertyBean;
    }
}
