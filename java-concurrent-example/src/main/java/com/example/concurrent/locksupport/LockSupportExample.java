package com.example.concurrent.locksupport;

import java.util.concurrent.locks.LockSupport;

public class LockSupportExample {
    private static final int total = 10;
    private  static int i = 0;
    static Thread t1 , t2;
    public static void main(String[] args) {
        t1 = new Thread(() ->{
            while (i < total) {
                System.out.println("t1:" + (++i));
                LockSupport.unpark(t2);
                LockSupport.park();
            }
        });

        t2 = new Thread(() -> {
            while (i < total) {
                LockSupport.park();
                System.out.println("t2:" + (++i));
                LockSupport.unpark(t1);
            }
        });

        t1.start();
        t2.start();
    }
}
