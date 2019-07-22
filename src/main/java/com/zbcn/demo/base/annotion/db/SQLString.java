package com.zbcn.demo.base.annotion.db;

import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SQLString {

    String name() default "";

    int value() default 0;

    Constraints constraint() default @Constraints;
}
