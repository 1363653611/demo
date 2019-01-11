package com.zbcn.demo.java8.option;

import java.util.Optional;

/**
 * TODO
 *
 * @author zbcn
 * @date 2019/1/11 16:39
 */
public class OptionalTest {

    public static void main(String[] args) {
        //返回空的Optional对象
        Optional<Object> empty = Optional.empty();
        System.out.println(empty.isPresent());
//        System.out.println(empty.get());
        String str = "dsafasfa";
        Optional<String> str1 = Optional.of(str);
        if(str1.isPresent()){
            System.out.println(str1.get());
        }
        Optional<Object> o = Optional.ofNullable(null);
        if(o.isPresent()){
            System.out.println(o.get());
        }
    }
}
