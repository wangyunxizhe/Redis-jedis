package com.yuan.jedis;

import redis.clients.jedis.Jedis;

/**
 * Keys的通用操作
 */
public class TestKeys {

    public static void main(String[] args) {
        Jedis jedis = ConnUtils.getJedis();

        //1，获取redis上所有的key
        System.out.println(jedis.keys("*"));
        //2，模糊匹配指定的key
        System.out.println(jedis.keys("my*"));
        //3，删除指定的key
        jedis.del("让这个不存在的key的值递减", "让这个不存在的key的值自增", "num");
        //4，查看指定key是否存在
        System.out.println("key==>mySort是否存在：" + jedis.exists("mySort"));
        //5，将key重命名
//        jedis.rename("value为字符串的情况下", "value为字符串的情况下-new");
        //6，设置key的过期时间，单位是秒
        jedis.expire("value为字符串的情况下-new",1000);
        //7，查看key的剩余时间
        System.out.println("指定的有效时间为："+jedis.ttl("value为字符串的情况下-new"));
        //8，查看指定key的value类型
        System.out.println("value类型为："+jedis.type("MyHashKey"));

        ConnUtils.closeJedis(jedis);
    }

}
