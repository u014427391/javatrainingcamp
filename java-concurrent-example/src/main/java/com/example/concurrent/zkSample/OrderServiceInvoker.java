package com.example.concurrent.zkSample;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.concurrent.locks.Lock;

@Service
@Slf4j
public class OrderServiceInvoker implements OrderService{

    private Lock zkLock = new ZKDistributeLock();

    @Override
    public void createOrder() {
        zkLock.lock();
        try {
            String orderCode = OrderCodeGenerator.generatorOrderCode();
            if (log.isInfoEnabled()) {
                log.info("订单编码为:{}" , orderCode);
            }

        } finally {
            zkLock.unlock();
        }
    }
}
