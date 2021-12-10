package com.example.concurrent.zkSample;

import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.exception.ZkNodeExistsException;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class ZKDistributeImproveLock implements Lock {

    private String localPath;
    private ZkClient zkClient;
    private String currentPath;
    private String beforePath;

    ZKDistributeImproveLock(String localPath) {
        super();
        this.localPath = localPath;
        zkClient = new ZkClient("localhost:2181");
        zkClient.setZkSerializer(new MyZkSerializer());
        if (!zkClient.exists(localPath)) {
            try {
                this.zkClient.createPersistent(localPath);
            } catch (ZkNodeExistsException e) {
            }
        }
    }

    @Override
    public void lock() {
        while (!tryLock()) {
            waitForLock();
        }
    }

    private void waitForLock() {
        CountDownLatch countDownLatch = new CountDownLatch(1);

        // 注册watcher
        IZkDataListener listener = new IZkDataListener() {
            @Override
            public void handleDataChange(String dataPath, Object data) throws Exception {
            }
            @Override
            public void handleDataDeleted(String dataPath) throws Exception {
                // 监听到锁释放，唤醒线程
                countDownLatch.countDown();
            }
        };
        zkClient.subscribeDataChanges(beforePath, listener);

        // 线程等待
        if (zkClient.exists(beforePath)) {
            try {
                countDownLatch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // 取消注册
        zkClient.unsubscribeDataChanges(beforePath , listener);

    }

    @Override
    public void unlock() {
        zkClient.delete(this.currentPath);
    }

    @Override
    public boolean tryLock() {
        if (this.currentPath == null) {
            currentPath = zkClient.createEphemeralSequential(localPath +"/" , "123");
        }
        // 获取Znode节点下面的所有子节点
        List<String> children = zkClient.getChildren(localPath);
        // 列表排序
        Collections.sort(children);
        if (currentPath.equals(localPath + "/" + children.get(0))) { // 当前节点是第1个节点
            return true;
        } else {
            //得到当前的索引号
            int index = children.indexOf(currentPath.substring(localPath.length() + 1));
            //取到前一个
            beforePath = localPath + "/" + children.get(index - 1);
        }
        return false;
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
