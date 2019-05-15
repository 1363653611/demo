

JDK 动态代理：
    两个类：
    1.java.lang.reflect.InvocationHandler
    2.java.long.reflect.Proxy

    实现步骤：
    1.编写一个中间类，实现InvocationHandler
    2. 在中间类中实现invoke方法
    3. 利用Proxy类创建 代理类

    Proxy 作用就是用反射来生成代理类。代替IHelloService
