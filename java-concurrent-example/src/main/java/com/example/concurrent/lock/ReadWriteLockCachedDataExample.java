package com.example.concurrent.lock;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * <pre>
 *      ReadWriteLock锁降级的例子
 * </pre>
 *
 * <pre>
 * @author mazq
 * 修改记录
 *    修改后版本:     修改人：  修改日期: 2021/12/02 18:07  修改内容:
 * </pre>
 */
public class ReadWriteLockCachedDataExample {

    class Data {}
    class RWDictionary {
        private final Map<String, Data> map = new ConcurrentHashMap<>();
        private final ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();
        private final Lock rLock = readWriteLock.readLock();
        private final Lock wLock = readWriteLock.writeLock();
        public Data get(String key) {
            rLock.lock();
            try {
                return map.get(key);
            }finally {
                rLock.unlock();
            }
        }
        public Data put(String key , Data value) {
            wLock.lock();
            try {
                return map.put(key , value);
            }finally {
                wLock.unlock();
            }
        }
        public String[] allKeys() {
            rLock.lock();
            try {
                return (String[]) map.keySet().toArray();
            }
            finally {
                rLock.unlock();
            }
        }
        public void clear() {
            wLock.lock();
            try {
                map.clear();
            }
            finally {
                wLock.unlock();
            }
        }
    }
    class CachedData {
        Object data;
        volatile boolean cacheValid;
        final ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();

        void processCachedData() {
            rwl.readLock().lock();
            if (!cacheValid) {
                // Must release read lock before acquiring write lock
                rwl.readLock().unlock();
                rwl.writeLock().lock();
                try {
                    // Recheck state because another thread might have
                    // acquired write lock and changed state before we did.
                    if (!cacheValid) {
                        data = getData();
                        cacheValid = true;
                    }
                    // 【降级】Downgrade by acquiring read lock before releasing write lock
                    rwl.readLock().lock();
                }finally {
                    rwl.writeLock().unlock();
                }
            }

            try {
                use(data);
            } finally {
                rwl.readLock().unlock();
            }
        }

        private Object getData() {
            return null;
        }

        void use(Object data){}
    }

}
