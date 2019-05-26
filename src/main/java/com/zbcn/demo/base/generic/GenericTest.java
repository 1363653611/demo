package com.zbcn.demo.base.generic;

import lombok.extern.slf4j.Slf4j;

/**
 * 可以解决当具体类型不确定的时候，这个通配符就是 ?  ；
 * 当操作类型时，不需要使用类型的具体功能时，只使用Object类中的功能。那么可以用 ? 通配符来表未知类型。
 *
 * @author Administrator
 * @date 2019/1/15 15:25
 */
@Slf4j
public class GenericTest {

    public static void showKeyValue(Generic<Number> obj){
        log.info("泛型测试","key value is " + obj.getKey());
    }

    //此处’？’是类型实参，而不是类型形参 ！
    public static void showKeyValue1(Generic<?> obj){
        log.info("泛型测试","key value is " + obj.getKey());
    }

    public static void showKeyValue2(Generic<? extends Number> obj){
            log.info("泛型测试","key value is " + obj.getKey());
    }

    //在泛型方法中添加上下边界限制的时候，必须在权限声明与返回值之间的<T>上添加上下边界，即在泛型声明的时候添加
//public <T> T showKeyName(Generic<T extends Number> container)，编译器会报错："Unexpected bound"
    //泛型的上下边界添加，必须与泛型的声明在一起 。
    public <T extends Number> T showKeyName(Generic<T> container){
        System.out.println("container key :" + container.getKey());
        T test = container.getKey();
        return test;
    }


    /**
     * 同一种泛型可以对应多个版本（因为参数类型是不确定的），不同版本的泛型类实例是不兼容的
     *
     *
     * 可以解决当具体类型不确定的时候，这个通配符就是 ?  ；
     * 当操作类型时，不需要使用类型的具体功能时，只使用Object类中的功能。那么可以用 ? 通配符来表未知类型
     * @param args
     */
    public static void main(String[] args) {
        Generic<String> generic1 = new Generic<String>("11111");
        Generic<Integer> gInteger = new Generic<Integer>(123);
        Generic<Number> gNumber = new Generic<Number>(456);
        // showKeyValue这个方法编译器会为我们报错：Generic<java.lang.Integer>
        //通过提示信息我们可以看到Generic<Integer>不能被看作为`Generic<Number>的子类
        //showKeyValue(gInteger);
        showKeyValue(gNumber);
        //类型通配符一般是使用？代替具体的类型实参，注意了，此处’？’是类型实参，而不是类型形参
        showKeyValue1(gInteger);
        showKeyValue1(gNumber);
        //泛型上下边界
        //showKeyValue2(generic1);  //这一行代码编译器会提示错误，因为String类型并不是Number类型的子类
        showKeyValue2(gNumber);
        showKeyValue2(gInteger);
    }
}
