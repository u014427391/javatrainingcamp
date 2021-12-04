package com.example.concurrent.acomic;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

/**
 * <pre>
 *     AtomicInteger Example
 * </pre>
 * <p>
 * <pre>
 * @author mazq
 * 修改记录
 *    修改后版本:     修改人：  修改日期: 2021/12/04 17:41  修改内容:
 * </pre>
 */
public class AtomicIntegerExample {

    private static AtomicInteger atomicInteger = new AtomicInteger(0);
    private static volatile int count = 0;
    private static ReentrantLock reentrantLock = new ReentrantLock();

    public static Integer count() {
        return atomicInteger.getAndIncrement();
    }

    public static Integer countLock(){
        reentrantLock.lock();
        try {
            count ++;
        } finally {
            reentrantLock.unlock();
        }
        return count;
    }

    public static void main(String[] args) throws InterruptedException {
        int threadSize = 500;
        final CountDownLatch countDownLatch = new CountDownLatch(threadSize);
        long start = System.currentTimeMillis();
        for (int i = 0 ; i < threadSize ; i ++ ) {
            new Thread(() -> {
                for (int n = 0 ; n < 10_000 ; n++) {
                    //count();
                    countLock();
                }
                countDownLatch.countDown();
            }).start();
        }
        countDownLatch.await();
//        System.out.println("统计计数:" + atomicInteger.get());
        System.out.println("统计计数:" + count);
        System.out.println("耗时："+ (System.currentTimeMillis() - start) +"ms");
    }

}
