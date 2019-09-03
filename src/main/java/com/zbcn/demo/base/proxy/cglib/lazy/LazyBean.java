package com.zbcn.demo.base.proxy.cglib.lazy;

import lombok.Data;
import net.sf.cglib.proxy.Enhancer;

/**
 * 延时加载bean
 */
@Data
public class LazyBean {

    private String name;

    private int age;

    private PropertyBean propertyBean;

    private PropertyBean propertyBeanDispatcher;

    public LazyBean(String name, int age) {
        this.name = name;
        this.age = age;
        this.propertyBean = createPropertyBeanLazyLoad();
        this.propertyBeanDispatcher = createPropertyBeanDispatcher();

    }

    private PropertyBean createPropertyBeanDispatcher() {
//        Enhancer enhancer = new Enhancer();
//        enhancer.setSuperclass(PropertyBean.class);
        PropertyBean propertyBean = (PropertyBean)Enhancer.create(PropertyBean.class,new ConcreteClassDispatcher());
        return propertyBean;
    }

    private PropertyBean createPropertyBeanLazyLoad() {
        /**
         * 使用cglib进行懒加载 对需要延迟加载的对象添加代理，在获取该对象属性时先通过代理类回调方法进行对象初始化。
         * 在不需要加载该对象时，只要不去获取该对象内属性，该对象就不会被初始化了（在CGLib的实现中只要去访问该对象内属性的getter方法，
         * 就会自动触发代理类回调）。
         */
//        Enhancer enhancer = new Enhancer();
//        enhancer.setSuperclass(PropertyBean.class);
        PropertyBean propertyBean = (PropertyBean) Enhancer.create(PropertyBean.class,new ConcreteClassLazyLoader());
        return  propertyBean;
    }
}
