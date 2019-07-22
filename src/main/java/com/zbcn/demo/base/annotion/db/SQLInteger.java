package com.zbcn.demo.base.annotion.db;

import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SQLInteger {

    String name() default "";

    Constraints constraint() default @Constraints;
}
