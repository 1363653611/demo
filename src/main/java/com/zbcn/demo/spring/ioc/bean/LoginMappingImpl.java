package com.zbcn.demo.spring.ioc.bean;

import com.zbcn.demo.spring.ioc.annotion.MyMapping;

@MyMapping
public class LoginMappingImpl implements LoginMapping {

    @Override
    public String login() {
        return "项目启动成功";
    }
}
