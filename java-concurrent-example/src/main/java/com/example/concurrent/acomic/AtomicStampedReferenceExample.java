package com.example.concurrent.acomic;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * <pre>
 *      AtomicStampedReference Example
 * </pre>
 * <p>
 * <pre>
 * @author mazq
 * 修改记录
 *    修改后版本:     修改人：  修改日期: 2021/12/04 19:43  修改内容:
 * </pre>
 */
public class AtomicStampedReferenceExample {

    private static AtomicInteger atomicInteger = new AtomicInteger(100);
    private static AtomicStampedReference atomicStampedReference = new AtomicStampedReference(100,0);

    public static void main(String[] args) throws InterruptedException {

        Thread t1 = new Thread(()->{
            atomicInteger.compareAndSet(100 , 101);
            atomicInteger.compareAndSet(101,100);
        });
        Thread t2 = new Thread(()->{
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
            }
            boolean flag = atomicInteger.compareAndSet(100,102);
            System.out.println(flag);
        });
        t1.start();
        t2.start();
        t1.join();
        t2.join();

        Thread tt1 = new Thread(()->{
            atomicStampedReference.compareAndSet(100 , 101,
                    atomicStampedReference.getStamp(), atomicStampedReference.getStamp() + 1);
            atomicStampedReference.compareAndSet(101, 100 ,
                    atomicStampedReference.getStamp() , atomicStampedReference.getStamp() + 1);
        });
        Thread tt2 = new Thread(()->{
            // 获取邮戳版本号
            int stamp = atomicStampedReference.getStamp();
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
            }
            boolean flag = atomicStampedReference.compareAndSet(100,102 ,
                    stamp , stamp+1);
            System.out.println(flag);
        });
        tt1.start();
        tt2.start();
        tt1.join();
        tt2.join();
    }
}
