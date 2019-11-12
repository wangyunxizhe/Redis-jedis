package com.yuan.jedis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;
import java.util.List;

/**
 * redis的一些特性
 */
public class RedisFeatures {

    public static void main(String[] args) {
        Jedis jedis = ConnUtils.getJedis();

        //1，连接指定的redis数据库（不指定默认连接0号数据库）
        jedis.select(1);
        System.out.println("1号数据库中的所有key为：" + jedis.keys("*"));
        //2，将指定key移动到指定redis库
        jedis.select(0);
//        jedis.move("myList1", 1);
        jedis.select(1);
        System.out.println("移动后，1号数据库中的所有key为：" + jedis.keys("*"));
        //3，事务操作
        jedis.select(0);
        jedis.incrBy("swTest", 1);//创建一个自增的key-value
        jedis.watch("swTest");//监视指定key（可写多个）
        Transaction transaction = jedis.multi();//开启事务
        //从这里开始可看作是事务体；
        // 在事务体中，当代码出错或监视的key被本事务对象之外的对象修改时，事务不会提交，返回空列表
        transaction.incr("swTest");//自增1，注意此时用的对象（不是jedis了）
        transaction.incr("swTest");//再自增1
        List<Object> result = transaction.exec();// 提交事务
        System.out.println("事务结果是否为空：" + result.isEmpty());// 判断事务是否成功，不为空时成功
        System.out.println("事务结果：" + result);
        jedis.unwatch();// 解除监视

        ConnUtils.closeJedis(jedis);
    }

}
