package com.itheima.travel.test;

import com.itheima.travel.util.JedisUtils;
import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.List;

public class JedisTest {

    //普通Jedis测试
    @Test
    public void testJedis() throws Exception {
        Jedis jedis = new Jedis("127.0.0.1",6379);
        jedis.set("username","宝宝");
        System.out.println(jedis.get("username"));
        jedis.close();
    }

    //Jedis连接池测试
    @Test
    public void testJedisPool() throws Exception {

        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(10);
        jedisPoolConfig.setMaxIdle(5);//最大空闲数
        jedisPoolConfig.setMaxWaitMillis(3);

        JedisPool jedisPool = new JedisPool(jedisPoolConfig,"127.0.0.1", 6379);
        Jedis jedis = jedisPool.getResource();
        jedis.lpush("class","c1","c2","c3");
        List<String> aClass = jedis.lrange("class", 0, -1);
        System.out.println(aClass);
        jedis.close();
    }

    //Jedis连接池工具类测试
    @Test
    public void testJedisPoolUtil() throws Exception {
        Jedis jedis = JedisUtils.getJedis();
        if (jedis.exists("class")){
            List<String> list = jedis.lrange("class", 0, -1);
            System.out.println(list);
        }
        jedis.close();
    }

}
