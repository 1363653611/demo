反射：
运行状态中，对于任意一个类，都可以通过反射知道这个类的属性和方法。
对于任意一个对象，都能调用它的任意方法和属性。

总结：反射可以知道一个类的任意属性和方法。

静态编译：编译时已经确定类型，绑定对象，即通过。
动态编译：运行时确定类型，绑定对象。 动态编译发挥了java 的最大灵活度。体现了多态的应用，降低了类之间的耦合。


缺点： 性能。反射基本上是一种解释执行的方式。

Class： （java.lang.Class）是所有类的类
    反射实现的基础

    普通对象：Code code1 = new Code();


    获取class：
    Class 对象的创建不能使用 new ；构造器是私有的
     - 何一个类都有一个隐含的静态成员变量class，这种方式是通过获取类的静态成员变量class得到的:Class c1 = Code.class;
     - code1是Code的一个对象，这种方式是通过一个类的对象的getClass()方法获得的
     - 这种方法是Class类调用forName方法，通过一个类的全量限定名获得:Class.forName("com.trigl.reflect.Code");

    获取成员方法Method
    获取成员变量Field
    获取构造函数Constructor








