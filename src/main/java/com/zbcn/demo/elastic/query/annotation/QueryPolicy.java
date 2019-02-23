package com.zbcn.demo.elastic.query.annotation;

/**
 * Created by kingphang on 16/11/9.
 */
public enum QueryPolicy {

    EQUAL,//等于

    EQUAL_IGNORE_CASE,//忽略大小写相等

    RANGE,//范围判断

    IN,//

    IGNORE,

    FULLTEXT,//搜索

    MATCH,//匹配 和q搜索区分开

    FUZZY,// 模糊

    MISSING,// 不存在

    EXIST,// 存在

    GROUP_BY,//分组

    LIST,//为集合

    AND,

    OR_IN;

}
