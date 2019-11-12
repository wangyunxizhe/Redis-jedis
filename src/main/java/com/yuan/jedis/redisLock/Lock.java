package com.yuan.jedis.redisLock;

import redis.clients.jedis.Jedis;

/**
 * 锁接口
 */
public interface Lock {

    /**
     * 测试加锁
     */
    boolean tryLock(Jedis jedis, String key, String val, Long ttl);

    /**
     * 释放锁
     */
    boolean releaseLock(Jedis jedis, String key, String val);

}
