package com.hlj.test.nosafeList;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * 集合类不安全的问题
 * ArrayList
 * HashSet 底层是一个default initial capacity (16) and load factor (0.75).
 * 意思：创建了一个初始值是16，负载因子是0.75的hashMap
 *
 */
public class ContainerNotSafeDemo {


    public static void main(String[] args) {

    }

    private static void hashMapNoSafe() {
        //        Map<String,String> map=new HashMap<>();
        Map<String,String> map=new ConcurrentHashMap<>();

        for (int i = 1; i <= 30; i++) {
            new Thread(() -> {
                map.put(Thread.currentThread().getName(), UUID.randomUUID().toString().substring(0,8));
                System.out.println(map);
            },String.valueOf(i)).start();
        }
    }

    /**
     *  Collections.synchronizedSet(new HashSet<>());
     *  new CopyOnWriteArraySet<>();
     */
    private static void setNoSafe() {
        //Set<String> set = Collections.synchronizedSet(new HashSet<>());
        Set<String> set = new CopyOnWriteArraySet<>();//new HashSet<>();
        for (int i = 1; i <= 30; i++) {
            new Thread(() -> {
                set.add(UUID.randomUUID().toString().substring(0,8));
                System.out.println(set);
            },String.valueOf(i)).start();
        }
    }

    private static void listNoSafe() {
        //        List<String> list = new ArrayList<>();
//        List<String> list = Collections.synchronizedList(new ArrayList<>());
        List<String> list = new CopyOnWriteArrayList<>();
        String str = "";

        for (int i = 1; i <= 30; i++) {
            new Thread(() -> {
                 list.add(UUID.randomUUID().toString().substring(0,8));
                System.out.println(list);
            },String.valueOf(i)).start();
        }
        /**
         * 不要只是会用，会用只不过是一个api调用工程师
         * java.util.ConcurrentModificationException  并发修改的异常
         * ArrayList 中高并发常见的错误
         *
         * 1.故障现象
         *      ava.util.ConcurrentModificationException
         *
         * 2.导致原因
         *      并发争抢修改导致，产考我们的花名册签名情况，
         *      一个人正在写，另外一个同学过来抢夺，导致数据不一致异常，并发修改异常
         * 3.解决方案
         *  3.1 new Vector<>();
         *  3.2 Collections.synchronizedList(new ArrayList<>());
         *  3.3 ???   Class CopyOnWriteArrayList<E>
         *      new CopyOnWriteArrayList<>();
         *      CopyOnWrite写时复制
         * 4.优化建议（同样的错误不犯第二次）
         */}

}
