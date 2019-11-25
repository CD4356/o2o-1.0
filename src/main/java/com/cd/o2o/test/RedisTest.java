package com.cd.o2o.test;

import com.cd.o2o.cache.JedisPoolUtil;
import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.annotation.Resource;

public class RedisTest extends BaseTest{

    @Resource(name = "jedisPoolUtil")
    private JedisPoolUtil jedisPoolUtil;

    @Test
    public void spring_redis(){
        //获取Redis连接池对象
        JedisPool jedisPool = jedisPoolUtil.getJedisPool();
        //通过Redis连接池获取jedis对象
        Jedis jedis = jedisPool.getResource();
        System.out.println(jedis.exists("arealist"));
        long count = jedis.del("arealist");
        System.out.println(count);
        System.out.println(jedis.exists("arealist"));
        //获取存储的key数据并输出
        System.out.println(jedis.get("arealist"));
    }

}
