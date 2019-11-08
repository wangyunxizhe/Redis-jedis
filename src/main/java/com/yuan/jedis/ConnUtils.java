package com.yuan.jedis;

import redis.clients.jedis.Jedis;

public class ConnUtils {

    private final static String REIDS_IP = "160.6.68.123";//单位有线

    private final static Integer REIDS_PORT = 6379;

    public static Jedis getJedis() {
        Jedis jedis = null;
        try {
            jedis = new Jedis(REIDS_IP, REIDS_PORT);
            System.err.println("redis连接成功");
        } catch (Exception e) {
            System.err.println("redis连接失败");
            e.printStackTrace();
        }
        return jedis;
    }

    public static void closeJedis(Jedis jedis) {
        if (jedis != null) {
            jedis.close();
            System.err.println("关闭连接");
        }
    }

}
