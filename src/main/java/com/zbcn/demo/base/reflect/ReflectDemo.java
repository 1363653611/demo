package com.zbcn.demo.base.reflect;

public class ReflectDemo {

    public static void main(String[] args) {
        Class<ReflectDemo> reflectDemoClass = ReflectDemo.class;
        System.out.println(reflectDemoClass.getName());

        ReflectDemo reflectDemo = new ReflectDemo();
        System.out.println(reflectDemo.getClass().getName());
        Class<?> aClass = null;
        try {
            aClass = Class.forName("com.zbcn.demo.base.reflect.ReflectDemo");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println(aClass.getName());
    }
}
