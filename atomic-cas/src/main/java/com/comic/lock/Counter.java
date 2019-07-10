package com.comic.lock;

/**
 * @author wangchen870
 * createAt 2019/6/26
 * updateAt 2019/6/26
 */
public class Counter {

    volatile int i = 0;

    public void add(){
        i++;
    }

}
 
