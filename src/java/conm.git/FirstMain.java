package conm.git;
/*
 * @auther longguanghai
 * @date 2020-06-13 20:59
 * @update 2020-06-13 20:59
 */

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class FirstMain {
//    public static void main(String[] args) {
//        int threadCount = 10;
//
//        MyThread task = new MyThread(threadCount);
//        for (int i = 0; i < threadCount; i++) {
//            new Thread(task).start();
//        }
//
//        int poolCount = 5;
//        ExecutorService executorService = Executors.newCachedThreadPool();
//        try {
//            for (int i = 0; i < poolCount; i++) {
//                executorService.submit(task);
//            }
//            System.out.println(executorService.isTerminated());
//        }finally {
//            executorService.shutdown();
//        }
//
//    }

    public static void main(String[] args) {

        for (int i = 0; i < 10; i++) {
            MyTask myTask = new MyTask();
            FutureTask<Integer> task = new FutureTask<>(myTask);
            new Thread(task,(i+1)+"号窗口").start();
        }
    }


    static class MyTask implements Callable<Integer>{

        Integer count  = 1000;

        @Override
        public Integer call() throws Exception {
            TimeUnit.MICROSECONDS.sleep(10);
            while (count > 0){
                sale();
            }
            return count;
        }

        private synchronized void sale() {
            if (count-- > 0){
                System.out.println("第二次放票当前线程" +System.currentTimeMillis() + Thread.currentThread().getName()+"正在销售"+ (1000-count) + "张票");
                System.out.println("剩余"+ count + "张票");
            }
        }

    }

    static class MyThread implements Runnable{

        int count  = 1000; //票的张数

        Integer number;

        Lock lock = new ReentrantLock();

        public MyThread (Integer count){
            this.number = count;
        }

        @Override
        public void run() {

            while (count > 0){
                try {
                    TimeUnit.MICROSECONDS.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                lock.lock();
                if (count > 0){
                    System.out.println("当前线程" + Thread.currentThread().getName()+";线程号：" + number);
                    count --;
                    System.out.println("正在销售"+ (1000-count) + "张票");
                }
                lock.unlock();
            }
        }
    }




}
