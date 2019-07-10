package com.comic.lock;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author wangchen870
 * createAt 2019/6/26
 * updateAt 2019/6/26
 * 使用AtomicInteger
 */
public class CounterAtomic {

    AtomicInteger i = new AtomicInteger(0);

    public void add(){
        //自增，每次+1
        i.incrementAndGet();
    }

}
 
