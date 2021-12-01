package com.example.concurrent.lock;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.ReentrantLock;

/**
 * <pre>
 *      ReentrantLock example
 * </pre>
 *
 * <pre>
 * @author mazq
 * 修改记录
 *    修改后版本:     修改人：  修改日期: 2021/11/30 14:42  修改内容:
 * </pre>
 */
public class ReentrantLockCountExample {

    private static volatile int count = 0;
    static ReentrantLock lock = new ReentrantLock();

    public static void countHandler() {
        lock.lock();
        try {
            count++;
        }finally {
            lock.unlock();
        }
    }

    public static void doCountConcurrent() throws InterruptedException {
        int threads = 20;
        CountDownLatch cdl = new CountDownLatch(threads);
        for (int i = 0; i < threads; i++) {
            new Thread(() -> {
                for (int n = 0; n < 10000; n++) {
                    countHandler();
                }
                cdl.countDown();
            }).start();
        }
        try {
            cdl.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        long start = System.currentTimeMillis();
        doCountConcurrent();
        System.out.println("统计耗时:" + (System.currentTimeMillis() - start) + "ms");
        System.out.println("result:"+ ReentrantLockCountExample.count);
    }

}
