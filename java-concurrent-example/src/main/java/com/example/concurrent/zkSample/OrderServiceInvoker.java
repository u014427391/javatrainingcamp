package com.example.concurrent.zkSample;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.concurrent.locks.Lock;

@Service
@Slf4j
public class OrderServiceInvoker implements OrderService{


    @Override
    public void createOrder() {
        Lock zkLock = new ZKDistributeLock("/zk-test");
        String orderCode = null;
        try {
            zkLock.lock();
            orderCode = OrderCodeGenerator.generatorOrderCode();

        } finally {
            zkLock.unlock();
        }
        System.out.println(String.format("thread name : %s , orderCode : %s" ,
                Thread.currentThread().getName(),
                orderCode));
    }

}
