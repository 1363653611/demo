package com.zbcn.demo.spring.ioc.bean;

import com.zbcn.demo.spring.ioc.annotion.MyAutowired;
import com.zbcn.demo.spring.ioc.annotion.MyService;

@MyService(value = "test")
public class LoginServiceImpl implements LoginService {

    @MyAutowired
    private LoginMapping loginMapping;

    @Override
    public String login() {
        return loginMapping.login();
    }
}
