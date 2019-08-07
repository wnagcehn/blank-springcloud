package com.comic.blank.rediscallback;

import com.comic.blank.rediscallback.Exception.RedisClientException;
import com.comic.blank.rediscallback.service.CallBack;
import com.comic.blank.rediscallback.util.CacheUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisSentinelPool;
import redis.clients.jedis.exceptions.JedisException;
import redis.clients.util.Pool;

/**
 * @author wangchen
 * createAt 2019/7/17
 * updateAt 2019/7/17
 */
public class DefaultClientImpl implements InitializingBean, DisposableBean {

    private final Logger logger = LoggerFactory.getLogger(DefaultClientImpl.class.getName());

    private Pool<Jedis> pool;

    private JedisPool jedisPool;

    private JedisSentinelPool jedisSentinelPool;

    private JedisSentinelPoolFactory jedisSentinelPoolFactory;

    public void setJedisPool(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }

    public void setJedisSentinelPoolFactory(JedisSentinelPoolFactory jedisSentinelPoolFactory) {
        this.jedisSentinelPoolFactory = jedisSentinelPoolFactory;
        createJedisSentinelPool();
    }

    public void setKeySeparator(String keySeparator) {
        CacheUtils.setKeySeparator(keySeparator);
    }

    public void createJedisSentinelPool() {
        this.jedisSentinelPool = this.jedisSentinelPoolFactory.create();
    }

    protected <T> T execute(CallBack<T> callback) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return callback.invoke(jedis);
        } catch (JedisException e) {
            logger.error("jedis pool get resource error:{}", e);
        } finally {
            if (null != jedis) {
                jedis.close();
            }
        }
        return null;
    }

    @Override
    public void destroy() throws Exception {
        if (null != jedisPool) {
            jedisPool.close();
        }
        if (null != jedisSentinelPool) {
            jedisSentinelPool.close();
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if (null == jedisPool && null == jedisSentinelPool) {
            throw new RedisClientException("No connection pool found! Will not work.");
        }
        if (null != jedisPool && null != jedisSentinelPool) {
            throw new RedisClientException("There can only be one pool! Will not work.");
        }
        if (null != jedisPool) {
            pool = jedisPool;
            return;
        }
        if (null != jedisSentinelPool) {
            pool = jedisSentinelPool;
        }
    }

}
 
