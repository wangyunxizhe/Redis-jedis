package com.yuan.jedis;

import redis.clients.jedis.BinaryClient;
import redis.clients.jedis.Jedis;

/**
 * redis数据结构3--List
 */
public class TestList {

    public static void main(String[] args) {
        Jedis jedis = ConnUtils.getJedis();

        //1，从左侧开始，向list中添加值
        jedis.lpush("myList", "a", "b", "c");
        //2，从右侧开始，向list中添加值
        jedis.rpush("myList", "1", "2", "3");
        //3，查看list。参数1：list的名称。参数2：从哪开始。参数3：从哪结束
        System.out.println(jedis.lrange("myList", 0, -2));//从0查看到倒数第二个元素，如果是5，就是从0看到第5个元素
        //4，弹出指定list中的第一个元素，弹出后该元素就不存在了。如果指定list名不存在则返回null
        System.out.println(jedis.lpop("myList"));
        //5，弹出指定list中的最后一个元素
        System.out.println(jedis.rpop("myList"));
        System.out.println(jedis.lrange("myList", 0, -1));
        //6，获取指定list的长度
        System.out.println(jedis.llen("myList"));
        //7，仅当key存在时，才往这个list中set值。与1，2不同的是：1，2会将不存在的新建出来再set值
        long isAdd = jedis.lpushx("不存在的list", "x");
        if (isAdd < 1) {
            System.out.println("插入失败");
        }
        //8，删除指定list中的元素
        jedis.lpush("myList", "a", "b", "c");
        jedis.lpush("myList", "a", "b", "c");
        jedis.lpush("myList", "a", "b", "c");
        jedis.lrem("myList", 2, "a");//正2表示从前往后，删除2个a
        System.out.println(jedis.lrange("myList", 0, -1));
        jedis.lrem("myList", -2, "b");//负2表示从后往前，删除2个b
        System.out.println(jedis.lrange("myList", 0, -1));
        jedis.lrem("myList", 0, "2");//0表示删除该list中“所有”的2
        System.out.println(jedis.lrange("myList", 0, -1));
        //9，set list中指定下标的元素值
        jedis.lset("myList", 2, "mmm");
        System.out.println(jedis.lrange("myList", 0, -1));
        //10，在list中的第一个“b”之前添加指定元素“11”
        jedis.linsert("myList", BinaryClient.LIST_POSITION.BEFORE.BEFORE, "b", "11");
        System.out.println(jedis.lrange("myList", 0, -1));
        //11，将myList1中最左侧的元素移动到myList2的最右侧，
        // 这个方法的使用场景：做数据备份（在被取出别的应用一条之后，可以调用该方法往自己备用的list中转移，确保数据不丢失）
        jedis.lpush("myList1", "a", "b", "c");
        jedis.lpush("myList2", "1", "2", "3");
        jedis.rpoplpush("myList1", "myList2");
        System.out.println("myList1" + jedis.lrange("myList1：", 0, -1));
        System.out.println("myList2" + jedis.lrange("myList2：", 0, -1));


        jedis.del("myList");
        ConnUtils.closeJedis(jedis);
    }

}
