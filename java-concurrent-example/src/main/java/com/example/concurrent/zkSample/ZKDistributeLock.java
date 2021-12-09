package com.example.concurrent.zkSample;


import lombok.extern.slf4j.Slf4j;
import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.exception.ZkNodeExistsException;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

@Slf4j
public class ZKDistributeLock implements Lock {

    private String localPath;
    private ZkClient zkClient;

    ZKDistributeLock(String localPath) {
        super();
        this.localPath = localPath;
        zkClient = new ZkClient("localhost:2181");
        zkClient.setZkSerializer(new MyZkSerializer());

    }

    @Override
    public void lock() {
        if (!tryLock()) {
            waitForLock();
            lock();
        }
    }

    private void waitForLock() {
        CountDownLatch countDownLatch = new CountDownLatch(1);

        IZkDataListener listener = new IZkDataListener() {
            @Override
            public void handleDataChange(String path, Object o) throws Exception {
                System.out.println("zookeeper data has change!!!");
            }

            @Override
            public void handleDataDeleted(String s) throws Exception {
                // System.out.println("zookeeper data has delete!!!");
                countDownLatch.countDown();
            }
        };
        zkClient.subscribeDataChanges(localPath , listener);

        if (zkClient.exists(localPath)) {
            try {
                countDownLatch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        zkClient.unsubscribeDataChanges(localPath , listener);

    }

    @Override
    public void unlock() {
        zkClient.delete(localPath);
    }

    @Override
    public boolean tryLock() {
        try {
            zkClient.createEphemeral(localPath);
        } catch (ZkNodeExistsException e) {
            return false;
        }
        return true;
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return false;
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {
    }

    @Override
    public Condition newCondition() {
        return null;
    }
}
