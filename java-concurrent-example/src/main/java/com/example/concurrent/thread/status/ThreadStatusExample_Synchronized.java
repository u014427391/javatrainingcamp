package com.example.concurrent.thread.status;


public class ThreadStatusExample_Synchronized {

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> {
           synchronized (ThreadStatusExample_Synchronized.class) {
               System.out.println("t1抢到锁");
           }
        });

        synchronized (ThreadStatusExample_Synchronized.class) {
            t1.start();
            Thread.sleep(1000L);
            System.out.println("t1抢不到锁时的状态："+t1.getState());
        }
    }
}
