package com.yuan.jedis.redisLock;

import com.yuan.jedis.ConnUtils;
import redis.clients.jedis.Jedis;

import java.util.concurrent.TimeUnit;

/**
 * redis分布式锁的简单实现
 */
public class MyLock {

    private static final String MONEY = "MONEY";
    private static final String MONEY_LOCK = "MONEY_LOCK";

    /**
     * 没有锁时候的情况
     */
    public static Runnable notLock() throws InterruptedException {
        return () -> {
            try (Jedis jedis = ConnUtils.getJedis()) {
                calImpl(jedis);
            } catch (Exception e) {
                e.printStackTrace();
            }
        };
    }

    /**
     * 有锁时候的情况
     */
    public static Runnable lock() throws InterruptedException {
        LockImpl lockImpl = new LockImpl();
        return () -> {
            try (Jedis jedis = ConnUtils.getJedis()) {
                cal(lockImpl, jedis);
            } catch (Exception e) {
                e.printStackTrace();
            }
        };
    }

    /**
     * 在if中尝试加锁，若加锁失败则进入else中重复尝试加锁
     */
    private static void cal(LockImpl lockImpl, Jedis jedis) {
        if (lockImpl.tryLock(jedis, MONEY_LOCK, "100", 1000L)) {
            calImpl(jedis);
            lockImpl.releaseLock(jedis, MONEY_LOCK, "100");
        } else {
            cal(lockImpl, jedis);
        }
    }

    /**
     * 计算
     */
    private static void calImpl(Jedis jedis) {
        int money = Integer.parseInt(jedis.get(MONEY));
        jedis.set(MONEY, String.valueOf(money - 100));
        System.err.println("计算：" + Thread.currentThread().getName());
    }

    public static void main(String[] args) throws InterruptedException {
        try (Jedis jedis = ConnUtils.getJedis()) {
            jedis.set(MONEY, "1000");
        } catch (Exception e) {
            e.printStackTrace();
        }

        Runnable runnable = notLock();
        for (int i = 0; i < 10; i++) {
            Thread t = new Thread(runnable, String.valueOf(i));
            t.start();
        }

        TimeUnit.SECONDS.sleep(5);
        try (Jedis jedis = ConnUtils.getJedis()) {
            int money = Integer.parseInt(jedis.get(MONEY));
            System.out.println(money);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
