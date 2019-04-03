package com.zbcn.demo.shiro;

import com.vividsolutions.jts.util.Assert;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.subject.Subject;
import org.junit.Test;

import java.util.Arrays;

public class RoleTest extends BaseTest{


    @Test
    public void testRoles(){
        login("classpath:shiro/ch2/shiro-role.ini", "zhang", "123");
        Subject subject = getSubject();
        Assert.isTrue(subject.hasRole("role1"));

        Assert.isTrue(subject.hasAllRoles(Arrays.asList("role1","role2","role3")));

        boolean[] result = subject.hasRoles(Arrays.asList("role1", "role2", "role3","role4"));

        Assert.equals(true, result[0]);
        Assert.equals(true, result[1]);
        Assert.equals(true, result[2]);
        Assert.equals(false, result[3]);
    }

    @Test(expected = UnauthorizedException.class)
    public void testCheckRole(){
        login("classpath:shiro/ch2/shiro-role.ini", "zhang", "123");
        Subject subject = getSubject();
        subject.checkRole("role1");
        subject.checkRoles(Arrays.asList("role1","role4"));
    }


}
