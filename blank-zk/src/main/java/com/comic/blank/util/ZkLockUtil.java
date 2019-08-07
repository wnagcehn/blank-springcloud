package com.comic.blank.util;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.apache.zookeeper.CreateMode;

import java.util.concurrent.CountDownLatch;

/**
 * zk分布式锁
 *
 * @author wangchen870
 *         createAt 2019/5/27
 *         updateAt 2019/5/27
 */
public class ZkLockUtil {

    //分布式锁,用于挂起当前线程,等待上一把分布式锁释放
    private static CountDownLatch DISTRIBUTE_LOCK = new CountDownLatch(1);
    //分布式锁的父节点名
    private final static String ZK_LOCK_PROJECT = "distributed";
    //分布式锁节点名
    private final static String DISTRIBUTE_LOCK_NAME = "lock";

    /**
     * 获取分布式锁
     */
    public static void getLock() {
        CuratorFramework client = ZkClientUtil.build();
        client.start();
        while (true) {
            try {
                client.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL).forPath("/" + ZK_LOCK_PROJECT + "/" + DISTRIBUTE_LOCK_NAME);
                System.out.println("获取分布式锁成功...");
                return;
            } catch (Exception e) {
                try {
                    //如果没有获取到锁,需要重新设置同步资源值
                    if (DISTRIBUTE_LOCK.getCount() <= 0) {
                        DISTRIBUTE_LOCK = new CountDownLatch(1);
                    }
                    System.out.println("获取分布式锁失败,等待他人释放锁中...");
                    DISTRIBUTE_LOCK.await();
                } catch (InterruptedException ie) {
                    ie.printStackTrace();
                }
            }
        }
    }

    /**
     * 释放锁资源
     */
    public static void  release(String path) {
        CuratorFramework client = ZkClientUtil.build();
        client.start();
        try {
            client.delete().forPath(path);
            System.out.println("锁释放成功...");
        } catch (Exception e) {
            System.out.println("释放锁失败...");
            e.printStackTrace();
        } finally {
            client.close();
        }
    }

    /**
     * 为指定路径节点创建watch,观察锁状态
     */
    public static void addWatcher2Path(final String path) throws Exception {
        CuratorFramework client = ZkClientUtil.build();
        client.start();
        final PathChildrenCache pathChildrenCache = new PathChildrenCache(client, path, true);
        pathChildrenCache.start(PathChildrenCache.StartMode.POST_INITIALIZED_EVENT);
        System.out.println("创建观察者成功...");
        pathChildrenCache.getListenable().addListener(new PathChildrenCacheListener() {
            public void childEvent(CuratorFramework curatorFramework, PathChildrenCacheEvent pathChildrenCacheEvent) throws Exception {
                if (pathChildrenCacheEvent.getType().equals(PathChildrenCacheEvent.Type.CHILD_REMOVED)) {
                    String nodePath = pathChildrenCacheEvent.getData().getPath();
                    System.out.println("上一会话已释放锁或会话已断开...节点路径为:" + nodePath);
                    if (nodePath.contains(DISTRIBUTE_LOCK_NAME)) {
                        DISTRIBUTE_LOCK.countDown();
                        System.out.println("释放计数器,计数器值为:"+DISTRIBUTE_LOCK.getCount()+"让当前请求来获取分布式锁...");
                    }
                }
            }
        });
    }

}
 
