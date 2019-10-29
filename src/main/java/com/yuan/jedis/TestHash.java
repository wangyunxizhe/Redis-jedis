package com.yuan.jedis;

import redis.clients.jedis.Jedis;
import java.util.HashMap;
import java.util.Map;

/**
 * redis数据结构2--哈希
 */
public class TestHash {

    public static void main(String[] args) {
        Jedis jedis = ConnUtils.getJedis();

        //1，set值
        jedis.hset("MyHashKey", "username", "wyuan");
        jedis.hset("MyHashKey", "age", "18");
        //2，一次set多个key-value
        Map<String, String> map = new HashMap();
        map.put("key1", "value1");
        map.put("key2", "value2");
        jedis.hmset("MyHashKeyMap", map);
        //3，get值
        System.out.println(jedis.hget("MyHashKey", "username"));
        System.out.println(jedis.hget("MyHashKeyMap", "key1"));
        //4，一次get多个值（以List返回）
        System.out.println(jedis.hmget("MyHashKey", "username", "age"));
        //5，get指定key下所有的值（以Map返回）
        System.out.println(jedis.hgetAll("MyHashKeyMap"));
        //6，删除指定hash，可以写多个
        jedis.hdel("MyHashKeyMap", "key1", "key2");
        System.out.println(jedis.hgetAll("MyHashKeyMap"));
        jedis.del("MyHashKeyMap");//也可以直接整个删除
        //7，给指定hash key的value值增加5
        jedis.hincrBy("MyHashKey", "age", 5);
        System.out.println(jedis.hget("MyHashKey", "age"));
        //8，判断指定hash中的key是否存在
        System.out.println("MyHashKey的属性age是否存在：" + jedis.hexists("MyHashKey", "age"));
        //9.获取hash中key的个数
        System.out.println("MyHashKey的属性有 " + jedis.hlen("MyHashKey") + " 个");
        //10.获取hash中所有的key（以Set返回）
        System.out.println("MyHashKey的属性是 " + jedis.hkeys("MyHashKey"));
        //11.获取hash中所有的value（以Set返回）
        System.out.println("MyHashKey的属性值是 " + jedis.hvals("MyHashKey"));

        ConnUtils.closeJedis(jedis);
    }

}
