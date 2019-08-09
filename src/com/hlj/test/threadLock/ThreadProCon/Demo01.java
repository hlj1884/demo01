package com.hlj.test.threadLock.ThreadProCon;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class ShareDate{//资源类
    private  int number=0;
    private Lock lock=new ReentrantLock();
    private Condition condition=lock.newCondition();
    public void incrment(){
        lock.lock();
        try {
            //1 判断
            while (number != 0){
                //等待，不能生产
                condition.await();
            }
            //2 干活
            number++;
            System.out.println(Thread.currentThread().getName()+"\t"+number);
            //3 通知唤醒
            condition.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void decrease(){
        lock.lock();
        //1判断
        try {
            while (number==0){
                condition.await();
            }
            number--;
            System.out.println(Thread.currentThread().getName()+"\t"+number);
            condition.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}
/**
 * 题目：一个初始值为零的变量，两个线程对其交替操作，一个加1一个减1，来5轮
 *
 * 线程操作资源类，判断干活唤醒通知
 * 严防多线程下的虚假唤醒
 *
 * 1    线程      操作 （方法）     资源类
 * 2    判断      干活      通知
 * 3    防止虚假唤醒操作
 */
public class Demo01 {
    public static void main(String[] args) {
        ShareDate shareDate = new ShareDate();

         new Thread(()->{
             for (int i = 1; i <= 5 ; i++) {
                 shareDate.incrment();
             }
         },"AAA").start();

          new Thread(()->{
              for (int i = 1; i <= 5 ; i++) {
                  shareDate.decrease();
              }
          },"BBB").start();
    }
}
