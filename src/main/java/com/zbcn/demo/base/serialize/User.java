package com.zbcn.demo.base.serialize;

import java.io.Serializable;

/**
 * @author
 * @create 2018-06-11 15:05
 **/
public class User implements Serializable {

    private static final long serialVersionUID =8294180014912103005L;

    private String username;

    private transient String pwd;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }
}
