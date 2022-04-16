package com.example.concurrent.threadlocal;

/**
 * <pre>
 *   InheritableThreadLocal  example
 * </pre>
 * <p>
 * <pre>
 * @author nicky.ma
 * 修改记录
 *    修改后版本:     修改人：  修改日期: 2022/04/16 20:16  修改内容:
 * </pre>
 */
public class InheritableThreadLocalSample {

    public static void main(String[] args) {
        ThreadLocal<String> t1 = new ThreadLocal<>();
        InheritableThreadLocal<String> t2 = new InheritableThreadLocal<>();
        t1.set("test1");
        t2.set("test2");

        new Thread(()->{
            System.out.println(String.format("获取ThreadLocal数据 %s" , t1.get()));
            System.out.println(String.format("获取InheritableThreadLocal数据 %s" , t2.get()));
        }).start();
    }
}
