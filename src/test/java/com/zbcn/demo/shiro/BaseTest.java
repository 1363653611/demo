package com.zbcn.demo.shiro;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.apache.shiro.util.ThreadContext;
import org.apache.tomcat.jni.Thread;
import org.junit.After;

public class BaseTest {

    public void login(String configFile){

        Factory<SecurityManager> factory = new IniSecurityManagerFactory(configFile);
        SecurityManager instance = factory.getInstance();

        SecurityUtils.setSecurityManager(instance);
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken("zhang", "123");

        try {
            subject.login(token);
        } catch (AuthenticationException e) {

            e.printStackTrace();
            throw e;
        }
    }

    public void login(String configFile,String username,String password){
        Factory<SecurityManager> factory = new IniSecurityManagerFactory(configFile);
        SecurityManager instance = factory.getInstance();
        
        SecurityUtils.setSecurityManager(instance);

        Subject subject = SecurityUtils.getSubject();

        UsernamePasswordToken token = new UsernamePasswordToken(username, password);

        subject.login(token);
    }

    public Subject getSubject(){
       return SecurityUtils.getSubject();
    }

    @After
    public void tearDown() throws Exception{
        //退出时请解除绑定Subject到线程 否则对下次测试造成影响
        ThreadContext.unbindSubject();
    }
}
