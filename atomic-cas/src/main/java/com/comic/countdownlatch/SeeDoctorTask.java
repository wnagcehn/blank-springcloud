package com.comic.countdownlatch;

import java.util.concurrent.CountDownLatch;

/**
 * 看大夫任务
 *
 * @author wangchen
 * createAt 2019/7/10
 * updateAt 2019/7/10
 */
public class SeeDoctorTask implements Runnable {

    private CountDownLatch countDownLatch;

    //构造器
    public SeeDoctorTask(CountDownLatch countDownLatch){
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(3000);
            System.out.println("看大夫成功，大夫给开了些药单子");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            if(countDownLatch != null) {
                countDownLatch.countDown();
            }
        }
        System.out.println("再去给自己买根冰棍");
    }
}
 
