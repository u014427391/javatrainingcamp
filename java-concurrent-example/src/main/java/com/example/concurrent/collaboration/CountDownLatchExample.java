package com.example.concurrent.collaboration;

import java.util.Random;
import java.util.concurrent.CountDownLatch;

/**
 * <pre>
 *      CountDownLatch例子
 * </pre>
 *
 * <pre>
 * @author mazq
 * 修改记录
 *    修改后版本:     修改人：  修改日期: 2021/11/15 18:17  修改内容:
 * </pre>
 */
public class CountDownLatchExample {

    public static void main(String[] args) {

        final CountDownLatch cdl = new CountDownLatch(1);

        int concurrency = 100;
        final Random random = new Random();
        for (int i = 0; i < concurrency; i++) {
            new Thread(()->{
                try {
                    Thread.sleep(random.nextInt(10_000));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + "准备就绪");
                // 让并发线程都等待发出信号
                try {
                    cdl.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + "开始工作");
            }).start();
        }
        System.out.println("******************** 发出开始信号***********");
        cdl.countDown();
    }
}
