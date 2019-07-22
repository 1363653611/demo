package com.zbcn.demo.base.annotion.db;

import java.lang.annotation.*;

/**
 * 约束注解
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Constraints {

    boolean primaryKey() default false;

    boolean allowNull() default  false;

    boolean unique() default  false;


}
