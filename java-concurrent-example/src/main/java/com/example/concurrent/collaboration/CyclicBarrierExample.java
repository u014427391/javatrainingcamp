package com.example.concurrent.collaboration;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * <pre>
 *      CyclicBarrier 例子
 * </pre>
 *
 * <pre>
 * @author mazq
 * 修改记录
 *    修改后版本:     修改人：  修改日期: 2021/11/26 11:13  修改内容:
 * </pre>
 */
public class CyclicBarrierExample {

    public static void main(String[] args) {
        int concurrency = 100;
        final CyclicBarrier cyclicBarrier = new CyclicBarrier(concurrency , ()->{
            System.out.println("*****************准备完成！************");
        });
        final Random random = new Random();
        for (int i = 0 ; i < concurrency; i++) {
            new Thread(() -> {
                try {
                    Thread.sleep(random.nextInt(10_000));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                System.out.println(Thread.currentThread().getName() + "准备就绪");
                try {
                    cyclicBarrier.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
                System.out.println(
                        Thread.currentThread().getName() + " 开始工作....");
            }).start();
        }
    }
}
