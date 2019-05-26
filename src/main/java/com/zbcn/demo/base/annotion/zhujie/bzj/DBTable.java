package com.zbcn.demo.base.annotion.zhujie.bzj;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**        
 * Title: DBTable.java
 * <p>    
 * Description: 表注解
 * @author likun       
 * @created 2018-3-30 下午2:18:04
 * @version V1.0
 */ 
@Target(ElementType.TYPE)//只能用于类上
@Retention(RetentionPolicy.RUNTIME)//表示保存到运行时
public @interface DBTable {
	String name() default "";
}
