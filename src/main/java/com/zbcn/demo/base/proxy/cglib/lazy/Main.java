package com.zbcn.demo.base.proxy.cglib.lazy;

public class Main {

    public static void main(String[] args) {
        LazyBean lazyBean = new LazyBean("张三", 20);
        //调用git** 方法只加载一次
        PropertyBean propertyBean = lazyBean.getPropertyBean();

        System.out.println("lazyload propertyBean->key :" + propertyBean.getKey());
        System.out.println("lazyload propertyBean->value :" + propertyBean.getValue());
        //每次调用get** 方法 都重新加载
        PropertyBean propertyBean11 = lazyBean.getPropertyBeanDispatcher();
        PropertyBean propertyBean12 = lazyBean.getPropertyBeanDispatcher();
        System.out.println("dispatcher load key： " + propertyBean11.getKey());
        System.out.println("dispatcher load： value" + propertyBean11.getValue());
    }
}
