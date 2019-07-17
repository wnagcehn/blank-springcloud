package com.comic.blankredis.rediscallback;

/**
 * 锁重试获取频率策略
 */
public enum LockRetryFrequency {

    VERY_QUICK(10), QUICK(50), NORMAL(100), SLOW(500), VERY_SLOW(1000);

    private int retrySpan = 100;

    private LockRetryFrequency(int rf) {
        retrySpan = rf;
    }

    public int getRetrySpan() {
        return retrySpan;
    }

}
