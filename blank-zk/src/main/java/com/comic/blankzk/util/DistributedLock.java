package com.comic.blankzk.util;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * classname：DistributedLock
 * desc：基于zookeeper的开源客户端Cruator实现分布式锁
 * author：wangchen
 */
public class DistributedLock {
    public static Logger log = LoggerFactory.getLogger(DistributedLock.class);
    private InterProcessMutex interProcessMutex;  //可重入排它锁
    private String lockName;  //竞争资源标志
    private String root = "/distributed/lock/";//根节点
    private static CuratorFramework curatorFramework;
    private static String ZK_URL = "zookeeper1.tq.master.cn:2181,zookeeper3.tq.master.cn:2181,zookeeper2.tq.master.cn:2181,zookeeper4.tq.master.cn:2181,zookeeper5.tq.master.cn:2181";
    static{
        curatorFramework= CuratorFrameworkFactory.newClient(ZK_URL,new ExponentialBackoffRetry(1000,3));
        curatorFramework.start();
    }

    /**
     * 实例化
     * @param lockName
     */
    public DistributedLock(String lockName){
        try {
            this.lockName = lockName;
            interProcessMutex = new InterProcessMutex(curatorFramework, root + lockName);
        }catch (Exception e){
            log.error("initial InterProcessMutex exception="+e);
        }
    }

    /**
     * 获取锁
     */
    public void acquireLock(){
        int flag = 0;
        try {
            //重试2次，每次最大等待2s，也就是最大等待4s
            while (!interProcessMutex.acquire(2, TimeUnit.SECONDS)){
                flag++;
                if(flag>1){  //重试两次
                    break;
                }
            }
        } catch (Exception e) {
            log.error("distributed lock acquire exception="+e);
        }
        if(flag>1){
            log.info("Thread:"+Thread.currentThread().getId()+" acquire distributed lock  busy");
        }else{
            log.info("Thread:"+Thread.currentThread().getId()+" acquire distributed lock  success");
        }
    }

    /**
     * 释放锁
     */
    public void releaseLock(){
        //校验当前锁的持有人与但概念请求是否相同
//        if (jedis.get(lockKey).equals(requestId)) {
//            // 执行在这里时，如果锁被其它请求重新获取到了，此时就不该删除了
//            jedis.del(lockKey);
//        }
        try {
            if(interProcessMutex != null && interProcessMutex.isAcquiredInThisProcess()){
                interProcessMutex.release();
                curatorFramework.delete().inBackground().forPath(root+lockName);
                log.info("Thread:"+Thread.currentThread().getId()+" release distributed lock  success");
            }
        }catch (Exception e){
            log.info("Thread:"+Thread.currentThread().getId()+" release distributed lock  exception="+e);
        }
    }
}