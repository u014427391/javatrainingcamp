package com.example.concurrent.zkSample;


import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class ConcurrentDistributeTest {

    public static void main(String[] args) {
        // 多线程数
        int threadSize = 20;
        // 创建多线程循环屏障
        CyclicBarrier cyclicBarrier = new CyclicBarrier(threadSize , ()->{
            System.out.println("准备完成！");
        }) ;

        // 模拟分布式集群的场景
        for (int i = 0 ; i < threadSize ; i ++) {
            new Thread(()->{
                OrderService orderService = new OrderServiceInvoker();
                // 所有线程都等待
                try {
                    cyclicBarrier.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
                // 模拟并发请求
                orderService.createOrder();
            }).start();
        }
    }
}
