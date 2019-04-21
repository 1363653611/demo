package com.zbcn.demo.shiro.authorizer;

import org.apache.shiro.authz.Permission;
import org.apache.shiro.authz.permission.PermissionResolver;
import org.apache.shiro.authz.permission.WildcardPermission;

/**
 * BitAndWildPermissionResolver 实现了 PermissionResolver 接口，并根据权限字符串是否以 “+” 开头来解析权限字符串为 BitPermission 或 WildcardPermission
 */
public class BitAndWildPermissionResolver implements PermissionResolver {
    /**
     * Resolves a Permission based on the given String representation.
     *
     * @param permissionString the String representation of a permission.
     * @return A Permission object that can be used internally to determine a subject's permissions.
     * @throws InvalidPermissionStringException if the permission string is not valid for this resolver.
     */
    @Override
    public Permission resolvePermission(String permissionString) {
        if(permissionString.startsWith("+")){
            return new BitPermission(permissionString);
        }
        return new WildcardPermission(permissionString);
    }
}
