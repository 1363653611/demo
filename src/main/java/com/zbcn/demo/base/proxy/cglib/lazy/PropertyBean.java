package com.zbcn.demo.base.proxy.cglib.lazy;

import lombok.Data;

@Data
public class PropertyBean {

    private String key;

    private Object value;


    @Override
    public String toString() {
        return "PropertyBean [key=" + key + ", value=" + value + "]" +getClass();
    }

}
