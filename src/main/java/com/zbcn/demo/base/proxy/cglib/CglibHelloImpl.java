package com.zbcn.demo.base.proxy.cglib;

import javax.xml.bind.SchemaOutputResolver;

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

    public int saySeeYou(int name){
        System.out.println("seeyou, 下次见" + name);
        return name;
    }
}
