package com.comic.blankredis.rediscallback;

import com.comic.blankredis.rediscallback.service.CallBack;
import com.comic.blankredis.rediscallback.service.GetDataCallBack;
import com.comic.blankredis.rediscallback.service.IRedisClient;
import com.comic.blankredis.rediscallback.util.CacheUtils;
import org.apache.commons.lang3.StringUtils;
import redis.clients.jedis.Jedis;

/**
 * @author wangchen
 * createAt 2019/7/17
 * updateAt 2019/7/17
 */
public class RedisClientImpl extends DefaultClientImpl implements IRedisClient {

    @Override
    public String set(String bizkey, String nameSpace, String value, String nxxx, String expx, long expire) {
        final String key = CacheUtils.getKeyByNamespace(bizkey, nameSpace);
        return this.performFunction(key, new CallBack<String>() {
            public String invoke(Jedis jedis) {
                return jedis.set(key, value, nxxx, expx, expire);
            }
        });
    }

    @Override
    public String get(String bizkey, String nameSpace, GetDataCallBack<String> gbs) {
        final String key = CacheUtils.getKeyByNamespace(bizkey, nameSpace);
        return this.performFunction(key, new CallBack<String>() {
            public String invoke(Jedis jedis) {
                String res = jedis.get(key);
                if (StringUtils.isEmpty(res)) {
                    if (null != gbs) {
                        res = gbs.invoke();
                        if (StringUtils.isNotEmpty(res)) {
                            set(bizkey, nameSpace, res, "NX", "EX", gbs.getExpiredTime());
                        }
                    }
                }
                return res;
            }
        });
    }

    @Override
    public Long del(String bizkey, String nameSpace) {
        final String key = CacheUtils.getKeyByNamespace(bizkey, nameSpace);
        return this.performFunction(key, new CallBack<Long>() {
            public Long invoke(Jedis jedis) {
                return jedis.del(key);
            }
        });
    }

    protected <R> R performFunction(String entrykey, CallBack<R> callBack) {
        return execute(callBack);
    }

}
 
