package com.zbcn.demo.shiro;

import com.sun.org.omg.CORBA.InitializerSeqHelper;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LoginLogoutTestTest {


    @Test
    public void testHelloWord(){
        //获取serurityManager 工厂，此处使用.ini文件初始化
        //Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro/ch2/shiro.ini");
        Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro/ch2/shiro-realm.ini");
        //2、得到SecurityManager实例 并绑定给SecurityUtils
        SecurityManager instance = factory.getInstance();
        SecurityUtils.setSecurityManager(instance);
        //3、得到Subject及创建用户名/密码身份验证Token（即用户身份/凭证）
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken("zhang", "123");
        //4、登录，即身份验证
        try {
            subject.login(token);
        } catch (AuthenticationException e) {
            //AuthenticationException的子类:
            // 登陆失败：DisabledAccountException（禁用的帐号）、LockedAccountException（锁定的帐号）、 UnknownAccountException（错误的帐号）、ExcessiveAttemptsException（登录失败次数过多）、IncorrectCredentialsException （错误的凭证）、 ExpiredCredentialsException（过期的凭证）
            e.printStackTrace();
        }
        //5、登录，即身份验证
        Assert.assertTrue(subject.isAuthenticated());
        subject.logout();
    }

    /**
     * 多realm
     */
    @Test
    public void testMultiAuthorization(){
        //获取securityManager工厂
        Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro/ch2/shiro-multi-realm.ini");
        SecurityManager instance = factory.getInstance();
        SecurityUtils.setSecurityManager(instance);
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken("wang", "123");

        try {
            subject.login(token);
        } catch (AuthenticationException e) {
            //登陆失败
            e.printStackTrace();
        }
        Assert.assertTrue(subject.isAuthenticated());
        subject.logout();
    }

    @Test
    public void testJdbcRealm(){

        Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro/ch2/shiro-jdbc-realm.ini");
        SecurityManager instance = factory.getInstance();
        SecurityUtils.setSecurityManager(instance);
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken("zhang", "123");

        try {
            subject.login(token);
        } catch (AuthenticationException e) {
            e.printStackTrace();
        }
        Assert.assertTrue(subject.isAuthenticated());
        subject.logout();
    }


}