package com.zbcn.demo.base.annotion.zhujie.bzj;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**        
 * Title: SQLInteger.java
 * <p>    
 * Description: 注解Integer类型字段
 * @author likun       
 * @created 2018-3-30 下午2:21:08
 * @version V1.0
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface SQLInteger {
	//该字段对应数据库表名
	String name() default "";
	//嵌套注解
	Constraints constraint() default @Constraints;
}
