_编译器对默认值的限制： _
1. 元素不能有不确定的值。也就是说，元素必须要么具有默认值，要么在使用注解时提供元素的值
2. 对于非基本类型的元素，无论是在源代码中声明，还是在注解接口中定义默认值，都不能以null作为值
_注解不支持继承_

_快捷方式_
    - 所谓的快捷方式就是注解中定义了名为value的元素，并且在使用该注解时，如果该元素是唯一需要赋值的一个元素，那么此时无需使用key=value的语法，而只需在括号内给出value元素所需的值即可
    
_Java内置注解与其它元注解_
 - @Override：用于标明此方法覆盖了父类的方法，源码如下
 - @Deprecated：用于标明已经过时的方法或类，源码如下，关于@Documented稍后分析：
 - @SuppressWarnnings:用于有选择的关闭编译器对类、方法、成员变量、变量初始化的警告，其实现源码如下：
  > deprecation：使用了不赞成使用的类或方法时的警告；
    unchecked：执行了未检查的转换时的警告，例如当使用集合时没有用泛型 (Generics) 来指定集合保存的类型; 
    fallthrough：当 Switch 程序块直接通往下一种情况而没有 Break 时的警告;
    path：在类路径、源文件路径等中有不存在的路径时的警告; 
    serial：当在可序列化的类上缺少 serialVersionUID 定义时的警告; 
    finally：任何 finally 子句不能正常完成时的警告; 
    all：关于以上所有情况的警告。
   >
 - @Documented  被修饰的注解会生成到javadoc中
 - @Inherited 可以让注解被继承，但这并不是真的继承，只是通过使用@Inherited，可以让子类Class对象使用getAnnotations()获取父类被@Inherited修饰的注解

_注解与反射机制_
    -  java 使用 Annotation 类型接口代表注解元素。
    - java 在java.lang.reflect 反射包下新增了Annotatedelement 接口。表示目前正在 VM 中运行的程序中已使用注解的元素，通过该接口提供的方法可以利用反射技术地读取注解的信息
    - 反射包类 Constructor类、Field 类、Method类、Package类、Class类都实现了AnnotatedElement接口
         > Class：类的Class对象定义 　 
            Constructor：代表类的构造器定义 　 
            Field：代表类的成员变量定义 
            Method：代表类的方法定义 　 
            Package：代表类的包定义
         >
      
_Java 8中注解增强_

    - 元注解@Repeatable是JDK1.8新加入的，它表示在同一个位置重复相同的注解。在没有该注解前，一般是无法在同一个类型上使用相同的注解的
    
      
  
  
  
  
  
   