package com.zbcn.demo.shiro;

import com.vividsolutions.jts.util.Assert;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.subject.Subject;
import org.junit.Test;

public class PermissionTest extends BaseTest {

    @Test
    public void isPermissioned(){
        login("classpath:shiro/ch2/shiro-permission.ini","zhang","123");
        Subject subject = getSubject();
        Assert.isTrue(subject.isPermitted("user:create"));

        Assert.isTrue(subject.isPermittedAll("user:create","user:delete"));

        Assert.isTrue(!subject.isPermitted("user:view"),"测试权限");
    }

    @Test(expected = UnauthorizedException.class)
    public void testCheckPermission(){
        login("classpath:shiro/ch2/shiro-permission.ini","zhang","123");
        Subject subject = getSubject();
        subject.checkPermission("user:create");
        subject.checkPermissions("user:delete", "user:update");
        subject.checkPermission("user:view");

    }
}
