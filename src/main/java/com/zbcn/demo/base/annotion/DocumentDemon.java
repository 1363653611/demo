package com.zbcn.demo.base.annotion;

import java.lang.annotation.*;
import java.util.Arrays;
//使用@Inherited后，可以使 @DocumentA 被子类的getAnntions（）方法获取到。
@Inherited
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@interface DocumentA {
}


@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@interface DocumentB {

}

@DocumentA
class A{ }


class B extends A{}

@DocumentB
class C{ }

class D extends C{}

//@Inherited 可以让注解被继承，但这并不是真的继承，
// 只是通过使用@Inherited，可以让子类Class对象使用getAnnotations()获取父类被@Inherited修饰的注解
public class DocumentDemon {


    public static void main(String[] args) {

        A instanceA=new B();

        System.out.println("已使用的@Inherited注解:"+ Arrays.toString(instanceA.getClass().getAnnotations()));

        C d = new D();

        System.out.println("未使用@Inherited 注解：" +Arrays.toString(d.getClass().getAnnotations()));
    }
}


