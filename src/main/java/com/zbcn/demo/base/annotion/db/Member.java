package com.zbcn.demo.base.annotion.db;

@DBTable(name = "member")
public class Member {

    @SQLString(name = "ID",value = 10, constraint = @Constraints(primaryKey =true,allowNull = true,unique = true))
    private String id;

    @SQLString(name = "NAME" , value = 30)
    private String name;

    @SQLInteger(name = "AGE")
    private int age;

    @SQLString(name = "DESCRIPTION" ,value = 150 , constraint = @Constraints(allowNull = true))
    private String description;
}
