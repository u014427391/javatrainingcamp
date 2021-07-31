package com.example.datastructure.array;


public class MyArray {

    private int[] a;
    private int n;

    public MyArray() {
        a = new int[16];
        n = 0;
    }

    public void initialize() {
        for (int i = 0 ; i <= 10; i++) {
            a[i] = i;
        }
        n = 10;
    }

    public void print() {
        for (int i = 0 ; i < n ; i++) {
            System.out.print(a[i] + " ");
        }
        // 换行
        System.out.println();
    }

    public void set(int position , int value) {
        a[position] = value;
    }

    public int get(int position) {
        return a[position];
    }

    public void put(int position , int value) {
        for (int i = n ; i >= position ; i--) {
            a[i+1] = a[i];
        }
        a[position] = value;
        n += 1;
    }

    public void remove(int position) {
        for (int i = position ; i < n ; i ++ ) {
            a[i] = a[i+1];
        }
        n -= 1;
    }

    public static void main(String[] args) {
        MyArray array = new MyArray();
        // 初始化数据数据
        array.initialize();
        // 打印验证  0 1 2 3 4 5 6 7 8 9
        array.print();

        // 数组对应位置设置数据
        array.set(0 , 10);
        // 打印验证  10 1 2 3 4 5 6 7 8 9
        array.print();

        // 重新初始数组
        array.initialize();
        // 在对应位置附加上数据
        array.put(0 , 11);
        // 打印验证 11 1 2 3 4 5 6 7 8 9
        array.print();

        // 重新初始数组
        array.initialize();
        // 对应位置移除数组元素
        array.remove(0);
        // 打印验证 1 2 3 4 5 6 7 8 9
        array.print();

    }

}
