package com.zbcn.demo.thread.concurrency;

import java.lang.annotation.*;

@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.CLASS)
public @interface Recommend {

    String value() default "推荐使用";
}
