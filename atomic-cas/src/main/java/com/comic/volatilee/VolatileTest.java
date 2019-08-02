package com.comic.volatilee;

public class VolatileTest {

    private volatile static int n = 0;

    private static void add(){
        n++;
    }

    public static void main(String[] args){
        new Thread1().start();
        while (n<100){
//            System.out.println(n);
        }
        System.out.println(n);
        System.out.println("stop");
    }

    static class Thread1 extends Thread {

        @Override
        public void run(){
            try {
                Thread.sleep(1000);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
            for (int i = 0; i < 200; i++){
                add();
            }
        }
    }

}


