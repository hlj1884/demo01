package com.hlj.test.threadLock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 自旋锁的好处:循环比较获取知道成功为止，没有雷士wait的阻塞
 *
 * 通过CAS操作完成自旋锁，A线程线进来调用mylock方法自己持有锁5秒钟，B随后进来后发现
 * 当前有线程持有锁，不是null，所以只能通过自旋等待，知道A释放锁后B随后抢到
 *
 */
public class SpinLockDemo {

    //原子引用线程
    AtomicReference<Thread> atomicReference=new AtomicReference<>();

    public void mylock(){
        Thread thread = Thread.currentThread();

        System.out.println(Thread.currentThread().getName()+"\t come in O(∩_∩)");
        while (!atomicReference.compareAndSet(null,thread)){

        }
    }
    public void myUnlock(){
        Thread thread = Thread.currentThread();
        atomicReference.compareAndSet(thread,null);
        System.out.println(Thread.currentThread().getName()+"\t invoked myUnlock()");
    }
    public static void main(String[] args) {

        /**
         * 因为两个线程之间暂停了1秒钟，所以首先启动aa线程，aa线程调用mylock()方法
         * 因为这个时候atomicReference还是null，所以调用compareAndSet比较并交换时，就把aa线程放到atomicReference
         * 当atomicReference.compareAndSet(null,thread)替换成功时，整体取反就跳出循环，完成方法
         * 1秒钟之后bb线程启动，运行mylock方法，因为这个时候atomicReference里面已经有aa线程了，所以一直在循环比较
         * 当5秒钟之后aa线程运行myUnlock方法，通过currentThread()获取当前正在执行的线程对象的引用aa时，通过
         *   atomicReference.compareAndSet(thread,null);就可以成功把atomicReference设置为null
         *   这时候bb线程的!atomicReference.compareAndSet(null,thread)就可以为真时，就可以跳出循环
         *   执行后续方法了
         */
        SpinLockDemo lockDemo = new SpinLockDemo();
         new Thread(()->{
             lockDemo.mylock();
             try { TimeUnit.SECONDS.sleep(5); } catch (InterruptedException e) { e.printStackTrace(); }
             lockDemo.myUnlock();
         },"aa").start();
        try { TimeUnit.SECONDS.sleep(1); } catch (InterruptedException e) { e.printStackTrace(); }

        new Thread(()->{
            lockDemo.mylock();
            lockDemo.myUnlock();
        },"bb").start();
    }
}
