package com.comic.blank.rediscallback;

import com.comic.blank.rediscallback.Exception.LockCantObtainException;
import com.comic.blank.rediscallback.Exception.LockInsideExecutedException;
import com.comic.blank.rediscallback.service.IRedisClient;
import com.comic.blank.rediscallback.service.Lock;
import com.comic.blank.rediscallback.service.LockCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Redis分布式并发悲观锁
 *
 * @author wangchen
 * createAt 2019/7/17
 * updateAt 2019/7/17
 */
public class LockImplRedisNX implements Lock {

    private static final Logger logger = LoggerFactory.getLogger(LockImplRedisNX.class);

    private static final String LOCK_NAMESPACE = "LOCK";

    private IRedisClient redisClient;

    @Override
    public <T> T lock(String key, long expireSeconds, LockCallback<T> lockCallback) throws LockInsideExecutedException, LockCantObtainException {
        return lock(key, LockRetryFrequency.VERY_SLOW, 1, expireSeconds, lockCallback);
    }

    @Override
    public <T> T lock(String key, int timeoutInSeconds, long expireSeconds, LockCallback<T> lockCallback) throws LockInsideExecutedException, LockCantObtainException {
        return lock(key, LockRetryFrequency.NORMAL, timeoutInSeconds, expireSeconds, lockCallback);
    }

    @Override
    public <T> T lock(String key, LockRetryFrequency frequency, int timeoutInSeconds, long expireSeconds, LockCallback<T> lockCallback) throws LockInsideExecutedException, LockCantObtainException {
        long curentTime = System.currentTimeMillis();
        long expireSecond = curentTime / 1000 + expireSeconds;
        long expireMillisSecond = curentTime + expireSeconds * 1000;

        int retryCount = Float.valueOf(timeoutInSeconds * 1000 / frequency.getRetrySpan()).intValue();

        for (int i = 0; i < retryCount; i++) {
            String res = redisClient.set(key, LOCK_NAMESPACE, String.valueOf(expireMillisSecond), "NX", "EX", expireSecond);
            if ("OK".equals(res)) {
                logger.debug("obtain the lock: {},  at {} retry", key, i);
                try {
                    return lockCallback.handleObtainLock();
                } catch (Exception e) {
                    LockInsideExecutedException ie = new LockInsideExecutedException(e);
                    return lockCallback.handleException(ie);
                } finally {
                    // logger.info("释放锁{}",key2);
                    redisClient.del(key, LOCK_NAMESPACE);
                }
            } else {
                logger.debug("do not obtain the lock: {},  at {} retry", key, i);
                try {
                    Thread.sleep(frequency.getRetrySpan());
                } catch (InterruptedException e) {
                    logger.error("Interrupte exception", e);
                }
            }
        }
        String expireSpecifiedInString = redisClient.get(key, LOCK_NAMESPACE, null);
        if (expireSpecifiedInString != null) {
            long expireSpecified = Long.valueOf(expireSpecifiedInString);
            if (curentTime > expireSpecified) {
                logger.warn("detect the task lock is expired, key: {}, expireSpecified:{}, currentTime:{}", key,
                        expireSpecified, curentTime);
                redisClient.del(key, LOCK_NAMESPACE);
            }
        }
        T r = lockCallback.handleNotObtainLock();
        return r;
    }
}
 
