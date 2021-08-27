package com.example.concurrent.callable;


import java.util.concurrent.*;

public class CallableExample {

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        // 1: 使用线程池方法调用Callable任务
        //callableExecutorService();
        //2: 使用FutureTask调用Callable任务
        callableFutureTask();

    }

    public static void callableFutureTask() throws InterruptedException, ExecutionException {
        CallableTask task = new CallableTask();
        FutureTask futureTask = new FutureTask(task);
        Thread t = new Thread(futureTask);
        t.start();
        Thread.sleep(1000L);
        System.out.println("task result:" + futureTask.get());
    }


    public static void callableExecutorService() throws InterruptedException, ExecutionException {
        ExecutorService service = new ThreadPoolExecutor(10, 10,
                60L, TimeUnit.SECONDS,
                new ArrayBlockingQueue(10));

        Future<Integer> future = service.submit(new CallableTask());
        Thread.sleep(3000);
        System.out.println("future is done?" + future.isDone());
        if (future.isDone()) {
            System.out.println("callableTask返回参数:"+future.get());
        }
        service.shutdown();
    }

    static class CallableTask implements Callable<Integer> {
        @Override
        public Integer call() {
            return ThreadLocalRandom.current().ints(0, (99 + 1)).limit(1).findFirst().getAsInt();
        }
    }
}
