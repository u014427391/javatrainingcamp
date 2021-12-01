package com.example.concurrent.lock;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.locks.ReentrantLock;

/**
 * <pre>
 *      ReentrantLock Example
 * </pre>
 *
 * <pre>
 * @author mazq
 * 修改记录
 *    修改后版本:     修改人：  修改日期: 2021/12/01 15:27  修改内容:
 * </pre>
 */
public class ReentrantLockExample {

    private static final ReentrantLock rlock = new ReentrantLock();
    private static int threadSize = 10;
    public static void main(String[] args) {
        ReentrantLock rlock = new ReentrantLock();
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        for (int i = 0; i< threadSize ; i++) {
            executorService.execute(new RunnableTask("Thread-"+i , rlock));
        }
        executorService.shutdown();
    }


    static class RunnableTask implements Runnable {
        private String threadName;
        private ReentrantLock rLock;
        RunnableTask(String threadName , ReentrantLock rLock) {
            this.threadName = threadName;
            this.rLock = rLock;
        }
        @Override
        public void run() {
            System.out.println("thread name "+threadName
            + "等待去获取锁");
            if (rLock.tryLock()) {
                try {
                    rLock.lock();
                    System.out.println("Thread " + threadName
                            + "抢到了锁");
                }finally {
                    rLock.unlock();
                }
            }
        }
    }

}
