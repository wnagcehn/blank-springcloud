package com.comic;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.LockSupport;

/**
 * 手动实现reentranklock
 *
 * @author wangchen870
 * createAt 2019/6/26
 * updateAt 2019/6/26
 */
public class BlankReentrantlock implements Lock {

    //锁的拥有者
    AtomicReference<Thread> owner = new AtomicReference<>();

    //等待队列
    private LinkedBlockingQueue<Thread> waiters = new LinkedBlockingQueue<>();

    //标记加锁次数，实现重入
    AtomicInteger count = new AtomicInteger(0);

    @Override
    public boolean tryLock() {
        //判断count是否为0，若count！=0，说明锁被占用
        int ct = count.get();
        if (ct != 0){
            //判断owner是否是自己，若是count+=1
            //若不是，抢锁失败，返回false
            if (owner.get() == Thread.currentThread()){
                count.set(ct+1);
                return true;
            }
        }else {
            //若count==0，说明锁没被占用，用CAS(0,1)来抢锁
            if (count.compareAndSet(ct,ct+1)){
                owner.set(Thread.currentThread());
            }
        }

        return false;
    }

    @Override
    public void lock() {
        if (!tryLock()){
            waiters.offer(Thread.currentThread());

            for (;;){
                //取到等待队列的头部线程，但不移除
                Thread head = waiters.peek();
                //如果当前线程是头部线程
                if (head == Thread.currentThread()){
                    //若失败，将线程挂起，成功将线程取出并返回
                    if (!tryLock()){
                        LockSupport.park();
                    }else {
                        waiters.poll();
                        return;
                    }
                }else {
                    LockSupport.park();
                }
            }
        }
    }

    public boolean tryUnlock(){
        //判断当前线程是不是owner
        if (owner.get() != Thread.currentThread()){
            throw new IllegalStateException();
        }else {
            //若是当前线程，count-1
            int ct = count.get();
            int nextc = ct - 1;
            count.set(nextc);
            //如果count=0就解锁成功，owner设置成null
            //如果count！=0就解锁失败
            if (nextc == 0){
                owner.compareAndSet(Thread.currentThread(),null);
                return true;
            }else {
                return false;
            }
        }
    }

    @Override
    public void unlock() {
        //解锁成功，唤醒头部线程
        if (tryUnlock()){
            Thread th = waiters.peek();
            if (null != th){
                LockSupport.unpark(th);
            }
        }
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {

    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return false;
    }

    @Override
    public Condition newCondition() {
        return null;
    }
}
 
