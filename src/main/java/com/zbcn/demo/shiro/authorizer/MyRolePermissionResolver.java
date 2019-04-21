package com.zbcn.demo.shiro.authorizer;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.Permission;
import org.apache.shiro.authz.permission.RolePermissionResolver;
import org.apache.shiro.authz.permission.WildcardPermission;

import java.util.Arrays;
import java.util.Collection;

/**
 *RolePermissionResolver 用于根据角色字符串来解析得到权限集合
 */
public class MyRolePermissionResolver implements RolePermissionResolver {
    /**
     * Resolves a Collection of Permissions based on the given String representation.
     *
     * @param roleString the String representation of a role name to resolve.
     * @return a Collection of Permissions based on the given String representation.
     */
    @Override
    public Collection<Permission> resolvePermissionsInRole(String roleString) {
        //此处的实现很简单，如果用户拥有 role1，那么就返回一个 “menu:*” 的权限
        if(StringUtils.equals("role1",roleString)){
            return Arrays.asList(new WildcardPermission("menu:*"));
        }
        return null;
    }
}
