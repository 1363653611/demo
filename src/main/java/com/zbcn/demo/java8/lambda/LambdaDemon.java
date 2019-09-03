package com.zbcn.demo.java8.lambda;

import com.alibaba.fastjson.JSON;
import com.zbcn.demo.java8.common.bean.Person;
import com.zbcn.demo.java8.lambda.factory.PersonFactory;
import org.apache.poi.ss.formula.functions.Oct2Dec;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * lambda 相关测试类
 */
public class LambdaDemon {

    public static void main(String[] args) {
        //sortTest();
        testFactory();
    }

    public static void sortTest(){

        List<String> lists = Arrays.asList("peter", "anna", "mike", "xenia");
//        Collections.sort(lists, new Comparator<String>() {
//            @Override
//            public int compare(String o1, String o2) {
//                return o1.compareTo(o2);
//            }
//        });

        //Collections.sort(lists, (String o1,String o2) -> o1.compareTo(o2));
        Collections.sort(lists, Comparator.naturalOrder());
        System.out.println(JSON.toJSONString(lists));
    }

    private static void testFactory(){
        //使用构造函数引用来将他们关联起来，而不是实现一个完整的工厂
        //使用 Person::new 来获取Person类构造函数的引用，Java编译器会自动根据PersonFactory.create方法的签名来选择合适的构造函数
        PersonFactory<Person> person = Person::new;
        Person person1 = person.create("张", "三");
        System.out.println(JSON.toJSONString(person1));
    }
}
