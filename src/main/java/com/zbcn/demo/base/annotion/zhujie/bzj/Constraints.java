package com.zbcn.demo.base.annotion.zhujie.bzj;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**        
 * Title: Constraints.java
 * <p>    
 * Description: 约束限制（主键、外键、不能为空等）
 * @author likun       
 * @created 2018-3-30 下午2:24:12
 * @version V1.0
 */ 
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Constraints {
	//判断是否作为主键约束
	boolean primaryKey() default false;
	//是否允许为空
	boolean allowNull() default false;
	//判断是否唯一
	boolean unique() default false;
}
