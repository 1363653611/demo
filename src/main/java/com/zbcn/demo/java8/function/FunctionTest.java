package com.zbcn.demo.java8.function;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * TODO
 *
 * @author Administrator
 * @date 2019/1/11 17:13
 */
public class FunctionTest {


    /**
     * @FunctionalInterface 函数的作用 ：表示这个接口被设计成函数式接口，只能有一个抽象方法，如果你添加多个抽象方法
     * @param list
     * @param consumer
     * @param <T>
     */
    public static <T> void forEach(List<T> list, Consumer<T> consumer){
        list.forEach(e ->{
            consumer.accept(e);
        });

    }
    private static void consumeTest() {
        List<String> list = Arrays.asList("A", "B", "C", "D");
        forEach(list,str->System.out.println(str));
        forEach(list,System.out::println);
    }

    /**
     * java.util.function.Function<T, R>接口定义了一个叫作apply()的方法，它接受一个泛型T的对象，并返回一个泛型R的对象
     */
    private static void functionTest(){
        List<Book> books = Arrays.asList(
                new Book("张三", 99.00D),
                new Book("李四", 59.00D),
                new Book("王老五", 59.00D)
        );
        List<String> names = mapRight(books, book -> book.getName());
        System.out.println("name：" + StringUtils.join(names,","));
    }

    private static <T,R> List<R> mapRight(List<T> list,Function<R,T> function){
        List<R> objects = new ArrayList<>();
        list.forEach( t->{
            objects.add(function.apply(t));
        });
        return objects;
    }

    public static void main(String[] args){
//        consumeTest();
        functionTest();
    }

    @Data
    @AllArgsConstructor
    static class Book{
        private String name;
        private Double price;
    }

}

