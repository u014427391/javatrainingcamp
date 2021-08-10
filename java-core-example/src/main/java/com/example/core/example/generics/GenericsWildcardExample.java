package com.example.core.example.generics;

/**
 * <pre>
 *      泛型通配符例子
 * </pre>
 *
 * <pre>
 * @author mazq
 * 修改记录
 *    修改后版本:     修改人：  修改日期: 2021/08/10 14:37  修改内容:
 * </pre>
 */
public class GenericsWildcardExample {

    public static void main(String[] args) {
        A<String> a0 = new A<>();
        show(a0);

        A<Integer> a1 = new A<>();
        show(a1);

        A<A1> a11 = new A<>();
        A<A2> a12 = new A<>();
        A<A3> a13 = new A<>();

        // 只能使用A2及其子类
        show0(a12);
        show0(a13);

        // 只能使用A2及其父类
        show1(a11);
        show1(a12);

    }

    public static void show(A<?> a){
        System.out.println(a.getClass());
    }

    public static void show0(A<? extends A2> a) {}

    public static void show1(A<? super A2> a){}

    static class A<T> {

    }

    static class A1{}

    static class A2 extends A1{}

    static class A3 extends A2{}
}
