package com.hlj.test.threadLock.ThreadProCon;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 资源类
 */
class MyResource {
    private volatile boolean FLAG = true;//默认开启，进行生产+消费
    private AtomicInteger atomicInteger = new AtomicInteger();
    BlockingQueue<String> blockingQueue = null;

    public MyResource(BlockingQueue<String> blockingQueue) {
        this.blockingQueue = blockingQueue;
        System.out.println(blockingQueue.getClass().getName());
    }

    public void myProd() throws InterruptedException {
        String date = null;
        boolean offer;
        while (FLAG) {
            date = atomicInteger.incrementAndGet() + "";
            offer = blockingQueue.offer(date, 2L, TimeUnit.SECONDS);
            if (offer) {
                System.out.println(Thread.currentThread().getName() + "\t 插入队列" + date + "成功");
            } else {
                System.out.println(Thread.currentThread().getName() + "\t 插入队列" + date + "失败");
            }
            TimeUnit.SECONDS.sleep(1);
        }
        System.out.println(Thread.currentThread().getName() + "\t 老板叫停，表示FLAG=false，生产动作结束");
    }

    public void myConsumer() throws InterruptedException {
        String result = null;
        while (FLAG) {
            result = blockingQueue.poll(2, TimeUnit.SECONDS);

            if (result == null || result.equalsIgnoreCase("")) {
                FLAG = false;
                System.out.println(Thread.currentThread().getName() + "\t 超过2秒没有取到蛋糕，消费退出");
                System.out.println();
                System.out.println();
                return;
            }
            System.out.println(Thread.currentThread().getName() + "\t 消费蛋糕队列" + result + "成功");
        }
    }

    public void stop() throws Exception {
        this.FLAG = false;
    }
}

public class ProdConsumer_BlockQueueDemo {
    public static void main(String[] args) throws Exception {
        MyResource myResource = new MyResource(new ArrayBlockingQueue<String>(10));

        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "\t 生产线程启动");

            try {
                myResource.myProd();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "prod").start();

        new Thread(() -> {
            try {
                System.out.println(Thread.currentThread().getName()+"消费线程启动");
                System.out.println();
                System.out.println();
                myResource.myConsumer();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "consumer").start();

        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println();
        System.out.println();
        System.out.println();

        System.out.println("5秒时间，到大老板main线程叫停，活动结束");

        myResource.stop();
    }


}
