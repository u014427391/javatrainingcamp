package com.example.concurrent.volatiledemo;


public class SingletonInstance {

    private static volatile SingletonInstance instance;

    public static SingletonInstance getInstance() {
        if (instance == null) {
            synchronized (SingletonInstance.class) {
                if (instance == null) {
                    instance = new SingletonInstance();
                }
            }
        }
        return instance;
    }
}
