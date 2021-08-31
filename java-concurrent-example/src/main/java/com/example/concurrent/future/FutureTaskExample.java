package com.example.concurrent.future;

import java.util.Random;
import java.util.concurrent.*;

/**
 * <pre>
 *      FutureTask例子
 * </pre>
 * <p>
 * <pre>
 * @author mazq
 * 修改记录
 *    修改后版本:     修改人：  修改日期: 2021/08/28 18:04  修改内容:
 * </pre>
 */
public class FutureTaskExample {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        FutureTask futureTask = new FutureTask(new CallableTask());
        Thread t = new Thread(futureTask);
        t.start();
        System.out.println(futureTask.get());

    }

    static class CallableTask implements Callable<Integer> {
        @Override
        public Integer call() throws Exception{
            Thread.sleep(1000L);
            return new Random().nextInt();
        }
    }
}
