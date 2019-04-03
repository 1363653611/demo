package com.zbcn.demo.shiro;

import com.vividsolutions.jts.util.Assert;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.junit.Test;

public class AuthenticatorTest extends BaseTest{

    @Test
    public void testAllSuccessfulStrategyWithSuccess(){
        login("classpath:shiro/ch2/shiro-authenticator-all-success.ini");
        Subject subject = SecurityUtils.getSubject();
        PrincipalCollection principals = subject.getPrincipals();
        Assert.equals(2,principals.asList().size());
    }

    @Test(expected = UnknownAccountException.class)
    public void testAllSuccessfulStrategyWithFail(){
        //todo ini中指定了securityManager.authenticator的authenticationStrategy为org.apache.shiro.authc.pam.AllSuccessfulStrategy，必须全部Realms验证成功
        login("classpath:shiro/ch2/shiro-authenticator-all-fail.ini");
    }


}
