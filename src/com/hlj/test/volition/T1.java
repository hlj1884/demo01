package com.hlj.test.volition;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class T1 {

    volatile int n =0;

    public void add(){
        n++;
    }

    /**
     * 如果new一个可重入锁时，不带参数，天生就是一个非公平锁
     * @param args
     */
    public static void main(String[] args) {


        Lock lock = new ReentrantLock();
    }

}
