package com.yuan.jedis;

import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Created by wangy on 2018/10/21.
 */
public class JedisDemo1 {

    /**
     * 单实例测试
     */
    @Test
    public void demo1() {
        //1，设置ip和端口号
//        Jedis jedis = new Jedis("192.168.1.109", 6379);
        //单位有线IP
        Jedis jedis = new Jedis("160.6.68.123", 6379);
        //2，保存数据
        jedis.set("name", "wyuan");
        //3，获取数据
        String val = jedis.get("name");
        System.out.println(val);
        //4，释放资源
        jedis.close();
    }

    /**
     * 连接池方式连接
     */
    @Test
    public void demo2() {
        //1，获得连接池的配置对象
        JedisPoolConfig config = new JedisPoolConfig();
        //1.1，设置最大连接数
        config.setMaxTotal(30);
        //1.2，设置最大空闲连接数
        config.setMaxIdle(10);
        //2，获得连接池
        JedisPool jedisPool = new JedisPool(config, "160.6.68.123", 6379);
        //3，获取jedis
        Jedis jedis = null;
        try {
            //3.1，通过连接池获取连接
            jedis = jedisPool.getResource();
            jedis.set("name", "王渊");
            String val = jedis.get("name");
            System.out.println(val);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //4，释放资源
            if (jedis != null) {
                jedis.close();
            }
            if (jedisPool != null) {
                jedisPool.close();
            }
        }
    }

}