package com.zbcn.demo.thread.concurrency.lock;

import com.google.common.collect.Maps;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @Description:
 * @Auther: zbcn
 * @Date: 2/28/19 20:07
 */
public class LockExample2 {

    private static final Map<String,Data> map = Maps.newTreeMap();

    private static final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    private static final ReentrantReadWriteLock.ReadLock readLock = lock.readLock();

    private static final ReentrantReadWriteLock.WriteLock writeLock = lock.writeLock();

    public static Data get(String key) {
        readLock.lock();
        try {
            return map.get(key);
        }finally {
            readLock.unlock();
        }

    }

    public static Set<String> getAllKeys(){
        readLock.lock();
        try {
            return map.keySet();
        }finally {
            readLock.unlock();
        }

    }

    public static Data put(String key, Data data){
        writeLock.lock();
        try {
            return map.put(key,data);
        }finally {
            writeLock.unlock();
        }

    }

}


class Data {

}
