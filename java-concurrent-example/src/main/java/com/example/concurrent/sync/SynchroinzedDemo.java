package com.example.concurrent.sync;

public class SynchroinzedDemo {
    static int a;
    public static synchronized void add1(int b){
        a += b;
    }

    public synchronized void add2(int b){
        a += b;
    }

    public void add3(int b){
        synchronized (this){
            a += b;
        }
    }

    public static void main(String[] args) {

    }
}
