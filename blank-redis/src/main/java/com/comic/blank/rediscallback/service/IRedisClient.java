package com.comic.blank.rediscallback.service;

/**
 * 〈〉
 *
 * @author wangchen
 * @create 2019/7/17
 * @update 2019/7/17
 */
public interface IRedisClient {

    public String set(final String bizkey, final String nameSpace, final String value, final String nxxx,
                      final String expx, final long expire);

    public String get(final String bizkey, final String nameSpace, final GetDataCallBack<String> gbs);

    public Long del(final String bizkey, final String nameSpace);

}
 
