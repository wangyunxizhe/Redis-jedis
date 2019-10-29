package com.yuan.jedis;

import redis.clients.jedis.Jedis;

/**
 * redis数据结构1--字符串
 */
public class TestString {

    public static void main(String[] args) {
        Jedis jedis = ConnUtils.getJedis();

        //1，set值
        jedis.set("String", "存了一个值");
        //2，get值
        System.out.println(jedis.get("String"));
        //3，先get值，再set新值
        jedis.getSet("String", "先获取这个key的值，再存这个新的值");
        //4，删除键值对
        long isDel = jedis.del("String");
        if (isDel > 0) {
            System.out.println("删除成功");
            System.out.println("删除后获取到的值是：" + jedis.get("String"));
        }
        //5，让key的value自增
        System.out.println(jedis.incr("让这个不存在的key的值自增"));//此时该key的value = 1
        System.out.println(jedis.incr("让这个不存在的key的值自增"));//此时该key的value = 2
        jedis.set("value为字符串的情况下", "还能自增吗？？");
//        System.out.println(jedis.incr("value为字符串的情况下"));//会抛出异常
        //6，让key的value递减
        System.out.println(jedis.decr("让这个不存在的key的值递减"));//此时该key的value = -1
        //7，让指定key的value增加5
        jedis.incrBy("num", 5);
        //8，让指定key的value减去2
        jedis.decrBy("num", 2);
        //9，让指定key的value追加上指定字符
        jedis.append("num", "5");
        System.out.println(jedis.get("num"));

        ConnUtils.closeJedis(jedis);
    }

}
