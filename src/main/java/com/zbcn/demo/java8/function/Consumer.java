package com.zbcn.demo.java8.function;

/**
 * TODO
 *
 * @author Administrator
 * @date 2019/1/11 17:13
 */
@FunctionalInterface
public interface Consumer<T> {

    void accept(T a);
}
