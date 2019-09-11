package com.zbcn.demo.elastic.manager;

import com.zbcn.demo.elastic.domain.StudentDoc;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

/**
 * 配置管理类
 */
public class MappingManager {

    public static void main(String[] args) {
        analysisClassInfo(StudentDoc.class);
    }

    public static <T> void analysisClassInfo(Class<T> clasz){
        Annotation[] annotations = clasz.getAnnotations();
        for (Annotation annotation :annotations) {
            Class<? extends Annotation> aClass = annotation.annotationType();
            System.out.println(aClass.getName());
            if(aClass.isAnnotation()){
                Field[] declaredFields = aClass.getDeclaredFields();
                for (Field declaredField : declaredFields) {
                    System.out.println(declaredField.getClass().getName());
                }
            }
        }
    }
}
