package com.zbcn.demo.base.reflect;

import lombok.Data;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@Data
public class Person {
    private String name;
    private int age;
    private String msg="hello wrold";

    public void fun() {
        System.out.println("fun");
    }

    public void fun(String name,int age) {
        System.out.println("我叫"+name+",今年"+age+"岁");
    }


    public static void main(String[] args) {
        try {
            Class<?> aClass = Class.forName("com.zbcn.demo.base.reflect.Person");

            Object o = aClass.newInstance();
            Method fun = aClass.getMethod("fun", String.class, int.class);
            //方法的调用
            Object ceshi = fun.invoke(o, "ceshi", 10);

            Method[] methods = aClass.getMethods();
            for (Method method:methods) {
                System.out.println(method.getName());
            }

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

    }
}
