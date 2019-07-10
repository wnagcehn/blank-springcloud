package com.comic.countdownlatch;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 配媳妇去看病，轮到媳妇看大夫时,我就开始去排队准备交钱了。
 *
 * @author wangchen
 * createAt 2019/7/10
 * updateAt 2019/7/10
 */
public class MainClient {

    public static void main(String[] args) throws InterruptedException {
        long current = System.currentTimeMillis();
        ExecutorService executor = Executors.newFixedThreadPool(2);
        CountDownLatch countDownLatch = new CountDownLatch(2);
        executor.execute(new SeeDoctorTask(countDownLatch));
        executor.execute(new QueueTask(countDownLatch));
        countDownLatch.await();
        System.out.println("over，回家 cost:"+(System.currentTimeMillis()-current));
        executor.shutdown();
    }

}
 
