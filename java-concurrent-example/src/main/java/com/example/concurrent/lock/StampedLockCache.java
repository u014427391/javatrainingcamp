package com.example.concurrent.lock;

import org.springframework.util.StringUtils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.StampedLock;

/**
 * <pre>
 *      StampedLock 缓存
 * </pre>
 *
 * <pre>
 * @author mazq
 * 修改记录
 *    修改后版本:     修改人：  修改日期: 2021/12/03 17:20  修改内容:
 * </pre>
 */
public class StampedLockCache {

    private final Map<Integer , String> cache = new ConcurrentHashMap<>();
    private final StampedLock stampedLock = new StampedLock();

    public void put(Integer key , String value) {
        // 上写锁
        long stamp = stampedLock.writeLock();
        try {
            cache.put(key , value);
        } finally {
            // 释放写锁
            stampedLock.unlockWrite(stamp);
        }
    }

    public String get(Integer key) {
        // 先获取乐观锁
        long stamp = stampedLock.tryOptimisticRead();
        // 先尝试通过乐观锁方式读取数据
        String value = cache.get(key);
        // 校验是否被其它线程修改过，true：表示未修改 false：修改过，表示需要加悲观锁
        if (!stampedLock.validate(stamp)) {
            // 上悲观锁
            stamp = stampedLock.readLock();
            try {
                value = cache.get(key);
            } finally {
                stampedLock.unlock(stamp);
            }
        }
        return value;
    }

    public String putIfNotExist(Integer key , String value) {
        long stamp = stampedLock.readLock();
        String currentValue = cache.get(key);
        try {
            while (StringUtils.isEmpty(currentValue)) {
                // 尝试锁升级，读锁升级为写锁
                long wstamp = stampedLock.tryConvertToWriteLock(stamp);
                if (wstamp != 0L) { // 不为0表示锁升级成功
                    stamp = wstamp;
                    currentValue = value;
                    // 数据写到缓存里
                    cache.put(key , value);
                    break;
                }
                else { // 锁升级失败
                    // 释放读锁
                    stampedLock.unlockRead(stamp);
                    stamp = stampedLock.writeLock();
                }
            }
        }finally {
            // 释放所有的锁
            stampedLock.unlock(stamp);
        }
        return currentValue;
    }

}
