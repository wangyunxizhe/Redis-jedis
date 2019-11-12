package com.yuan.jedis.redisCounter;

import com.yuan.jedis.ConnUtils;
import redis.clients.jedis.Jedis;

/**
 * V2版本的Redis计数器
 */
public class RedisCounterV2 {

    public static void main(String[] args) {
        Jedis jedis = ConnUtils.getJedis();

        int amountLimit = 100;//顶点
        int incrAmount = 1;//增加额
        //1，先验证该计数器是否存在
        if (!jedis.exists("countV2")) {
            //与V1的区别所在！！！setnx命令：当key存在时，什么都不做
            jedis.setnx("countV2", "95");
        }
        //2，获取当前计数器中的数值
        int currAmount = Integer.parseInt(jedis.get("countV2"));
        //3，与V1的区别所在！！！在if时直接使用incrBy命令增加，利用返回值比较是否大于上限
        if (jedis.incrBy("countV2", incrAmount) > amountLimit) {
            System.out.println("已超过系统规定的最大限额");
            return;//结束业务流程
        }
        System.out.println("未超限额，可继续业务流程");

        ConnUtils.closeJedis(jedis);
    }

}
