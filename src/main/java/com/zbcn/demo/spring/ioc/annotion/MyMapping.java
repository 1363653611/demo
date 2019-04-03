package com.zbcn.demo.spring.ioc.annotion;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MyMapping {

    String value() default "";
}
