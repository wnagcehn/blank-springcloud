package com.comic.blankzk.util;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * 手写zk锁
 *
 * @author wangchen870
 * createAt 2019/6/28
 * updateAt 2019/6/28
 */
public class ZooKeeperLock implements Lock, Watcher {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private static final String SPLITSTR = "lock";
    private static final int SESSION_TIMEOUT = 60000; //等锁的毫秒数
    private static final byte[] data = new byte[0];

    private ZooKeeper zk = null;

    private String root = "/locks"; //根
    private String lockName; //竞争资源的标志
    private String waitNode; //等待前一个锁
    private String myZnode; //当前锁

    private CountDownLatch latch; //计数器


    /**
     * 创建分布式锁,使用前请确认config配置的zookeeper服务可用
     * @param server 127.0.0.1:2181
     * @param lockName 竞争资源标志,lockName中不能包含单词lock
     */
    public ZooKeeperLock(String server, String lockName){
        this.lockName = lockName;
        //创建一个与服务器的连接
        try {
            zk = initZk(server);
            Stat stat = zk.exists(root,false);
            if (stat == null){
                //创建根节点
                zk.create(root, data, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            }
        } catch (Exception e){
            throw new LockException(e);
        }
    }

    private synchronized ZooKeeper initZk(String server){
        try {
            if (zk == null){
                zk = new ZooKeeper(server, SESSION_TIMEOUT, this);
            }
        } catch (IOException e) {
            throw new LockException("zk init connect fail" + e.getMessage());
        }
        return zk;
    }


    @Override
    public void lock() {

    }

    @Override
    public boolean tryLock() {
        try {
            if (lockName.contains(SPLITSTR)){
                throw new LockException("锁名不能包含lock");
            }
            //创建临时子节点(按顺序的临时节点)
            myZnode = zk.create(root + "/" + lockName + SPLITSTR, data, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);

            //取出所有子节点
            List<String> subNodes = zk.getChildren(root, false);
            //取出所有使用lockName锁的节点，并排序
            List<String> lockObjNodes = new ArrayList<String>();
            for (String node : subNodes) {
                String lock = node.split(SPLITSTR)[0];
                if (lockName.equals(lock)){
                    lockObjNodes.add(node);
                }
            }
            Collections.sort(lockObjNodes);

            //如果当前节点是最小节点，则获取到锁
            if (myZnode.equals(root + "/" + lockObjNodes.get(0))){
                return true;
            }
            //如果不是最小的节点，找到比自己小1的节点
            String myChildZnode = myZnode.substring(myZnode.lastIndexOf("/") + 1);
            waitNode = lockObjNodes.get(Collections.binarySearch(lockObjNodes, myChildZnode) - 1);
        }catch (Exception e) {
            throw new LockException(e);
        }
        return false;
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return false;
    }

    @Override
    public void unlock() {

    }

    @Override
    public void lockInterruptibly() throws InterruptedException {

    }

    @Override
    public Condition newCondition() {
        return null;
    }

    /**
     * zk 节点监视器
     * @param watchedEvent
     */
    @Override
    public void process(WatchedEvent watchedEvent) {
        if (latch != null){
            latch.countDown();
        }
    }

    private class LockException extends RuntimeException {
        private static final long serialVersionUID = 1L;
        private LockException(String e){
            super(e);
        }
        private LockException(Exception e){
            super(e);
        }
    }

}
 
