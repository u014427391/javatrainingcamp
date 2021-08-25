package com.example.concurrent.thread.status;

import java.util.concurrent.locks.LockSupport;

public class ThreadStatusExample_Park {
    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> {
            LockSupport.park();
        });
        t1.start();
        Thread.sleep(2000L);
        System.out.println("t1 LockSupport.park()之后的状态："+t1.getState());

        LockSupport.unpark(t1);
    }
}
