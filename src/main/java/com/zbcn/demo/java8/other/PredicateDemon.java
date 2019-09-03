package com.zbcn.demo.java8.other;

import java.util.function.Predicate;

/**
 * 接口只有一个参数，返回boolean类型
 */
public class PredicateDemon {
    public static void main(String[] args) {

        Predicate<String> s = (a) -> a.length()> 0;
        String b = "aaabbbbccc";
        System.out.println(s.test(b));
    }
}
