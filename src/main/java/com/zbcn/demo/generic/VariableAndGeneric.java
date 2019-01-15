package com.zbcn.demo.generic;

import lombok.extern.slf4j.Slf4j;

/**
 * 泛型方法与可变参数
 *
 * @author Administrator
 * @date 2019/1/15 16:01
 */
@Slf4j
public class VariableAndGeneric {

    public static <T> void printMsg( T... args){
        for(T t : args){
            log.info("泛型测试:{}","t is " + t);
        }
    }

    public static void main(String[] args) {
        printMsg("111",222,"aaaa","2323.4",55.55);
    }
}
