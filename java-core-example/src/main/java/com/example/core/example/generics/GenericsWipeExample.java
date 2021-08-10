package com.example.core.example.generics;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * <pre>
 *      泛型擦除例子
 * </pre>
 *
 * <pre>
 * @author mazq
 * 修改记录
 *    修改后版本:     修改人：  修改日期: 2021/08/10 15:15  修改内容:
 * </pre>
 */
public class GenericsWipeExample {

    public static void main(String[] args) {
        Class<?> cls0 = A.class;
        Field[] fields = cls0.getDeclaredFields();
        for (Field field : fields) {
            System.out.println(field.getName() + ":" + field.getType().getSimpleName());
        }


        Class<?> cls1 = A1.class;
        Field[] fields1 = cls1.getDeclaredFields();
        for (Field field : fields1) {
            System.out.println(field.getName() + ":" + field.getType().getSimpleName());
        }

        Class<?> cls2 = A2.class;
        Method[] methods = cls2.getDeclaredMethods();
        for (Method method : methods) {
            System.out.println(method.getName() + ":" + method.getReturnType().getSimpleName());
        }

        Class<?> cls3 = A3.class;
        Method[] methodss = cls3.getDeclaredMethods();
        for (Method method : methodss) {
            System.out.println(method.getName() + ":" + method.getReturnType().getSimpleName());
        }
    }


    static class A <T>{
        T t;
    }

    static  class A1<T extends Number> {
        T t;
    }

    static class A2<T extends Number> {
        T t;
        public <K extends Number> K test(K k) {
            return null;
        }
    }

    interface MyInter<T>{
        T test();
    }
    class A3 implements MyInter<String> {
        @Override
        public String test() {
            return null;
        }
    }

}
