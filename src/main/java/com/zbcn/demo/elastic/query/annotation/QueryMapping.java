package com.zbcn.demo.elastic.query.annotation;

import java.lang.annotation.*;

/**
 * es查询的mapping
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE,ElementType.FIELD})
@Repeatable(QueryMappingContainer.class)
public @interface QueryMapping {

    /**
     * Query url parameter string of HTTP request
     * 接口的请求字段名
     * @return
     */
    String mappedQueryString() default "";

    /**
     * 字段查询类型 如  判等or   范围
     * @return
     */
    QueryPolicy queryPolicy() default QueryPolicy.EQUAL;

    /**
     * 范围 from后缀
     * @return
     */
    String rangeFromSuffix() default "From";

    /**
     * 范围  to 后缀
     * @return
     */
    String rangeToSuffix() default "To";

    /**
     * 多个字段分隔符
     * @return
     */
    String inQuerySeparator() default ",";

    /**
     * For common Object, use mappedTo to specify which field of the Object will be applied for the query mapping.
     * For example, the exterior dictionary of a vehicle, you can specify mappedTo of "exterior.value" for the "value" field of the Dictionary.
     *
     * @return
     */
    String mappedTo() default "";
}
