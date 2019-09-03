package com.zbcn.demo.java8.jk;

public class SubFormulaTest {

    /**
     * ormula接口在拥有calculate方法之外同时还定义了sqrt方法，实现了Formula接口的子类只需要实现一个calculate方法，
     * 默认方法sqrt将在子类上可以直接使用
     * @param args
     */
    public static void main(String[] args) {

        Formula formula = new Formula() {

            @Override
            public double calculate(int a) {
                return sqrt(a);
            }
        };
        System.out.println(formula.calculate(11));
        System.out.println(formula.sqrt(11));
    }
}
