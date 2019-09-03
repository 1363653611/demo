package com.zbcn.demo.java8.other;

import com.alibaba.fastjson.JSON;
import com.zbcn.demo.java8.common.bean.Person;

import java.util.function.Supplier;

/**
 * Supplier 接口返回一个任意范型的值，和Function接口不同的是该接口没有任何参数
 */
public class SupplierTest {

    public static void main(String[] args) {
        Supplier<Person> supplier = Person::new;
        System.out.println(JSON.toJSONString(supplier.get()));
    }
}
