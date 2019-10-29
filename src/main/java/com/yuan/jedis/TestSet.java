package com.yuan.jedis;

import redis.clients.jedis.Jedis;

/**
 * redis数据结构4--Set
 */
public class TestSet {

    public static void main(String[] args) {
        Jedis jedis = ConnUtils.getJedis();

        //1，添加元素，元素若重复，则只会添加一次，而且set是无序的
        jedis.sadd("mySet", "a", "b", "c");
        jedis.sadd("mySet", "a", "1", "2", "3");
        //2，删除元素
        jedis.srem("mySet", "2");
        //3，查看set中的元素
        System.out.println(jedis.smembers("mySet"));
        //4，判断set中有没有指定的元素
        System.out.println("该set中是否存在“a”元素：" + jedis.sismember("mySet", "a"));
        //5，查看两个set的差集，注意入参顺序的差别
        jedis.sadd("mySet1", "1", "b", "c");
        jedis.sadd("mySet2", "2", "a", "b");
        System.out.println(jedis.sdiff("mySet1", "mySet2"));
        //6，查看两个set的交集
        System.out.println(jedis.sinter("mySet1", "mySet2"));
        //7，查看两个set的并集，两者间重复的元素会被去重
        System.out.println(jedis.sunion("mySet1", "mySet2"));
        //8，查看Set中元素数量
        System.out.println(jedis.scard("mySet"));
        //9，随机返回Set中的一个元素
        System.out.println(jedis.srandmember("mySet"));
        //10，将2个Set的差集存储到一个新的Set中
        jedis.sdiffstore("myDiffSet", "mySet1", "mySet2");
        System.out.println("myDiffSet：" + jedis.smembers("myDiffSet"));
        //11，将2个Set的交集存储到一个新的Set中
        jedis.sinterstore("mySameSet", "mySet1", "mySet2");
        System.out.println("mySameSet：" + jedis.smembers("mySameSet"));
        //11，将2个Set的并集存储到一个新的Set中
        jedis.sunionstore("myAllSet", "mySet1", "mySet2");
        System.out.println("myAllSet：" + jedis.smembers("myAllSet"));


        jedis.del("mySet");
        ConnUtils.closeJedis(jedis);
    }

}
