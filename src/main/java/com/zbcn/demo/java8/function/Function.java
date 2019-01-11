package com.zbcn.demo.java8.function;

/**
 * TODO
 *
 * @author Administrator
 * @date 2019/1/11 17:22
 */
@FunctionalInterface
public interface Function<R,T> {

    R apply(T t);
}
