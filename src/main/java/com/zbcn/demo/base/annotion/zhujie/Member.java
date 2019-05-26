package com.zbcn.demo.base.annotion.zhujie;

import com.zbcn.zhujie.bzj.Constraints;
import com.zbcn.zhujie.bzj.DBTable;
import com.zbcn.zhujie.bzj.SQLInteger;
import com.zbcn.zhujie.bzj.SQLString;

/**        
 * Title: Member.java
 * <p>    
 * Description: 数据库表Member对应实例类bean
 * @author likun       
 * @created 2018-3-30 下午2:35:18
 * @version V1.0
 */ 
@DBTable(name="MEMBER")
public class Member {
	//主键
	@SQLString(name="ID",value=50,constraints = @Constraints(primaryKey=true))
	private String id;
	@SQLString(name="NAME",value=30)
	private String name;
	
	@SQLInteger(name="AGE")
	private int age;
	
	@SQLString(name="DESCRIPTION",value=200,constraints=@Constraints(allowNull = true))
	private String description;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	
}
