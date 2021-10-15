package com.example.concurrent.forkjoin;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

/**
 * <pre>
 *      大数组排序
 * </pre>
 * <p>
 * <pre>
 * @author mazq
 * 修改记录
 *    修改后版本:     修改人：  修改日期: 2021/10/12 17:04  修改内容:
 * </pre>
 */
public class ArraySortTask extends RecursiveAction{

    final long[] array; final int lo, hi;
    ArraySortTask(long[] array, int lo, int hi) {
        this.array = array; this.lo = lo; this.hi = hi;
    }
    ArraySortTask(long[] array) { this(array, 0, array.length); }

    @Override
    protected void compute() {
        if (hi - lo < THRESHOLD)
            // 少于阀值,使用Arrays.sort 快排
            sortSequentially(lo, hi);
        else {
            /* 归并排序 */
            // 取中间值
            int mid = (lo + hi) >>> 1;
            // 拆分任务
            invokeAll(new ArraySortTask(array, lo, mid),
                    new ArraySortTask(array, mid, hi));
            // 归并结果
            merge(lo, mid, hi);
        }
    }

    // implementation details follow:
    static final int THRESHOLD = 1000;
    void sortSequentially(int lo, int hi) {
        Arrays.sort(array, lo, hi);
    }

    void merge(int lo, int mid, int hi) {
        long[] buf = Arrays.copyOfRange(array, lo, mid);
        for (int i = 0, j = lo, k = mid; i < buf.length; j++)
            array[j] = (k == hi || buf[i] < array[k]) ?
                    buf[i++] : array[k++];
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        int size = 10_000;
        long[] array = new long[size];
        Random random = new Random();
        for (int i = 0; i< size; i++) {
            array[i] = random.nextInt();
        }

        ForkJoinPool forkJoinPool = new ForkJoinPool();
        ArraySortTask task = new ArraySortTask(array , 0 , size);
        forkJoinPool.submit(task);
        task.get();

        for (long a : array) {
            System.out.println(a);
        }
    }
}
