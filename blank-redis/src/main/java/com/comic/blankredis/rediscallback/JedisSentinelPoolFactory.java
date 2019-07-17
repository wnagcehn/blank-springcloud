package com.comic.blankredis.rediscallback;

import org.apache.commons.lang3.StringUtils;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisSentinelPool;
import redis.clients.jedis.Protocol;

import java.util.HashSet;
import java.util.Set;

/**
 * @author wangchen
 * createAt 2019/7/17
 * updateAt 2019/7/17
 */
public class JedisSentinelPoolFactory {


    public static final String COMMA = ",";

    String masterName;
    Set<String> sentinels;
    JedisPoolConfig poolConfig = new JedisPoolConfig();
    int timeout = Protocol.DEFAULT_TIMEOUT;
    String password;
    int database = Protocol.DEFAULT_DATABASE;
    String clientName;

    /**
     */
    public JedisSentinelPoolFactory() {
    }

    public void setMasterName(String masterName) {
        this.masterName = masterName;
    }

    public void setSentinels(String sentinels) {
        String[] sentinel = StringUtils.split(sentinels, COMMA);
        Set<String> sentinelsSet = new HashSet<String>();
        for (int i = 0; i < sentinel.length; i++) {
            sentinelsSet.add(sentinel[i]);
        }
        this.sentinels = sentinelsSet;
    }

    public void setPoolConfig(JedisPoolConfig poolConfig) {
        this.poolConfig = poolConfig;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setDatabase(int database) {
        this.database = database;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public JedisSentinelPool create() {
        return new JedisSentinelPool(masterName, sentinels, poolConfig, timeout, timeout, password, database, clientName);
    }


}
 
