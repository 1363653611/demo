package com.zbcn.demo.base.proxy.stat;

/**
 * 委托类的具体实现
 */
public class CommissionServiceImpl implements ICommissionService {
    @Override
    public String doSomeThing(String name) {

        System.out.println("委托类做具体事情！" + name);
        return "CommissionServiceImpl" + name;
    }
}
