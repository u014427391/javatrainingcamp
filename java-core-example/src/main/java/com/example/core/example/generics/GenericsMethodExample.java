package com.example.core.example.generics;

/**
 * <pre>
 *      使用泛型方法
 * </pre>
 *
 * <pre>
 * @author mazq
 * 修改记录
 *    修改后版本:     修改人：  修改日期: 2021/08/10 13:55  修改内容:
 * </pre>
 */
public class GenericsMethodExample {

    public static void main(String[] args) {
        A0 a0 = new A0();
        a0.test0("hello");

        A1<Integer> a1 = new A1<>();
        a1.test("hi", "hello");
        A1.test1("hello world");

    }

    static class A0 {
        public <T, E> T test0(T t) {
            System.out.println("t = " + t);
            return null;
        }
    }

    static class A1<K> {

        /**
         * 泛型方法的类型和泛型类一致时，以具体方法的为准
         * @param k
         * @param t
         */
        public <T, K> T test(T t , K k) {
            System.out.println("t = " + t);
            System.out.println("k = " + k);
            return null;
        }

        /**
         * static方法要使用泛型，必须指定为泛型方法加上{@code <K>}
         * @param k
         */
        public static <K> void test1(K k) {

        }
    }
}
