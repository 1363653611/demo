package com.zbcn.demo.base.annotion.zhujie.bzj;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**        
 * Title: SQLString.java
 * <p>    
 * Description: 注解String类型的字段
 * @author likun       
 * @created 2018-3-30 下午2:29:16
 * @version V1.0
 */ 
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface SQLString {
	//对应表的列明
	String name() default "";
	//列类分配的长度，如varchar（30）的30
	int value() default 0;
	Constraints constraints() default @Constraints;
}
