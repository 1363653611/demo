package com.zbcn.demo.java8.stream;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;

/**
 *
 * @author zbcn
 * @date 2018/11/29 12:03
 */
public class ListStreamTest {

    public static void main(String[] args) {
        ArrayList<String> s = new ArrayList<>();
        s.add("aa");
        s.add("bb");
        s.add("cc");
        s.add("dd");
        s.add("ee");
        s.stream().filter(v->{
            return StringUtils.equals("aa",v)||StringUtils.equals("bb",v);
        }).forEach(v->{
            System.out.println(v);
        });
    }
}
