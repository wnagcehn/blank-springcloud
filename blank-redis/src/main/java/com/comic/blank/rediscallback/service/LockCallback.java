package com.comic.blank.rediscallback.service;

import com.comic.blank.rediscallback.Exception.LockCantObtainException;
import com.comic.blank.rediscallback.Exception.LockInsideExecutedException;

/**
 * 〈回调接口〉
 *
 * @author wangchen
 * @create 2019/7/17
 * @update 2019/7/17
 */
public interface LockCallback<T> {

    /**
     * 获取到锁时回调
     * @return
     */
    public T handleObtainLock();

    /**
     * 没有获取到锁时回调
     * @return
     * @throws LockCantObtainException
     */
    public T handleNotObtainLock() throws LockCantObtainException;

    /**
     * 获取到锁时，执行业务逻辑出错
     * @param e
     * @return
     * @throws LockInsideExecutedException
     */
    public T handleException(LockInsideExecutedException e) throws LockInsideExecutedException;

}
 
