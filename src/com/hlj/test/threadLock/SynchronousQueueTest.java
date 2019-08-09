package com.hlj.test.threadLock;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;

/**
 * 阻塞队列SynchronousQueue演示
 */
public class SynchronousQueueTest {
    public static void main(String[] args) {
        BlockingQueue<String> blockingQueue=new SynchronousQueue<>();

         new Thread(()->{
             try {
                 System.out.println(Thread.currentThread().getName()+"\t put 1");
                 blockingQueue.put("a");
                 System.out.println(Thread.currentThread().getName()+"\t put 2");
                 blockingQueue.put("b");
                 System.out.println(Thread.currentThread().getName()+"\t put 3");
                 blockingQueue.put("a");

             } catch (InterruptedException e) {
                 e.printStackTrace();
             }
         },"AAA").start();
         new Thread(()->{
             try {
                try { TimeUnit.SECONDS.sleep(5); } catch (InterruptedException e) { e.printStackTrace(); }
                 System.out.println(Thread.currentThread().getName()+"\t"+blockingQueue.take());
                try { TimeUnit.SECONDS.sleep(5); } catch (InterruptedException e) { e.printStackTrace(); }
                 System.out.println(Thread.currentThread().getName()+"\t"+blockingQueue.take());
                try { TimeUnit.SECONDS.sleep(5); } catch (InterruptedException e) { e.printStackTrace(); }
                 System.out.println(Thread.currentThread().getName()+"\t"+blockingQueue.take());

             } catch (InterruptedException e) {
                 e.printStackTrace();
             }
         },"BBB").start();


    }
}
