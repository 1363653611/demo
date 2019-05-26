package com.zbcn.demo.base.annotion.zhujie;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.zbcn.zhujie.bzj.Constraints;
import com.zbcn.zhujie.bzj.DBTable;
import com.zbcn.zhujie.bzj.SQLInteger;
import com.zbcn.zhujie.bzj.SQLString;

/**        
 * Title: TableCreator.java
 * <p>    
 * Description: 创建表格的工具类
 * @author likun       
 * @created 2018-3-30 下午5:01:46
 * @version V1.0
 */ 
public class TableCreator {
	/**     
	 * Description: 创建sql语句
	 * <p>   
	 * @param className
	 * @return
	 * @throws ClassNotFoundException     
	 * @author likun      
	 * @created 2018-3-30 下午4:46:42      
	 */
	public static String createTableSql(String className) throws ClassNotFoundException {
		Class<?> c1 = Class.forName(className);
		DBTable dbTable = c1.getAnnotation(DBTable.class);
		if(dbTable == null){
			System.out.println("No DBTable annotation in class");
			return null;
		}
		String tableName = dbTable.name();
		
		if(StringUtils.isBlank(tableName)){
			tableName = c1.getName().toUpperCase();
		}
		
		List<String> columnDefs = new ArrayList<>();
		Field[] fields = c1.getDeclaredFields();
		for (Field field : fields) {
			String columnName = null;
			Annotation[] anns = field.getDeclaredAnnotations();
			if(anns.length<1){
				continue;
			}else{
				if(anns[0] instanceof SQLInteger){
					SQLInteger sInt = (SQLInteger)anns[0];
					if(StringUtils.isBlank(sInt.name())){
						columnName = field.getName().toUpperCase();
					}else{
						columnName = sInt.name();
					}
					columnDefs.add(columnName + "INT" + getConstraints(sInt.constraint()));
				}
				if(anns[0] instanceof SQLString){
					SQLString sString = (SQLString)anns[0];
					if(StringUtils.isBlank(sString.name())){
						columnName = field.getName().toUpperCase();
					}else{
						columnName = sString.name();
					}
					columnDefs.add(columnName + " VARCHAR(" +
			                sString.value() + ")" +
			                getConstraints(sString.constraints()));
				}
			}
		}
		
		StringBuilder createCommand = new StringBuilder("CREATE TABLE " + tableName + "(");
		for (String columnDef  : columnDefs) {
			createCommand.append("\n    " + columnDef + ",");
		}
		String tableCreate = createCommand.substring(
	            0, createCommand.length() - 1) + ");";
		return tableCreate;
	}
	
	/**     
	 * Description: 判断改字段有没有其他约束
	 * <p>   
	 * @param con
	 * @return     
	 * @author likun      
	 * @created 2018-3-30 下午4:46:19      
	 */
	private static String getConstraints(Constraints con){
		String constraints = StringUtils.EMPTY;
		if(!con.allowNull()){
			constraints += "NOT NULL";
		}
		if(con.primaryKey()){
			constraints += " PRIMARY KEY";
		}
		if(con.unique()){
			constraints += " UNIQUE";
		}
		return constraints;
		
	}
	
	public static void main(String[] args) throws ClassNotFoundException{
		String className = "com.thunisoft.zj.Member";
		System.out.println("Table Creation SQL for " +
	              className + " is :\n" + createTableSql(className));
	}
	
}
