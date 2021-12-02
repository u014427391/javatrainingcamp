package com.example.concurrent.lock;

import java.util.LinkedList;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * <pre>
 *      juc condition lock
 * </pre>
 *
 * <pre>
 * @author mazq
 * 修改记录
 *    修改后版本:     修改人：  修改日期: 2021/12/02 14:10  修改内容:
 * </pre>
 */
public class ConditionLockQueue {

    private LinkedList<Object> queue;
    private volatile int capacity;
    private Condition productCondition = null;
    private Condition consumeCondition = null;
    private Lock lock;

    public ConditionLockQueue(int capacity) {
        this.queue = new LinkedList<Object>();
        this.capacity = capacity;
        lock = new ReentrantLock();
        this.productCondition  = lock.newCondition();
        this.consumeCondition = lock.newCondition();
    }

    public void put(Object obj) throws InterruptedException {
        lock.lock();
        try {
            while (queue.size() == capacity) {
                productCondition.await();
            }
            queue.add(obj);
            System.out.println("[Producer]: " + obj);
            consumeCondition.signal();
        } finally {
            lock.unlock();
        }
    }

    public Object sub() throws InterruptedException {
        Object obj = null;
        lock.lock();
        try {
            while (queue.size() == 0) {
                consumeCondition.await();
            }
            Object prod =  queue.remove(0);
            System.out.println("[Consumer]: " + prod);
            productCondition.signal();
        }finally {
            lock.unlock();
        }
        return obj;
    }

    public boolean isEmpty() {
        return (queue.size() == 0);
    }


    public static void main(String[] args) throws InterruptedException {
        Random random = new Random();
        ConditionLockQueue queue = new ConditionLockQueue(10);
        Runnable produceTask = () -> {
            for (; ;) {
                try {
                    queue.put(random.nextInt(10));
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        Runnable consumeTask = () -> {
            for (; ;) {
                try {
                    queue.sub();
                    Thread.sleep(random.nextInt(100));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        ExecutorService executorService = Executors.newFixedThreadPool(3);
        executorService.execute(produceTask);
        executorService.execute(consumeTask);
        executorService.shutdown();

    }

}
