package com.example.concurrent.threadpool;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * <pre>
 *  线程池使用例子
 * </pre>
 * <p>
 * <pre>
 * @author mazq
 * 修改记录
 *    修改后版本:     修改人：  修改日期: 2021/08/27 16:32  修改内容:
 * </pre>
 */
public class ThreadPoolExample {

    public static void main(String[] args) {
        ExecutorService service = new ThreadPoolExecutor(2, 5,
                60L, TimeUnit.SECONDS,
                new ArrayBlockingQueue(5));
        service.execute(() ->{
            System.out.println(String.format("thread name:%s",Thread.currentThread().getName()));
        });
        // 避免内存泄露，记得关闭线程池
        service.shutdown();


    }
}
