package com.zbcn.demo.base.proxy.stat;

public class StaticProxy implements ICommissionService {

    private ICommissionService comminissionService;

    public StaticProxy(ICommissionService comminissionService) {
        this.comminissionService = comminissionService;
    }

    @Override
    public String doSomeThing(String name) {
        System.out.println("方法加强！！！");
        return comminissionService.doSomeThing(name);
    }
}
