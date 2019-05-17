package com.zbcn.demo.base.annotion;

import java.lang.annotation.*;

/**
 * 自定义注解：
 * 对于非基本类型的元素，无论是在源代码中声明，还是在注解接口中定义默认值，都不能以null作为值
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AnnotationElementDemo {

    //所有的基本类型（int,float,boolean,byte,double,char,long,short）
    /**
     * 枚举类型
     * @return
     */
    Status status() default Status.FIXED;

    /**
     * boolean 类型
     */
    boolean showSupport() default true;

    /**
     * String 类型
     * @return
     */
    String name() default  "";

    /**
     * class 类型
     * @return
     */
    Class<?> clasz() default Object.class;

    /**
     * 注解类型
     * @return
     */
    Reference reference() default @Reference(next = true);

    /**
     * 数组类型
     * @return
     */
    long[] value() default {1L,2L};

    enum Status {
        FIXED,NORMAL
    }

}

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@interface Reference {
    boolean next() default  false;
}
