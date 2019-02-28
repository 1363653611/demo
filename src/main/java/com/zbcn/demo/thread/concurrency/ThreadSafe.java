package com.zbcn.demo.thread.concurrency;

import java.lang.annotation.*;

/**
 * 线程安全
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.CLASS)
public @interface ThreadSafe {
    String value() default "线程安全";
}
