package com.example.concurrent.zkSample;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

public class OrderCodeGenerator {

    private static final String DATE_FORMAT = "yyyyMMddHHmmss";
    private static AtomicInteger ai  = new AtomicInteger(0);;

    private static ThreadLocal<SimpleDateFormat> threadLocal = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat(DATE_FORMAT);
        }
    };

    public static DateFormat getDateFormat() {
        return (DateFormat) threadLocal.get();
    }

    public static String generatorOrderCode() {
        try {
            return getDateFormat().format(new Date(System.currentTimeMillis()))
                    + ai.getAndIncrement();
        } finally {
            threadLocal.remove();
        }
    }

    public static void main(String[] args) {
        System.out.println(new OrderCodeGenerator().generatorOrderCode());
    }
}
