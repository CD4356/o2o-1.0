package com.cd.o2o.cache;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 *  redis连接池
 * @Author caidong
 */
public class JedisPoolUtil {

    // Redis连接池对象
    private JedisPool jedisPool;


    /**
     * @param jedisPoolConfig 连接池配置相关信息
     * @param hostname   服务器IP
     * @param port   服务器上Redis的服务端口
     */
    public JedisPoolUtil(JedisPoolConfig jedisPoolConfig, String hostname, int port) {
        try{
            jedisPool = new JedisPool(jedisPoolConfig,hostname,port);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 获取Redis连接池对象
     * @return
     */
    public JedisPool getJedisPool() {
        return jedisPool;
    }

    /**
     * 注入Redis连接池
     * @param jedisPool
     */
    public void setJedisPool(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }
}
