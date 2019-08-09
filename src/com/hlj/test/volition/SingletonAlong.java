package com.hlj.test.volition;

/**
 * 单机版单例模式
 */
public class SingletonAlong {
    private static volatile SingletonAlong instance=null;


    private SingletonAlong(){
        System.out.println(Thread.currentThread().getName()+"\t 我是构造方法SingletonDemo()");


    }

    //DCL （Double Check lock双端检锁机制）
    //所谓的双端检锁机制，就是在加锁的前和后都进行了一次判断
    public static SingletonAlong getInstance(){
        if (instance==null){
            synchronized (SingletonAlong.class){
                if (instance==null){
                    instance=new SingletonAlong();
                }
            }
        }
        return instance;
    }

    public static void main(String[] args) {
        //单线程（main线程的操作动作。。。。）
//        System.out.println(SingletonAlong.getInstance()==SingletonAlong.getInstance());
//        System.out.println(SingletonAlong.getInstance()==SingletonAlong.getInstance());
//        System.out.println(SingletonAlong.getInstance()==SingletonAlong.getInstance());

//        System.out.println();
//        System.out.println();
//        System.out.println();
        //可以加synchronized 解决并发问题，但是太重
        for (int i = 0; i < 10; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    SingletonAlong.getInstance();
                }
            },String.valueOf(i)).start();
        }
    }
}
