package com.zbcn.demo.generic;

import java.util.Random;

/**
 * TODO
 *
 * @author Administrator
 * @date 2019/1/15 15:21
 */
public class FruitGenerator implements GenericIF<String> {

    private String[] fruits = new String[]{"Apple", "Banana", "Pear"};
    @Override
    public String getGeneric() {
        Random random = new Random();
        return fruits[random.nextInt(3)];
    }
}
