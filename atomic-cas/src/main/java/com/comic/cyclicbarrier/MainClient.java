package com.comic.cyclicbarrier;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author wangchen
 * createAt 2019/7/10
 * updateAt 2019/7/10
 */
public class MainClient {

    public static void main(String[] args) throws Exception{

        CyclicBarrier cyclicBarrier = new CyclicBarrier(3,new TourGuideTask());
        ExecutorService executor = Executors.newFixedThreadPool(3);
        //赵峰最墨迹，到的最晚
        executor.execute(new TravelTask(cyclicBarrier,"赵峰",5));
        executor.execute(new TravelTask(cyclicBarrier,"王宸",3));
        executor.execute(new TravelTask(cyclicBarrier,"昊城",1));
        executor.shutdown();
    }

}
 
