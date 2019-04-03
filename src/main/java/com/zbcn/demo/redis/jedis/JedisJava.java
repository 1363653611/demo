package com.zbcn.demo.redis.jedis;

import redis.clients.jedis.Jedis;

/**
 * redis 代码测试
 */
public class JedisJava {

    private static Jedis redis = new Jedis("localhost");
    public static void main(String[] args) {
        //Jedis redis = new Jedis("localhost");
        System.out.println("链接成功：   "+ redis.ping());
        //设置值
        //System.out.println(setString("runoobkey", "www.runoob.com"));
        //获取值
        //System.out.println(getString("runoobkey"));

        //设置list
        //System.out.println(setList("listKey","zhangsan","lisi"));
        //获取list
        System.out.println(getList("listKey"));
    }

    private static String setString(String key,String value){
        return redis.set(key, value);
    }

    private static String getString(String key){
        return redis.get(key);
    }

    private static Long setList(String key,String ... value){
        return redis.lpush(key,value);
    }

    private static String getList(String key){
        return redis.lpop(key);
    }
}
