package com.comic.blank.rediscallback.service;

/**
 * @author wangchen
 * createAt 2019/7/17
 * updateAt 2019/7/17
 */
public interface GetDataCallBack<R> {

    /**
     * ttl时间,不是所有命令都支持ttl设置
     * */
    int getExpiredTime();

    /**
     * 执行回调方法
     */
    R invoke();

}
 
