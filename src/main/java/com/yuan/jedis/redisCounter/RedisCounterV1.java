package com.yuan.jedis.redisCounter;

import com.yuan.jedis.ConnUtils;
import redis.clients.jedis.Jedis;

/**
 * V1版本的Redis计数器，缺点：在并发时数据会错乱
 */
public class RedisCounterV1 {

    public static void main(String[] args) {
        Jedis jedis = ConnUtils.getJedis();

        int amountLimit = 100;//顶点
        int incrAmount = 1;//增加额
        //1，先验证该计数器是否存在
        if (!jedis.exists("countV1")) {
            //与V2的区别所在！！！set命令：当key存在时，覆盖旧值
            jedis.set("countV1", "95");
        }
        //2，获取当前计数器中的数值
        int currAmount = Integer.parseInt(jedis.get("countV1"));
        //3，校验当前数值 + 增加额是否大于上限
        if (currAmount + incrAmount > amountLimit) {
            System.out.println("已超过系统规定的最大限额");
            return;//结束业务流程
        }
        jedis.incrBy("countV1", incrAmount);
        System.out.println("未超限额，可继续业务流程");

        ConnUtils.closeJedis(jedis);
    }

}
