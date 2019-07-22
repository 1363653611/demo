package com.zbcn.demo.base.annotion.db;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class DBCreator {

    public static String getTableSql(String className) {
        Class<?> cl = null;
        try {
            cl = Class.forName(className);
            DBTable dbTable = cl.getAnnotation(DBTable.class);
            if (dbTable == null) {
                System.out.println(
                        "No DBTable annotations in class " + className);
                return null;
            }
            String tableName = dbTable.name();

            if (tableName.length() < 1) {
                tableName = cl.getName().toUpperCase();
            }
            List<String> columnDefs = new ArrayList<String>();
            //获取属性
            for (Field field : cl.getDeclaredFields()) {
                String columnName = null;
                Annotation[] annotations = field.getDeclaredAnnotations();
                if (annotations.length < 1) {
                    continue;
                }

                //判断注解类型
                Annotation anns = annotations[0];
                if (anns instanceof SQLInteger) {
                    SQLInteger sInt = (SQLInteger) anns;

                    if (sInt.name().length() < 1) {
                        columnName = field.getName().toUpperCase();
                    } else {
                        columnName = sInt.name();
                    }
                    columnDefs.add(columnName + " INT" +
                            getConstraints(sInt.constraint()));
                }

                if (anns instanceof SQLString) {
                    SQLString sStr = (SQLString) anns;

                    if (sStr.name().length() < 1) {
                        columnName = field.getName().toUpperCase();
                    } else {
                        columnName = sStr.name();
                    }
                    columnDefs.add(columnName + "  VARCHAR (" + sStr.value() + ")" + getConstraints(sStr.constraint()));
                }
            }

            StringBuilder stringBuilder = new StringBuilder("CREATE TABLE " + tableName + "(");

            for (String colum : columnDefs) {
                stringBuilder.append(" " + colum + ",");
            }

            String tableCreater = stringBuilder.substring(0, stringBuilder.length() - 1) + ");";
            return tableCreater;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return null;

    }

    /**
     * 判断该字段是否有其他约束
     * @param con
     * @return
     */
    private static String getConstraints(Constraints con) {
        String constraints = "";
        if(!con.allowNull())
            constraints += " NOT NULL";
        if(con.primaryKey())
            constraints += " PRIMARY KEY";
        if(con.unique())
            constraints += " UNIQUE";
        return constraints;
    }


    public static void main(String[] args) {
        String tableSql = DBCreator.getTableSql("com.zbcn.demo.base.annotion.db.Member");

        System.out.println(tableSql);
    }
}
