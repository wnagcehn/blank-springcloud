package com.comic.semaphore;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * 信号量类测试（可限制并发的线程数）
 *
 * @author wangchen
 * createAt 2019/7/10
 * updateAt 2019/7/10
 */
public class SemaphoreTest {

    public static void main(String[] args){
        //8个工人，5台机器
        int num = 8;
        Semaphore semaphore = new Semaphore(5);
        ExecutorService executor = Executors.newFixedThreadPool(8);
//        ExecutorService executor2 = Executors.newSingleThreadExecutor();
//        ExecutorService executor3 = Executors.newCachedThreadPool();
//        ExecutorService executor4 = Executors.newScheduledThreadPool(5);
        for (int i = 0; i < 8; i++){
            executor.execute(new Worker(i,semaphore));
        }
        executor.shutdown();
    }

    static class Worker implements Runnable {

        private int num;
        private Semaphore semaphore;
        public Worker(int num,Semaphore semaphore){
            this.num = num;
            this.semaphore = semaphore;
        }

        @Override
        public void run() {
            try{
                semaphore.acquire();
                System.out.println("工人" + num + "占用一个机器在生产...");
                Thread.sleep(2000);
                System.out.println("工人" + num + "释放出机器");
                semaphore.release();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

}
 
