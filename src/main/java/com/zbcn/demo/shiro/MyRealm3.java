package com.zbcn.demo.shiro;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.realm.Realm;

public class MyRealm3  implements Realm{
    /**
     * Returns the (application-unique) name assigned to this <code>Realm</code>. All realms configured for a single
     * application must have a unique name.
     *
     * @return the (application-unique) name assigned to this <code>Realm</code>.
     */
    @Override
    public String getName() {
        return "MyRealm3";
    }

    /**
     * Returns <tt>true</tt> if this realm wishes to authenticate the Subject represented by the given
     * {@link AuthenticationToken AuthenticationToken} instance, <tt>false</tt> otherwise.
     * <p>
     * <p>If this method returns <tt>false</tt>, it will not be called to authenticate the Subject represented by
     * the token - more specifically, a <tt>false</tt> return value means this Realm instance's
     * {@link #getAuthenticationInfo} method will not be invoked for that token.
     *
     * @param token the AuthenticationToken submitted for the authentication attempt
     * @return <tt>true</tt> if this realm can/will authenticate Subjects represented by specified token,
     * <tt>false</tt> otherwise.
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof UsernamePasswordToken;
    }

    /**
     * Returns an account's authentication-specific information for the specified <tt>token</tt>,
     * or <tt>null</tt> if no account could be found based on the <tt>token</tt>.
     * <p>
     * <p>This method effectively represents a login attempt for the corresponding user with the underlying EIS datasource.
     * Most implementations merely just need to lookup and return the account data only (as the method name implies)
     * and let Shiro do the rest, but implementations may of course perform eis specific login operations if so
     * desired.
     *
     * @param token the application-specific representation of an account principal and credentials.
     * @return the authentication information for the account associated with the specified <tt>token</tt>,
     * or <tt>null</tt> if no account could be found.
     * @throws AuthenticationException if there is an error obtaining or constructing an AuthenticationInfo object based on the
     *                                 specified <tt>token</tt> or implementation-specific login behavior fails.
     */
    @Override
    public AuthenticationInfo getAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String principal = (String)token.getPrincipal();
        String credentials =new String((char[])token.getCredentials());
        if(!StringUtils.equals("zhang",principal)){
            throw new UnknownAccountException("未知用戶");
        }
        if(!StringUtils.equals("123",credentials)){
            throw new IncorrectCredentialsException("不正确的用戶密碼");
        }
        return new SimpleAuthenticationInfo(principal + "@163.com", credentials, getName());
    }
}
