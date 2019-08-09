package com.hlj.test.threadLock;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * ArrayBlockingQueue 是一个基于数组结构的有界阻塞队列，此队列按FIFO（先进先出）原则对元素进行排序
 * LinkedBlockingQueue 是一个基于链表结构的阻塞队列，此队列按FIFO(先进先出)排序元素，吞吐量通常要高于ArrayBlockingQueue
 * SynchronousQueue 一个不存储元素的阻塞队列，每个插入操作必须等到另一个线程调用移除操作，否则插入操作一直处于阻塞状态，吞吐量通常要高
 *
 *
 * 1.队列
 *
 * 2.阻塞队列
 *  2.1 阻塞队列有没有好的一面
 *
 *  2.2 不得不阻塞，你如何管理
 */
public class  BlockingQueueDemo {

    public static void main(String[] args) throws InterruptedException {
        BlockingQueue<String> blockingQueue = new ArrayBlockingQueue<>(3);
        System.out.println(blockingQueue.offer("a", 2, TimeUnit.SECONDS));
        System.out.println(blockingQueue.offer("a", 2, TimeUnit.SECONDS));
        System.out.println(blockingQueue.offer("a", 2, TimeUnit.SECONDS));
        System.out.println(blockingQueue.offer("a", 2, TimeUnit.SECONDS));

    }

    private static void test3() throws InterruptedException {
        BlockingQueue<String> blockingQueue = new ArrayBlockingQueue<>(3);
        //死站不退
        blockingQueue.put("a");
        blockingQueue.put("a");
        blockingQueue.put("a");
        blockingQueue.put("a");
        System.out.println("============================");

        blockingQueue.take();
        blockingQueue.take();
        blockingQueue.take();
        blockingQueue.take();
    }

    private static void test2() {
        BlockingQueue<String> blockingQueue = new ArrayBlockingQueue<>(3);
        System.out.println(blockingQueue.offer("a"));
        System.out.println(blockingQueue.offer("b"));
        System.out.println(blockingQueue.offer("c"));
        System.out.println(blockingQueue.offer("d"));

        System.out.println(blockingQueue.peek());

        System.out.println(blockingQueue.poll());
        System.out.println(blockingQueue.poll());
        System.out.println(blockingQueue.poll());
        System.out.println(blockingQueue.poll());
    }

    private static void test1() {
        //        List list = new ArrayList();
        BlockingQueue<String> blockingQueue=new ArrayBlockingQueue<String>(3);
        System.out.println(blockingQueue.add("a"));
        System.out.println(blockingQueue.add("b"));
        System.out.println(blockingQueue.add("c"));
        //Exception in thread "main" java.lang.IllegalStateException: Queue full 队列满了
//        System.out.println(blockingQueue.add("d"));
        System.out.println(blockingQueue.element());
        System.out.println(blockingQueue.remove());
        System.out.println(blockingQueue.remove());
        System.out.println(blockingQueue.remove());
        //Exception in thread "main" java.util.NoSuchElementException 队列中已经没有内容了
//        System.out.println(blockingQueue.remove());
    }

}
