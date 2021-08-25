package com.example.concurrent.thread.status;


public class ThreadStatusExample_Sleep {

    private static volatile boolean running = true;

    public static void main(String[] args) throws Throwable{
        Thread t = new Thread(() -> {
            while (running) {}
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        System.out.println("创建线程后状态:" + t.getState());

        t.start();
        System.out.println("Thread.start()之后的状态：" + t.getState());

        // volatile boolean标志的变量设置为false，让子线程退出循环
        running = false;

        Thread.sleep(1000L);
        System.out.println("Threa.sleep之后的状态：" + t.getState());




    }


}
