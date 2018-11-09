package com.zbcn.demo.jsonnode.jackson;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;

import java.io.IOException;

/**
 * JsonProperty测试
 *
 * @author Administrator
 * @date 2018/11/8 11:33
 */
@Data
public class Student {

    private String trueName;

    @JsonProperty("name")
    public void setName(String trueName) {
        this.trueName = trueName;
    }

    @JsonProperty("_name")
    public void set_Name(String trueName) {
        this.trueName = trueName;
    }
    @JsonProperty("trueName")
    public void setTrueName(String trueName) {
        this.trueName = trueName;
    }
    @JsonProperty("Name")
    public String getTrueName() {
        return trueName;
    }


    public static void main(String[] args) {
        ObjectMapper mapper = new ObjectMapper();
        //test3(mapper);
        test();
    }

    private static void test3(ObjectMapper mapper) {
        try {
            //JsonNode jsonNode = mapper.readTree();
            Student student = mapper.readValue(test2(), Student.class);
            TypeReference<String> typeReference = new TypeReference<String>() {
            };
//            String s = mapper.convertValue(student, typeReference);
            String s1 = mapper.writeValueAsString(student);
            System.out.println(s1);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String test2(){
        return "{\"name\":\"张三\"}";

    }

    private static void test() {
        Student student = new Student();
        student.setTrueName("张三");
        try {
            System.out.println(new ObjectMapper().writeValueAsString(student));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

}
