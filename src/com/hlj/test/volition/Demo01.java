package com.hlj.test.volition;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

class MyData{

    volatile int number = 0;

    public void addTo60(){
        this.number=60;
    }

    //此时number是加了volatile关键字修饰的，volatile是不保证原子性的
    public void addPlusPlus(){
        number++;
    }

    AtomicInteger atomicInteger= new AtomicInteger();

    public void addMyAtomic(){
        atomicInteger.getAndIncrement();
    }
}

/**
 * 1.验证volatile的可见性
 *  1.1 假如 int number=0；number变量之前根本没有添加volatile关键字
 *  1.2 添加了volatile，可以解决可见性问题。
 *
 * 2.验证volatile不保证原子性
 *  2.1 原子性指得是什么意思？ 说白了 就是保证数据的完整一致性
 *      不可分割，完整性，也即某个线程正在做某个具体业务时，中间不可以被加塞或者被分割，需要整体完整
 *      要么同时成功，要么同时失败
 *  2.2 volatile 不保证原子性的案例演示
 *  2.3 why??
 *  2.4 如何解决原子性？
 *      *加synchronized
 *      *直接使用JUC AtomicInteger
 */
public class Demo01 {
    public static void main(String[] args) {//main是一切方法的运行入口
        MyData myData = new MyData();

        for (int i = 1; i <= 20; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int j = 0; j < 1000; j++) {
                        myData.addPlusPlus();
                        myData.addMyAtomic();
                    }
                }
            },String.valueOf(i)).start();
        }
        //需要等待上面20个线程都计算完成后，再用main线程取得最终的结果值看是多少
        /**
         * Thread.activeCount()返回此线程组及其子组中活动线程数的估计。
         * 为什么Thread.activeCount()要大于2呢，因为默认后台是由2个线程的，一个是gc（垃圾处理），一个是main线程
         * Thread.yield()对调度程序的一个暗示，即当前线程愿意产生当前使用的处理器。
         *  意思就是礼让线程意思就是main线程先不执行，让上面的线程线执行
         */
        while (Thread.activeCount() > 2){
            Thread.yield();
        }
        System.out.println(Thread.currentThread().getName()+"\t int type,finally number value: "+myData.number);
        System.out.println(Thread.currentThread().getName()+"\t AtomicInteger type,finally number value: "+myData.atomicInteger);
    }

    /**
     * volatile 可以保证可见性，及时通知其他线程，主物理内存的值已经被修改
     */
    public static void seeOkByVolatile() {
        MyData myData = new MyData();
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName()+"\t come in");
                try {
                    TimeUnit.SECONDS.sleep(3);
                    myData.addTo60();
                    System.out.println(Thread.currentThread().getName()+"\t update number value "+myData.number);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"aaa").start();

        //第二个线程就是我们的main线程
        while (myData.number == 0){
            //main线程就一直在这里等待循环，直到number值不在等于0
        }
        System.out.println(Thread.currentThread().getName()+"\t mission is ove ,main get number value: "+myData.number);
    }




}
