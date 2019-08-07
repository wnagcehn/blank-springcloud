package com.comic.blank.rediscallback.service;
/*
 * Copyright (c) 2019
 * FileName: Lock.java
 * Author:   wangchen
 * Date:     2019年7月17日
 */


import com.comic.blank.rediscallback.Exception.LockCantObtainException;
import com.comic.blank.rediscallback.Exception.LockInsideExecutedException;
import com.comic.blank.rediscallback.LockRetryFrequency;

/**
 * redis分布式并发锁
 *
 * @author wangchen
 */
public interface Lock {

    /**
     * 功能描述: 一次性分布式并发锁，只获取一次锁，拿不到所直接返回失败
     *
     * @author wangchen
     * @date 2019年7月17日
     *
     * @param key
     *              锁的key, 通过这个key来唯一确认锁，建议
     * @param expireSeconds
     *              锁的最大过期时间， 因为是并发锁，为了杜绝因为获得锁而没有释放造成的问题
     * @param lockCallback
     *              获取锁过程中的一些callback方法
     * @return
     * @throws LockInsideExecutedException
     *              在获得锁后，在进行业务操作是发生的异常
     * @throws LockCantObtainException
     *              获取锁的timeout, 仍未获得锁
     */
    public abstract <T> T lock(String key, long expireSeconds, LockCallback<T> lockCallback)
            throws LockInsideExecutedException, LockCantObtainException;

    /**
     *
     * 功能描述:
     *          分布式并发锁 ,可重试多次拿锁,通过设置获取锁的超时时间(timeoutInSeconds)会根据常规频率(LockRetryFrequency.NORMAL)计算重试次数 </br>
     * 示例描述:
     *          timeoutInSeconds=1秒钟,计算得到的重试次数=10次
     *          timeoutInSeconds=5秒钟,计算得到的重试次数=50次
     *          timeoutInSeconds=10秒钟,计算得到的重试次数=100次
     *
     * @param key
     *            锁的key, 通过这个key来唯一确认锁，建议
     * @param timeoutInSeconds
     *            获取锁的timeout,说明：1秒等于重试10次
     * @param expireSeconds
     *            锁的最大过期时间， 因为是并发锁，为了杜绝因为获得锁而没有释放造成的问题
     * @param lockCallback
     *            获取锁过程中的一些callback方法
     * @return
     * @throws LockInsideExecutedException
     *             在获得锁后，在进行业务操作是发生的异常
     * @throws LockCantObtainException
     *             获取锁的timeout, 仍未获得锁
     */
    public abstract <T> T lock(String key, int timeoutInSeconds, long expireSeconds, LockCallback<T> lockCallback)
            throws LockInsideExecutedException, LockCantObtainException;

    /**
     *
     * 功能描述:
     *          分布式并发锁,可重试多次拿锁,通过设置获取锁的超时时间(timeoutInSeconds)和获取频率(frequency)计算重试次数 </br>
     * 示例描述:
     *          timeoutInSeconds=1秒,frequency=LockRetryFrequency.VERY_QUICK,计算得到的重试次数=100次
     *          timeoutInSeconds=1秒,frequency=LockRetryFrequency.QUICK,计算得到的重试次数=20次
     *          timeoutInSeconds=1秒,frequency=LockRetryFrequency.NORMAL,计算得到的重试次数=10次
     *          timeoutInSeconds=1秒,frequency=LockRetryFrequency.SLOW,计算得到的重试次数=2次
     *          timeoutInSeconds=1秒,frequency=LockRetryFrequency.VERY_SLOW,计算得到的重试次数=1次
     *
     * @param key
     *            锁的key, 通过这个key来唯一确认锁，建议
     * @param frequency
     *            获取锁的frequency,
     * @param timeoutInSeconds
     *            获取锁的timeout,
     * @param expireSeconds
     *            锁的最大过期时间， 因为是并发锁，为了杜绝因为获得锁而没有释放造成的问题
     * @param lockCallback
     *            一些了callback方法
     * @return
     * @throws LockInsideExecutedException
     *             在获得锁后，在进行业务操作是发生的异常
     * @throws LockCantObtainException
     *             获取锁的timeout, 仍未获得锁
     */
    public abstract <T> T lock(String key, LockRetryFrequency frequency, int timeoutInSeconds,
                               long expireSeconds, LockCallback<T> lockCallback) throws LockInsideExecutedException,
            LockCantObtainException;

}