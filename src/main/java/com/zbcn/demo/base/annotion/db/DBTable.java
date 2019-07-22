package com.zbcn.demo.base.annotion.db;


import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DBTable {

    String name() default "";
}
