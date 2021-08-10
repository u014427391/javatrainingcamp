package com.example.core.example.generics;

import java.lang.reflect.Field;

/**
 * <pre>
 *      java泛型类例子
 * </pre>
 *
 * <pre>
 * @author mazq
 * 修改记录
 *    修改后版本:     修改人：  修改日期: 2021/08/10 11:08  修改内容:
 * </pre>
 */
public class GenericsClassExample {

    public static void main(String[] args) {
        A<String , Integer> a0 = new A<>();
        a0.t= "testStr";
        A<Integer , Integer> a1 = new A<>();
        a1.t = 100;
         //  泛型参数在使用时只能引用数据类型，不能是基本数据类型，比如int等等
        //A<Integer , int> a2 = new A<>();

        // 泛型类如果不指定具体的参数类型，默认数据类型是object
        Class<A> cls = A.class;
        Field[] fields = cls.getDeclaredFields();
        for (Field field : fields) {
            System.out.println(field.getName() + ":" + field.getType().getSimpleName());
        }

        // 泛型类在具体实现上可以看出不同数据类型，实践上是相同的数据类型，可以通过hashcode进行验证
        System.out.println(a0.getClass().hashCode());
        System.out.println(a1.getClass().hashCode());
    }

    static class A <T , E> {
        public T t;
        public void test0(T  t) {

        }
    }

}
