package com.example.concurrent.thread.status;


public class ThreadStatusExample_Join {

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> {
            try {
                Thread.sleep(10000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread t2 = new Thread(() -> {
            try {
                //join使得线程串行执行
                System.out.println(" t2 中执行 t1.join(5000L)...");
                // t2等t1 5s
                t1.join(5000L);
                // t2等t1执行完成
                t1.join();
                System.out.println("t2执行完成");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        t1.start();
        t2.start();

        System.out.println("t2的状态："+ t2.getState());

        Thread.sleep(5000L);

        System.out.println("t2的状态："+ t2.getState());

    }


}
