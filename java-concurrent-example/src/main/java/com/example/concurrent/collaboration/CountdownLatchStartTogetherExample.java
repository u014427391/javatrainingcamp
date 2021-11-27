package com.example.concurrent.collaboration;


import java.util.Random;
import java.util.concurrent.CountDownLatch;

public class CountdownLatchStartTogetherExample {

    public static void main(String[] args) throws InterruptedException {
        final CountDownLatch cdl = new CountDownLatch(1);
        int concurrency = 100;
        final CountDownLatch cdln = new CountDownLatch(concurrency);
        final Random random = new Random();
        for (int i = 0;i < concurrency; i++) {
            new Thread(()->{
                try {
                    Thread.sleep(random.nextInt(10_000));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(
                        Thread.currentThread().getName() + " 准备就绪");
                // 调用countDown()报告完成任务
                cdln.countDown();
                // 让所有线程都等待发出信号
                try {
                    cdl.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(
                        Thread.currentThread().getName() + " 开始工作");
            }).start();
        }
        //等待准备完成
        cdln.await();
        System.out.println("******************** 发出开始信号***********");
        cdl.countDown();
    }
}
