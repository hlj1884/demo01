package com.hlj.test.threadLock.study;

import java.util.concurrent.Callable;

class MyThread1 implements Runnable{

    @Override
    public void run() {

    }
}

/**
 * 怎么用
 */
class myThread2 implements Callable<Integer>{

    @Override
    public Integer call() throws Exception {
        System.out.println("张三");
        return 1024;
    }
}
/**
 *
 */
public class CallableDemo {
    public static void main(String[] args) {

    }
}
