package com.zbcn.demo.base.proxy.jdk;

public class HelloServiceImpl implements IHelloService {
    @Override
    public String sayHello(String name) {

        System.out.println("say hello" + name);
        return name;
    }

    @Override
    public String sayBye(String name) {

        System.out.println("say good_bye" + name);
        return name;
    }
}
