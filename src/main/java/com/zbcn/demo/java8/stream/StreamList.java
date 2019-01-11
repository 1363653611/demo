package com.zbcn.demo.java8.stream;

import com.alibaba.fastjson.JSON;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 *
 * @author likun
 * @date 2019/1/11 10:08
 */
public class StreamList {

    private static List<String> getTestDemon(){
        List<String> strings = new ArrayList<>();
        strings.add("e");
        strings.add("f");
        strings.add("d");
        strings.add("e");
        strings.add("a");
        strings.add("b");
        strings.add("c");
        strings.add("d");
        return strings;
    }

    /**
     * sort
     */
    private static void testSort(){
        List<String> testDemon = getTestDemon();
        Collections.sort(testDemon, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.compareTo(o2);
            }
        });
        System.out.println(testDemon);
    }

    private static void testSortJava8(){
        List<String> e = getTestDemon().stream()
                                       .filter(s -> !StringUtils.equals("e", s)).sorted()
                                       .collect(Collectors.toList());
        System.out.println(e);
    }

    /**
     * 它的作用就是把 input Stream 的每一个元素，映射成 output Stream 的另外一个元素。
     */
    private static void map(){
        List<String> collect = getTestDemon().stream().map(String::toUpperCase).collect(Collectors.toList());
        System.out.println(collect);
    }

    /**
     * flatMap 把 input Stream 中的层级结构扁平化，就是将最底层元素抽出来放到一起，
     * 最终 output 的新 Stream 里面已经没有 List 了，都是直接的数字
     */
    private static void faltMap(){

        Stream<List<Integer>> listStream = Stream.of(
                Arrays.asList(1),
                Arrays.asList(2, 3),
                Arrays.asList(4, 5, 6));
        Stream<Integer> integerStream = listStream.flatMap(child -> child.stream());
        System.out.println(Arrays.toString(integerStream.toArray()));

    }

    /**
     * filter 对原始 Stream 进行某项测试，通过测试的元素被留下来生成一个新 Stream。
     */
    private static void filter(){

        Integer[] sixNums = {1, 2, 3, 4, 5, 6};
        List<Integer> collect = Stream.of(sixNums).filter(n -> n % 2 == 0).collect(Collectors.toList());
        System.out.println(collect);

    }

    /**
     *forEach 方法接收一个 Lambda 表达式，然后在 Stream 的每一个元素上执行该表达式。
     * forEach 是 terminal 操作，因此它执行后，Stream 的元素就被“消费”掉了，你无法对一个 Stream 进行两次 terminal 运算。
     * 可以使用peek方法达到次效果
     */
    private static void forEach(){
        getTestDemon().stream().forEach(t -> {
            System.out.println(t);
        });
    }

    /**
     * peek 对每个元素执行操作并返回一个新的 Stream/
     * peek可以对一个 Stream 进行两次 terminal 运算
     */
    private static void peek(){
        List<String> collect = Stream.of("one", "two", "three", "four")
                                     .filter(e -> e.length() > 3)
                                     .peek(e -> System.out.println(e))
                                     .map(String::toUpperCase)
                                     .peek(e -> System.out.println(e))
                                     .collect(Collectors.toList());
        System.out.println(collect);
    }

    /**
     * Optional 这也是一个模仿 Scala 语言中的概念，作为一个容器，它可能含有某值，或者不包含。
     * 使用它的目的是尽可能避免 NullPointerException。
     */
    private static void optional(){

        String test = "1232133";
        String test1 = null;
        Optional.ofNullable(test).ifPresent(System.out::println);
    }


    /**
     * 这是一个 termimal 兼 short-circuiting 操作,它总是返回 Stream 的第一个元素，或者空
     */
    private static void findFirst(){
        getTestDemon().stream().findFirst().ifPresent(System.out::println);
    }

    /**
     * 这个方法的主要作用是把 Stream 元素组合起来。它提供一个起始值（种子），
     * 然后依照运算规则（BinaryOperator），和前面 Stream 的第一个、第二个、第 n 个元素组合。
     * 从这个意义上说，字符串拼接、数值的 sum、min、max、average 都是特殊的 reduce
     */
    private static void reduce(){
        //有起始值
        String concat = Stream.of("A", "B", "C", "D").reduce("", String::concat);
        System.out.println(concat);
        // 求和，sumValue = 10, 有起始值
        int sumValue = Stream.of(1, 2, 3, 4).reduce(0, Integer::sum);
        System.out.println(sumValue);
        // 求和，sumValue = 10, 无起始值
        //没有起始值的 reduce()，由于可能没有足够的元素，返回的是 Optional，请留意这个区别
        sumValue = Stream.of(1, 2, 3, 4).filter(e -> e==5).reduce(Integer::sum).get();
        System.out.println(sumValue);
    }

    private static void testLimtAndSkip(){
        List<Dic> dics = new ArrayList<>();
        for (int i = 1; i <= 10000; i++) {
            dics.add(new Dic(Integer.toString(i),"name"+ i));
        }
        Stream.of(dics)
            .limit(10).skip(3)
            .forEach(d -> System.out.println(JSON.toJSONString(d)));

        //comment: peek支持有问题哈
        getTestDemon().stream().peek(d ->{
            System.out.println("test:"+d.getBytes());
        });

    }

    //Match
    //
    //Stream 有三个 match 方法，从语义上说：
    //
    //allMatch：Stream 中全部元素符合传入的 predicate，返回 true
    //anyMatch：Stream 中只要有一个元素符合传入的 predicate，返回 true
    //noneMatch：Stream 中没有一个元素符合传入的 predicate，返回 true

    private static void match(){
        boolean a = getTestDemon().stream().anyMatch(d -> StringUtils.equals("a", d));
        System.out.println(a);
    }



    /**
     * @param args
     */
    public static void main(String[] args) {
//        testSort();
//        testSortJava8();
//        map();
//        faltMap();
//        filter();
//        forEach();
//        peek();
//        optional();
//        findFirst();
//        reduce();
//        testLimtAndSkip();
        match();
    }

    @Data
    @AllArgsConstructor
    public static class Dic {
        String key;
        String value;
    }
}
