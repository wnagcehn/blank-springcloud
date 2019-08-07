package com.comic.blank.rediscallback.service;

import redis.clients.jedis.Jedis;

/**
 * 回调
 * @author wangchen
 * createAt 2019/7/17
 * updateAt 2019/7/17
 */
public interface CallBack<T> {

    T invoke(Jedis jedis);

}
 
