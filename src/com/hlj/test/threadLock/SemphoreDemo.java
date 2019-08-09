package com.hlj.test.threadLock;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class SemphoreDemo {

    public static void main(String[] args) {

        Semaphore semaphore = new Semaphore(3);//模拟3个停车位

        for (int i = 1; i <= 6; i++) {//模拟6辆汽车
              new Thread(()->{
                  try {
                      semaphore.acquire();
                      System.out.println(Thread.currentThread().getName()+"\t 抢到车位");
                      try { TimeUnit.SECONDS.sleep(3); } catch (InterruptedException e) { e.printStackTrace(); }
                      System.out.println(Thread.currentThread().getName()+"\t 停车3秒后离开车位");
                      semaphore.release();
                  } catch (InterruptedException e) {
                      e.printStackTrace();
                  } finally {

                  }
              },String.valueOf(i)).start();
        }
    }
}
