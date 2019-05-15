package com.zbcn.demo.base.proxy.cglib;

/**
 * 委托类
 */
public class CglibHelloImpl {


    public String sayHello(String name){
        System.out.println("hello , 你好！"+ name);
        return name;
    }

    public String sayBye(String name){

        System.out.println("bye, 再见！" + name);

        return name;
    }
}
