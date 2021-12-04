package com.example.concurrent.acomic;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

/**
 * <pre>
 *      AtomicFieldUpdater example
 * </pre>
 * <p>
 * <pre>
 * @author mazq
 * 修改记录
 *    修改后版本:     修改人：  修改日期: 2021/12/04 18:45  修改内容:
 * </pre>
 */
public class AtomicFieldUpdaterExample {

    static class ParentBean {
        volatile int count;
    }

    static class DemoBean extends ParentBean {
        volatile int count;
        public DemoBean (){
            this.count = 0;
        }
        public int getCount (){
            return count;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        DemoBean obj = new DemoBean();
        AtomicIntegerFieldUpdater<DemoBean> atomicIntegerFieldUpdater =
                AtomicIntegerFieldUpdater.newUpdater(DemoBean.class , "count");
        int threadSize = 50;
        CountDownLatch countDownLatch = new CountDownLatch(threadSize);
        for (int t = 0 ; t < threadSize ; t++) {
            new Thread(()->{
                for (int i = 0 ;i < 10_000; i ++) {
                    atomicIntegerFieldUpdater.incrementAndGet(obj);
                }
                countDownLatch.countDown();
            }).start();
        }
        countDownLatch.await();
        System.out.println("统计计数："+obj.getCount());
    }


}
