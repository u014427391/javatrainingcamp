package com.example.concurrent.collaboration;

import java.util.Random;
import java.util.concurrent.Semaphore;

/**
 * <pre>
 *      Semaphore example
 * </pre>
 *
 * <pre>
 * @author mazq
 * 修改记录
 *    修改后版本:     修改人：  修改日期: 2021/11/30 14:16  修改内容:
 * </pre>
 */
public class SemaphoreExample {
    // 公平模式
    private Semaphore semaphore = new Semaphore(10, true);
    private final Random random = new Random();

    public void userCar() throws InterruptedException {
        System.out.println(Thread.currentThread().getName() + "需要用车");
        long start = System.currentTimeMillis();
        // 获取许可
        semaphore.acquire();
        System.out.println(Thread.currentThread().getName() + "租车成功，等待了:" +(System.currentTimeMillis() - start));
        try {
            Thread.sleep(random.nextInt(10_000));
        }catch (InterruptedException e) {
        } finally {
            System.out.println(Thread.currentThread().getName() + "还车了！");
            // 释放许可
            semaphore.release();
        }
    }

    public static void main(String[] args) {
        final Random ran = new Random();
        final SemaphoreExample semaphoreExample = new SemaphoreExample();
        for (int i = 0; i < 20 ; i++) {
            new Thread(()-> {
                try {
                    Thread.sleep(ran.nextInt(5_000));
                    semaphoreExample.userCar();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }
}
