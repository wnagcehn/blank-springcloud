package com.comic.lock;

/**
 * @author wangchen870
 * createAt 2019/6/26
 * updateAt 2019/6/26
 */
public class Test {

    public static void main(String[] args) throws InterruptedException {
        CounterLock ct = new CounterLock();

        for (int i = 0; i < 10 ; i++){
            new Thread(){
                @Override
                public void run() {
                    for (int j = 0; j < 10000; j++){
                        ct.add();
                    }
                    System.out.println("done.......");
                }
            }.start();
        }

        Thread.sleep(5000);
        System.out.println(ct.i);
    }

}
 
