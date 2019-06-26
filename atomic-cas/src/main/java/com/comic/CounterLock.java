package com.comic;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author wangchen870
 * createAt 2019/6/26
 * updateAt 2019/6/26
 * 使用reentranklock
 */
public class CounterLock {

    volatile int i = 0;

    Lock lock = new BlankReentrantlock();

    public void add(){
        lock.lock();

        i++;

        lock.unlock();
    }

}
 
