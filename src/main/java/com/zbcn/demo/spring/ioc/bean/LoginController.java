package com.zbcn.demo.spring.ioc.bean;

import com.zbcn.demo.spring.ioc.annotion.MyAutowired;
import com.zbcn.demo.spring.ioc.annotion.MyController;
import com.zbcn.demo.spring.ioc.annotion.Value;

@MyController
public class LoginController {

    @Value(value = "ioc.scan.pathTest")
    private String test;

    @MyAutowired(value = "test")
    private LoginService loginService;

    public String login() {
        return loginService.login();
    }

}