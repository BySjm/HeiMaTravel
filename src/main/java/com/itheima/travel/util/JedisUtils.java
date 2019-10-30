package com.itheima.travel.util;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.ResourceBundle;

public class JedisUtils {

    private static JedisPool jedisPool = null;

    static {
        ResourceBundle bundle = ResourceBundle.getBundle("jedis");
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxTotal(Integer.parseInt(bundle.getString("jedis.MaxTotal")));
        poolConfig.setMaxIdle(Integer.parseInt(bundle.getString("jedis.MaxIdle")));
        poolConfig.setMaxWaitMillis(Integer.parseInt(bundle.getString("jedis.MaxWaitMillis")));
        jedisPool = new JedisPool(poolConfig,bundle.getString("jedis.host"), Integer.parseInt(bundle.getString("jedis.port")));

    }

    public static Jedis getJedis(){
        return jedisPool.getResource();
    }

}
