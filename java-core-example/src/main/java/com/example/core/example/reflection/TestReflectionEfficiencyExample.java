package com.example.core.example.reflection;


import com.example.core.example.reflection.domain.User;

import java.lang.reflect.Method;
/**
 * <pre>
 *      测试反射性能效率问题
 * </pre>
 * <p>
 * <pre>
 * @author mazq
 * 修改记录
 *    修改后版本:     修改人：  修改日期: 2021/08/09 16:34  修改内容:
 * </pre>
 */
public class TestReflectionEfficiencyExample {

    private static final Integer TOTAL_COUNT = 1000000;

    public static void main(String[] args) throws Exception{
        test0();
        test1();
        test2();
    }

    public static void test0() {
        long start = System.currentTimeMillis();
        User user = new User();
        for (int i = 0 ; i < TOTAL_COUNT; i++) {
            user.hello();
        }
        System.out.println(String.format("传统调用方法执行时间:%d" , System.currentTimeMillis() - start));
    }

    public static void test1() throws Exception{
        long start = System.currentTimeMillis();
        Class<?> cls = Class.forName("com.example.core.example.reflection.domain.User");
        Object obj = cls.newInstance();
        Method hello = cls.getMethod("hello");
        for (int i = 0 ; i < TOTAL_COUNT; i++) {
            hello.invoke(obj);
        }
        System.out.println(String.format("反射方法调用执行时间:%d" , System.currentTimeMillis() - start));
    }

    public static void test2() throws Exception{
        long start = System.currentTimeMillis();
        Class<?> cls = Class.forName("com.example.core.example.reflection.domain.User");
        Object obj = cls.newInstance();
        Method hello = cls.getMethod("hello");
        hello.setAccessible(true);
        for (int i = 0 ; i < TOTAL_COUNT; i++) {
            hello.invoke(obj);
        }
        System.out.println(String.format("优化后的反射方法调用执行时间:%d" , System.currentTimeMillis() - start));
    }
}
