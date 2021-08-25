package com.example.concurrent;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.*;

@SpringBootTest
class JavaConcurrentExampleApplicationTests {

    private volatile boolean stop = false;

    @Test
    void contextLoads() throws Exception{

        ExecutorService service = new ThreadPoolExecutor(10, 10,
                60L, TimeUnit.SECONDS,
                new ArrayBlockingQueue(10));
//        service.execute(() ->{
//            System.out.println(String.format("thread name:%s",Thread.currentThread().getName()));
//        });

        Future<Integer> future = service.submit(new CallableTask());
        Thread.sleep(3000);
        System.out.println("future is done?" + future.isDone());
        if (future.isDone()) {
            System.out.println("callableTask返回参数:"+future.get());
        }

        new Thread(() ->{
            System.out.println(Thread.currentThread().getName());
        }).start();

        new Thread(() -> {
            System.out.println("runable run.");
        }) {
            @Override
            public void run() {
                System.out.println(String.format("thread name %s is run.", Thread.currentThread().getName()));
            }
        }.start();



    }

    public void doWork() {
        Thread t = new Thread(() -> {
            while (!stop) {
                // do someting
            }
        });
    }

    public void doStop() {
        stop = true;
    }

    static class CallableTask implements Callable<Integer>{
        @Override
        public Integer call() {
            return ThreadLocalRandom.current().ints(0, (99 + 1)).limit(1).findFirst().getAsInt();
        }
    }



}
