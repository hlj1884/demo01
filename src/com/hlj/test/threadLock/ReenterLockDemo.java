package com.hlj.test.threadLock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Phone implements Runnable{
    public synchronized void sendSMS(){
        System.out.println(Thread.currentThread().getName()+"\t invoked senSMS");
        sendEmail();
    }
    public synchronized void sendEmail(){
        System.out.println(Thread.currentThread().getName()+"\t ##########invoked sendEmail");
    }

    //========================================================================
    Lock lock = new ReentrantLock();

    @Override
    public void run() {
        get();
    }
    public void get(){
        lock.lock();
        try {
            System.out.println(Thread.currentThread().getName()+"\t invoked get()");
            set();
        } catch (Exception e) {
             e.printStackTrace();
        }finally {
             lock.unlock();
        }
    }
    public void set(){
        lock.lock();
        try {
            //线程可以进入任何一个它已经拥有的锁
            //
            // 所同步着的代码块
            System.out.println(Thread.currentThread().getName()+"\t invoked set()");
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }
}
/**
 * 同一个线程，外层函数获得锁之后，内层递归函数仍然能够获得锁的代码，
 * 在同一个线程在外层方法获取锁的时候，在进入内层方法会自动获取锁
 *
 * 也即是说，线程可以进入任何一个它已经拥有的锁所同步着的代码块
 *
 * case one Synchronized 就是一个典型的可重入锁
 * t1	 invoked senSMS             t1线程在外层方法获取锁的时候
 * t1	 ##########invoked sendEmail    t1在进入内层方法会自动获取锁
 * t2	 invoked senSMS
 * t2	 ##########invoked sendEmail
 *
 * case two
 * ReentranLock
 *
 * lock 和 unlock 只要你配对 写几把锁都没问题  意思就是加锁几次就要有解锁几次，两两匹配
 */
public class ReenterLockDemo {

    public static void main(String[] args) {

        Phone phone = new Phone();

         new Thread(()->{
             try {
                 phone.sendSMS();
             } catch (Exception e) {
                 e.printStackTrace();
             }
         },"t1").start();

          new Thread(()->{
              try {
                  phone.sendSMS();
              } catch (Exception e) {
                  e.printStackTrace();
              }
          },"t2").start();

        try { TimeUnit.SECONDS.sleep(1); } catch (InterruptedException e) { e.printStackTrace(); }
        System.out.println();
        System.out.println();
        System.out.println();

        Thread t3 = new Thread(phone);
        Thread t4 = new Thread(phone);
        t3.start();
        t4.start();
    }
}
