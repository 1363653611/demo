package com.zbcn.demo.thread.concurrency.immutable;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;

/**
 * @Description: 不可变对象
 * @Auther: zbcn
 * @Date: 2/27/19 17:03
 */
public class ImmutableExImple3 {

    private final static ImmutableList list = ImmutableList.of(1,4,3,5);

    private final static ImmutableSet set = ImmutableSet.copyOf(list);

    private final static ImmutableMap map = ImmutableMap.of(1,2,3,4,5,6);

    private final static ImmutableMap<Integer, Integer> map2 = ImmutableMap.<Integer, Integer>builder().put(2,3).put(4,5).build();
    public static void main(String[] args) {
        list.add(5);
    }
}
