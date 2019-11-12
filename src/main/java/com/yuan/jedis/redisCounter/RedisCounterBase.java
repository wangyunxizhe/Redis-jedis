package com.yuan.jedis.redisCounter;

import com.yuan.jedis.ConnUtils;
import redis.clients.jedis.Jedis;

/**
 * redis计数器的关键方法
 * 计数器使用场景：并发场景下的数量控制
 */
public class RedisCounterBase {

    public static void main(String[] args) {
        Jedis jedis = ConnUtils.getJedis();

        //1，当指定key不存在时，新建指定key-value
        Long is_set = jedis.setnx("myCounter", "99");
        if (is_set > 0) {
            System.out.println("myCounter是否成功建立：" + is_set);
        }
        //2，给该key设置有效期
        jedis.expire("myCounter",10);
        //查看可以的生存时间
        jedis.ttl("myCounter");

        ConnUtils.closeJedis(jedis);
    }

}
