import java.util.concurrent.locks.ReentrantLock;

/*
问题：写一段java多线程代码，完成如下逻辑，同时启动3个线程从1开始打印递增数字，
 每次每个线程打印3次，打印到36程序结束，输出如下：

线程1：1
线程1：2
线程1：3

线程2：4
线程2：5
线程2：6

线程3：7
线程3：8
线程3：9

线程1：10
线程1：11
线程1：12

......

线程3：34
线程3：35
线程3：36
*/
public class T {

    private static volatile int num = 1;
    static Object obj = new Object();
    public static void main(String[] args)throws Exception{


//        new Thread(new RunnableTask(obj , 1)).start();
//        new Thread(new RunnableTask(obj , 2)).start();
//       new Thread(new RunnableTask(obj , 3)).start();

//        new Thread(() -> {
//            synchronized (obj) {
//                while (num < 36) {
//                    for (int i=0;i<3;i++) {
//                        System.out.println("线程1：" + num++);
//                    }
//                    obj.notifyAll();
//                    try {
//                        obj.wait();
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//                obj.notifyAll();
//            }
//        }).start();
//
//        new Thread(() -> {
//            synchronized (obj) {
//                while (num < 36) {
//                    for (int i=0;i<3;i++) {
//                        System.out.println("线程2：" + num++);
//                    }
//                    obj.notifyAll();
//                    try {
//                        obj.wait();
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//                obj.notifyAll();
//            }
//        }).start();
//
//        new Thread(() -> {
//            synchronized (obj) {
//                while (num < 36) {
//                    for (int i=0;i<3;i++) {
//                        System.out.println("线程3：" + num++);
//                    }
//                    obj.notifyAll();
//                    try {
//                        obj.wait();
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//                obj.notifyAll();
//            }
//        }).start();


    }

    static class RunnableTask implements Runnable {
        // num计数器
        private static volatile int num = 1;
        private final Object obj;
        // 线程id 1,2,3
        private int threadId;
        public RunnableTask(Object obj,int threadId){
            this.obj = obj;
            this.threadId = threadId;
        }

        @Override
        public void run() {
            while (num <= 36) {
                // 加synchronized同步锁
                synchronized(obj){
                    while (num /3 % 3 +1 != threadId) {
                        try {
                            // 阻塞线程
                            obj.wait();
                        }catch(Exception e){
                            System.err.println(String.format("异常:%s",e));
                        }
                    }

                    for (int i = 0 ; i < 3 ; i++,num++) {
                        System.out.println(String.format("线程%s：%s",threadId, num));
                   }
                    // 执行完成，就唤醒所有线程
                    obj.notifyAll();
                }

            }
        }

    }

}