package com.zbcn.demo.spring.ioc;

import com.zbcn.demo.spring.ioc.bean.LoginController;

public class PlatformApplication {

    public static void main(String[] args) throws Exception {
        // 从容器中获取对象(自动首字母小写)
        ClassLoadManager applicationContext = new ClassLoadManager().buildClass();
        LoginController loginController = (LoginController) applicationContext.getIocBean("LoginController");
        String login = loginController.login();
        System.out.println(login);
    }

}
