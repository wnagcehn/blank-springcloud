package com.comic.blankredis.rediscallback.Exception;

import java.io.Serializable;

/**
 * @author wangchen
 * createAt 2019/7/17
 * updateAt 2019/7/17
 */
public class RedisClientException extends RuntimeException {


    private static final long serialVersionUID = 2248734573252273941L;

    /**
     * 构造方法
     *
     * @param msg 异常信息
     */
    public RedisClientException(String msg) {
        super(msg);
    }

    /**
     * 构造方法
     *
     * @param exception 异常原因
     */
    public RedisClientException(Throwable exception) {
        super(exception);
    }

    /**
     * 构造方法
     *
     * @param mag 异常信息
     * @param exception 异常原因
     */
    public RedisClientException(String mag, Exception exception) {
        super(mag, exception);
    }

}
 
