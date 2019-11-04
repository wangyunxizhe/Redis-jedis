package com.yuan.jedis;

import redis.clients.jedis.Jedis;

import java.util.HashMap;
import java.util.Map;

/**
 * redis数据结构5--SortedSet，类似于set都是一种字符串集合
 * 应用场景：分数排名
 */
public class TestSortedSet {

    public static void main(String[] args) {
        Jedis jedis = ConnUtils.getJedis();

        //1，添加元素，当同样的key不同的value时，只做update的操作
        Map<String, Double> map = new HashMap<>();
        map.put("wy", 100D);
        map.put("wyx", 100D);
        map.put("wz", 100D);
        Long counts = jedis.zadd("mySort", map);
        System.out.println("mySort添加了" + counts + "个元素");
        //2，按元素key获取元素value
        Double val = jedis.zscore("mySort", "wy");
        System.out.println(val);
        //3，获取元素数量
        Long size = jedis.zcard("mySort");
        System.out.println("mySort长度为：" + size);
        //4，删除指定元素
        jedis.zrem("mySort", "wy");
        //5，查看指定位置元素，-1代表最后一个
        map.put("wy", 97D);
        map.put("jack", 85D);
        map.put("rose", 95D);
        jedis.zadd("mySort", map);
        System.out.println(jedis.zrange("mySort", 0, -1));
        //带分数遍历元素，从小到大
        System.out.println(jedis.zrangeWithScores("mySort", 0, -1));
        //带分数遍历元素，从大到小
        System.out.println(jedis.zrevrangeWithScores("mySort", 0, -1));
        //6，删除指定范围的元素
        jedis.zremrangeByRank("mySort", -2, -1);
        //7，按指定分数范围删除
        jedis.zremrangeByScore("mySort", 90, 99);
        //8，按分数（元素value）范围获取元素key
        System.out.println("分数范围内的key有：" + jedis.zrangeByScore("mySort", 60, 100));
        System.out.println("分数范围内的key,value有：" + jedis.zrangeByScoreWithScores("mySort", 60, 100));
        //9，给指定的key加分
        jedis.zincrby("mySort", 3, "jack");
        //10，查看指定分数区间的元素总数
        System.out.println("指定分数区间的元素总数为：" + jedis.zcount("mySort", 60, 99));

        ConnUtils.closeJedis(jedis);
    }

}
