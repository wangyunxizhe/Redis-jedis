package com.yuan.jedis.redisLock;

import redis.clients.jedis.Jedis;

/**
 * 锁的实现
 */
public class LockImpl implements Lock {

    /**
     * 加锁
     *
     * @return set返回“OK”时，该方法返回true
     */
    @Override
    public boolean tryLock(Jedis jedis, String key, String val, Long ttl) {
        //参数nxxx：值为“NX”时，key若存在则添加失败。参数expx：值为“EX”时，表示设置过期时间。
        String ok = jedis.set(key, val, "NX", "EX", ttl);
        return "OK".equals(ok);
    }

    /**
     * 解锁
     *
     * @return del方法返回行数大于1时，该方法返回true
     */
    @Override
    public boolean releaseLock(Jedis jedis, String key, String val) {
        //自己加的锁自己去释放
        if (val.equals(jedis.get(key))) {
            return jedis.del(key) > 0;
        }
        return false;
    }

}
