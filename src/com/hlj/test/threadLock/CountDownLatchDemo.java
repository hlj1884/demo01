package com.hlj.test.threadLock;

import com.hlj.test.enums.CountryEnum;

import java.util.concurrent.CountDownLatch;

public class CountDownLatchDemo {
    public static final int STU_COUNT=6;

    public static void main(String[] args) {
        closeDoor();
    }

    private static void YiTongLiuGuo() {
        CountDownLatch countDownLatch = new CountDownLatch(STU_COUNT);

        for (int i = 1; i <= 6; i++) {
            new Thread(()->{
                System.out.println(Thread.currentThread().getName()+"\t 国，被灭");
                countDownLatch.countDown();
            }, CountryEnum.forEach_CountryEnum(i).getRetMessage()).start();
        }
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            System.out.println(Thread.currentThread().getName()+"\t **********秦帝国，一统华夏");
        }

        System.out.println(CountryEnum.ONE);
        System.out.println(CountryEnum.ONE.getRetcode());
        System.out.println(CountryEnum.ONE.getRetMessage());
    }

    private static void closeDoor() {
        CountDownLatch countDownLatch = new CountDownLatch(STU_COUNT);

        for (int i = 1; i <= 6; i++) {
             new Thread(()->{
                 System.out.println(Thread.currentThread().getName()+"\t 上完自习，离开教室");
                 countDownLatch.countDown();
             },String.valueOf(i)).start();
        }
        try {
            //await();导致当前线程等到锁存器计数到零，除非线程是interrupted 。
            countDownLatch.await();
            System.out.println(Thread.currentThread().getName()+"\t 班长最后关门走人");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
        }
    }
}
