package com.example.concurrent.thread.status;

public class ThreadStatusExample_Wait {

    public static void main(String[] args) throws InterruptedException {
        Object obj = new Object();
        Thread t1 = new Thread(() -> {
            synchronized (obj) {
                try {
                    obj.wait(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        });
        t1.start();
        Thread.sleep(1000L);


        synchronized (obj) {
            System.out.println("t1的状态:"+t1.getState());
            obj.notify();
            Thread.sleep(1000L);
            // 锁被抢了
            System.out.println("t1的状态："+t1.getState());
        }

    }
}
