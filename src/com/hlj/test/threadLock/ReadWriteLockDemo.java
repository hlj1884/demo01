package com.hlj.test.threadLock;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantReadWriteLock;


/**
 * 写缓存的3大操作
 * 1 set k1 v1 (写)
 * 2 get（读）
 * 3 清空
 */
class MyCache{//资源类

    private volatile Map<String ,Object> map = new HashMap<>();

    private ReentrantReadWriteLock readWriteLock=new ReentrantReadWriteLock();
    public void put(String key,Object value){
        readWriteLock.writeLock().lock();
        try {
            System.out.println(Thread.currentThread().getName()+"\t 正在写入："+key);
            try { TimeUnit.MILLISECONDS.sleep(300); } catch (InterruptedException e) { e.printStackTrace(); }
            map.put(key,value);
            System.out.println(Thread.currentThread().getName()+"\t 写入完成");
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            readWriteLock.writeLock().unlock();
        }

    }

    public void get(String key){

        readWriteLock.readLock().lock();
        try {
            System.out.println(Thread.currentThread().getName()+"\t 正在读取");
            try { TimeUnit.MILLISECONDS.sleep(300); } catch (InterruptedException e) { e.printStackTrace(); }
            Object result = map.get(key);
            System.out.println(Thread.currentThread().getName()+"\t 读取完成："+result);
        } catch (Exception e) {
             e.printStackTrace();
        }finally {
             readWriteLock.readLock().unlock();
        }

    }

    public void clearMap(){
        map.clear();
    }
}
/**
 * 多个线程同时读一个资源类没有任何问题，所以为了满足并发量，读取共享资源应该可以同时进行
 * 但是
 * 如果有一个线程想去写共享资源来，就不应该再由其他线程可以对该资源进行读或写；
 * 小总结；
 *          读-读能共存
 *          读写不能共存
 *          写写不能共存
 *
 *          写操作： 原子+独占 整个过程必须是一个完整的统一体，中间不许被分割，被打断
 *
 *          读写分离
 */
public class ReadWriteLockDemo {

    public static void main(String[] args) {

        MyCache myCache = new MyCache();

        for (int i = 1; i <= 5; i++) {
            final int tempInt=i;
            new Thread(() -> {
                myCache.put(tempInt+"",tempInt+"");
            },String.valueOf(i)).start();
        }

        for (int i = 1; i <= 5; i++) {
            final int tempInt=i;
            new Thread(() -> {
                myCache.get(tempInt+"");
            },String.valueOf(i)).start();
        }
    }
}
