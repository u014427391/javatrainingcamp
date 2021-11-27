package com.example.concurrent.collaboration;


import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class CyclicBarrierMultipleStartTogetherExample {

    static int p = 0;
    public static void main(String[] args) {
        int parties = 3;
        CyclicBarrier cyclicBarrier = new CyclicBarrier(parties ,()->{
            switch (p) {
                case 0:
                    System.out.println("******************第一阶段，大家都到公司了，出发去公园！");
                    break;
                case 1:
                    System.out.println("******************第二阶段：大家都到公园大门，出发去餐厅！");
                    break;
                case 2:
                    System.out.println("******************第三阶段：大家都到餐厅了，开始用餐！");
                    break;
            }
            p ++;
        });

        final Random random = new Random();
        for (int i = 0; i < parties ;i++) {
            new Thread(() ->{
                String staff = "员工【" + Thread.currentThread().getName()
                        + "】";

                /* 第一阶段任务：从家出发，到公司 */
                System.out.println(staff + "从家出发了...");
                try {
                    Thread.sleep(random.nextInt(5000));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(staff + "到达公司！");

                // 协同，等待所有线程都到达
                try {
                    cyclicBarrier.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }

                /* 第二阶段任务：去公园玩，再到公园大门口集合 */
                System.out.println(staff + "出发去公园玩...");
                try {
                    Thread.sleep(random.nextInt(5000));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(staff + "到达公园大门集合！");

                // 协同，第二次等待所有线程都到达
                try {
                    cyclicBarrier.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }


                /* 第三阶段任务：去餐厅 */
                System.out.println(staff + "出发去餐厅...");
                // 经过一段时间后，到达餐厅
                try {
                    Thread.sleep(random.nextInt(5000));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(staff + "到达餐厅！");


                // 协同，第三次等待所有线程都到达
                try {
                    cyclicBarrier.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }


                /* 第四阶段任务：就餐 */
                System.out.println(staff + "开始用餐...");
                try {
                    Thread.sleep(random.nextInt(5000));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                // 吃完饭回家了
                System.out.println(staff + "回家了...");

            }).start();
        }
    }

}
