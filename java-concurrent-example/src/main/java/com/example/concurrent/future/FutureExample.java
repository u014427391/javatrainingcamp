package com.example.concurrent.future;

import java.util.Random;
import java.util.concurrent.*;

/**
 * <pre>
 *      Future例子
 * </pre>
 * <p>
 * <pre>
 * @author mazq
 * 修改记录
 *    修改后版本:     修改人：  修改日期: 2021/08/28 17:11  修改内容:
 * </pre>
 */
public class FutureExample {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService service = new ThreadPoolExecutor(10, 10,
                60L, TimeUnit.SECONDS,
                new ArrayBlockingQueue(10));

        Future<Integer> future = service.submit(new CallableTask());
        System.out.println(future.get());

        service.shutdown();
    }

    static class CallableTask implements Callable<Integer> {
        @Override
        public Integer call() throws Exception{
            Thread.sleep(1000L);
            return new Random().nextInt();
        }
    }
}
