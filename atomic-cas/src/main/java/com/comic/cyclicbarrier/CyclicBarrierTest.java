package com.comic.cyclicbarrier;

import java.util.concurrent.CyclicBarrier;

/**
 * @author wangchen
 * createAt 2019/7/10
 * updateAt 2019/7/10
 */
public class CyclicBarrierTest {

    static CyclicBarrier cb = new CyclicBarrier(2);

    public static void main(String[] args){
        new Thread(new Runnable() {

            @Override
            public void run() {
                try{
                    cb.await();
                }catch (Exception e){
                    e.printStackTrace();
                }
//                try {
//                    Thread.sleep(5000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
                System.out.println(1);
            }
        }).start();

        try{
            cb.await();
        }catch (Exception e){
            e.printStackTrace();
        }
        System.out.println(2);

    }

}
 
