package com.example.concurrent;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
public class JavaConcurrentExampleApplication {

    public static void main(String[] args) throws Exception{
        SpringApplication.run(JavaConcurrentExampleApplication.class, args);
        Thread t  = new Thread(new RunnableTask ());
        t.start();

        Runnable runnable = new Runnable() {
            /**
             * run方法是不允许在声明throws Exception的，
             * 且run方法内无法 throw 出 checked Exception，
             * 除非使用try catch进行处理
             */
            @Override
            public void run() {
                try {
                    throw new IOException();
                }catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
    }

    //public  static void main(String[] args) {

    //}
    static class RunnableTask implements Runnable {
        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName());
        }
    }

}
