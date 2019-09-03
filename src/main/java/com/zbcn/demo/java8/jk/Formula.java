package com.zbcn.demo.java8.jk;

/**
 * Java 8允许我们给接口添加一个非抽象的方法实现，只需要使用 default关键字即可，这个特征又叫做扩展方法
 */
public interface Formula {

    double calculate(int a);

    /**
     * 默认方法
     * @param a 参数
     * @return 平方值
     */
    default double sqrt(int a){
        return Math.sqrt(a);
    }
}
