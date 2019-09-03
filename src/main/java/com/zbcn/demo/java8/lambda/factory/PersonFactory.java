package com.zbcn.demo.java8.lambda.factory;

import com.zbcn.demo.java8.common.bean.Person;

/**
 * 创建Person对象的对象工厂接口
 * @param <P>
 */
public interface PersonFactory<P extends Person> {

    P create(String firstName,String secondName);
}
