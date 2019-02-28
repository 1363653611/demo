package com.zbcn.demo.thread.concurrency;

import java.lang.annotation.*;

@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.CLASS)
public @interface UnRecommend {

    String value() default "不推荐使用";
}
