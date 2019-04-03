package com.zbcn.demo.spring.ioc;

import com.zbcn.demo.spring.ioc.annotion.*;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class ClassLoadManager {

    private Set<String> classSet = new HashSet();

    /**
     * IOC容器 如： String(loginController) --> Object(loginController实例)
     */
    private Map<String, Object> iocBeanMap = new ConcurrentHashMap(32);

    private void getPackageClassFile(String packageName) {

        URL resource = this.getClass().getClassLoader().getResource(packageName);

        File file = new File(resource.getFile());
        if(file.exists() && file.isDirectory()){
            File[] files = file.listFiles();
            for (File fileSon : files) {
                if(fileSon.isDirectory()){
                    // 递归扫描
                    getPackageClassFile(packageName + "/" + fileSon.getName());
                } else{
                    if(fileSon.getName().endsWith(".class")){
                        System.out.println("正在加载: " + packageName.replace("/", ".") + "." + fileSon.getName());
                        classSet.add(packageName.replace("/", ".") + "." + fileSon.getName().replace(".class", ""));
                    }
                    }
                }
        }else {
            throw new RuntimeException("没有找到需要扫描的文件目录");
        }

    }

    private void addServiceToIoc(Class classZ) throws IllegalAccessException, InstantiationException {
        // 预留位置，之后优化
        if (classZ.getAnnotation(MyController.class) != null) {
            iocBeanMap.put(StringUtils.lowerCase(classZ.getSimpleName()), classZ.newInstance());
            System.out.println("控制反转访问控制层:" + StringUtils.lowerCase(classZ.getSimpleName()));
        } else if (classZ.getAnnotation(MyService.class) != null) {
            // 将当前类交由IOC管理
            MyService myService = (MyService) classZ.getAnnotation(MyService.class);
            iocBeanMap.put(StringUtils.isEmpty(myService.value()) ? StringUtils.lowerCase(classZ.getSimpleName()) : StringUtils.lowerCase(myService.value()), classZ.newInstance());
            System.out.println("控制反转服务层:" + StringUtils.lowerCase(classZ.getSimpleName()));
        } else if (classZ.getAnnotation(MyMapping.class) != null) {
            MyMapping myMapping = (MyMapping) classZ.getAnnotation(MyMapping.class);
            iocBeanMap.put(StringUtils.isEmpty(myMapping.value()) ? StringUtils.lowerCase(classZ.getSimpleName()) : StringUtils.lowerCase(myMapping.value()), classZ.newInstance());
            System.out.println("控制反转持久层:" + StringUtils.lowerCase(classZ.getSimpleName()));
        }
    }


    public void addAutowiredToField(Object obj) throws IllegalAccessException, InstantiationException {

        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field field : fields) {
            MyAutowired annotation = field.getAnnotation(MyAutowired.class);
            if(annotation != null){
              field.setAccessible(true);

                Class<?> type = field.getType();
                if(type.isInterface()){
                    if(StringUtils.isNotBlank(annotation.value())){
                        field.set(obj,iocBeanMap.get(annotation.value()));
                    }else {
                        List<Object> list = findSuperInterfaceByIoc(field.getType());
                        if (list != null && list.size() > 0) {
                            if (list.size() > 1) {
                                throw new RuntimeException(obj.getClass() + "  注入接口 " + field.getType() + "   失败，请在注解中指定需要注入的具体实现类");
                            } else {
                                field.set(obj, list.get(0));
                                // 递归依赖注入
                                addAutowiredToField(field.getType());
                            }
                        } else {
                            throw new RuntimeException("当前类" + obj.getClass() + "  不能注入接口 " + field.getType().getClass() + "  ， 接口没有实现类不能被实例化");
                        }
                    }
                    }else {
                    String beanName = StringUtils.isEmpty(annotation.value()) ? StringUtils.lowerCase(field.getName()) : StringUtils.lowerCase(annotation.value());
                    Object beanObj = iocBeanMap.get(beanName);
                    field.set(obj, beanObj == null ? field.getType().newInstance() : beanObj);
                    System.out.println("依赖注入" + field.getName());
//                递归依赖注入
                }
                addAutowiredToField(field.getType());
                }
            if (field.getAnnotation(Value.class) != null) {
                field.setAccessible(true);
                Value value = field.getAnnotation(Value.class);
                field.set(obj, StringUtils.isNotEmpty(value.value()) ? ConfigurationUtils.getPropertiesByKey(value.value()) : null);
                System.out.println("注入配置文件  " + obj.getClass() + " 加载配置属性" + value.value());
                }

            }
        }




    private List<Object> findSuperInterfaceByIoc(Class classz) {
        Set<String> beanNameList = iocBeanMap.keySet();
        ArrayList<Object> objectArrayList = new ArrayList<>();
        for (String beanName : beanNameList) {
            Object obj = iocBeanMap.get(beanName);
            Class<?>[] interfaces = obj.getClass().getInterfaces();
            if (useArrayUtils(interfaces, classz)) {
                objectArrayList.add(obj);
            }
        }
        return objectArrayList;
    }

    private boolean useArrayUtils(Class<?>[] interfaces, Class classz) {

        for (Class in :interfaces) {
            if(in.equals(classz)){
              return true;
            }
        }
        return false;
    }

    public ClassLoadManager buildClass() throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        // 获取扫描包路径
        String classScanPath = (String) ConfigurationUtils.getProperties().get("ioc.scan.path");
        if (StringUtils.isNotEmpty(classScanPath)) {
            classScanPath = classScanPath.replace(".", "/");
        } else {
            throw new RuntimeException("请配置项目包扫描路径 ioc.scan.path");
        }
        // 扫描项目根目录中所有的class文件
        getPackageClassFile(classScanPath);
        for (String className : classSet) {
            addServiceToIoc(Class.forName(className));
        }
        // 获取带有MyService注解类的所有的带MyAutowired注解的属性并对其进行实例化
        Set<String> beanKeySet = iocBeanMap.keySet();
        for (String beanName : beanKeySet) {
            addAutowiredToField(iocBeanMap.get(beanName));
        }
        return this;
    }


    public Object getIocBean(String loginController) {
        return iocBeanMap.get(loginController);
    }
}
