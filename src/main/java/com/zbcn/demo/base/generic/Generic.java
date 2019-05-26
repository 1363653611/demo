package com.zbcn.demo.base.generic;

import lombok.extern.slf4j.Slf4j;

/**
 *
 * 泛型类
 * 此处T可以随便写为任意标识，常见的如T、E、K、V等形式的参数常用于表示泛型
 *
 * @author Administrator
 * @date 2019/1/15 15:06
 */
//class 类名称 <泛型标识：可以随便写任意标识号，标识指定的泛型的类型>{
//      private 泛型标识 /*（成员变量类型）*/ var;
//        .....
//
//        }
//}
//在实例化泛型类时，必须指定T的具体类型
    @Slf4j
public class Generic <T> {

    //key这个成员变量的类型为T,T的类型由外部指定
    private T key;


    //泛型构造方法形参key的类型也为T，T的类型由外部指定
    public Generic(T key) {
        this.key = key;
    }

    //  //我想说的其实是这个，虽然在方法中使用了泛型，但是这并不是一个泛型方法。
    // 这只是类中一个普通的成员方法，只不过他的返回值是在声明泛型类已经声明过的泛型。
    // 所以在这个方法中才可以继续使用 T 这个泛型。
    public T getKey() {
        return key;
    }

    //泛型方法getKey的返回值类型为T，T的类型由外部指定
    public void setKey(T key) {
        this.key = key;
    }

    /**
     * 这个方法显然是有问题的，在编译器会给我们提示这样的错误信息"cannot reslove symbol E"
     * 因为在类的声明中并未声明泛型E，所以在使用E做形参和返回值类型时，编译器会无法识别。
     public E setKey(E key){
     this.key = keu
     }
     */


    /**
     * 这才是一个真正的泛型方法。
     * 首先在public与返回值之间的<T>必不可少，这表明这是一个泛型方法，并且声明了一个泛型T
     * 这个T可以出现在这个泛型方法的任意位置.
     * 泛型的数量也可以为任意多个
     *    如：public <T,K> K showKeyName(Generic<T> container){
     *        ...
     *        }
     */
    public <T> T showKeyName(Generic<T> container){
        System.out.println("container key :" + container.getKey());
        //当然这个例子举的不太合适，只是为了说明泛型方法的特性。
        T test = container.getKey();
        return test;
    }

    //这也不是一个泛型方法，这就是一个普通的方法，只是使用了Generic<Number>这个泛型类做形参而已。
    public void showKeyValue1(Generic<Number> obj){
        log.info("泛型测试","key value is " + obj.getKey());
    }

    //这也不是一个泛型方法，这也是一个普通的方法，只不过使用了泛型通配符?
    //同时这也印证了泛型通配符章节所描述的，?是一种类型实参，可以看做为Number等所有类的父类
    public void showKeyValue2(Generic<?> obj){
        log.info("泛型测试","key value is " + obj.getKey());
    }

    /**
     * 这个方法也是有问题的，编译器会为我们提示错误信息："UnKnown class 'T' "
     * 对于编译器来说T这个类型并未项目中声明过，因此编译也不知道该如何编译这个类。
     * 所以这也不是一个正确的泛型方法声明。
     public void showkey(T genericObj){

     }
     */


    /**
     * 1. 泛型的类型参数只能是类类型，不能是简单类型。
     * 2. 不能对确切的泛型类型使用instanceof操作。如下面的操作是非法的，编译时会出错。
     * @param args
     */
    public static void main(String[] args) {
        //泛型的类型参数只能是类类型（包括自定义类），不能是简单类型
        Generic<Integer> integerGeneric = new Generic<>(1);
        //传入的实参类型需与泛型的类型参数类型相同，即为String.
        Generic<String> sss = new Generic<>("sss");

        //可以不传递任何参数，但是就没有了限制
        Generic integerGeneric1 = new Generic<>(1323);
        integerGeneric1.setKey("assdf");
    }
}
