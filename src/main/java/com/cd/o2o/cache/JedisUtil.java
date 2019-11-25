package com.cd.o2o.cache;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.util.SafeEncoder;

import java.util.Set;

public class JedisUtil {

    //操作key的方法 （用于判断某个key是否存在，需不需要清空key里的数据），这是对key的操作
    private Keys keys;
    //对存储结构为String的操作（Redis中，字符串类型的value最大可容纳的数据为512M），这是对value的操作
    private Strings strings;

    //Redis连接池
    private JedisPool jedisPool;


    /**
     * 获取Redis连接池
     * @return
     */
    public JedisPool getJedisPool() {
        return jedisPool;
    }

    /**
     * 设置Redis连接池
     * @param jedisPoolUtil
     */
    public void setJedisPool(JedisPoolUtil jedisPoolUtil) {
        this.jedisPool = jedisPoolUtil.getJedisPool();
    }

    /**
     * 从Redis连接池中获取jedis对象
     * @return
     */
    public Jedis getJedis(){
        return jedisPool.getResource();
    }


    /*-----------------------Keys--------------------------*/


    public class Keys{

        public Keys() {
        }

        public Keys(JedisUtil jedisUtil) {

        }

        /**
         * 清除所有的key
         * @return 状态码
         */
        public String flushAll(){
            //获取jedis连接对象
            Jedis jedis = getJedis();
            //调用jedis的flushAll()方法，清空所有的key
            String state = jedis.flushAll();
            //关闭jedis连接
            jedis.close();
            return state;
        }

        /**
         * 判断某个Key是否存在
         *
         * @param key
         * @return boolean
         */
        public boolean exists(String key){
            // 获取jedis连接对象
            Jedis jedis = getJedis();
            // 调用jedis的exists()方法，判断某个Key是否存在于Redis服务器中
            boolean exists = jedis.exists(key);
            // 关闭jedis连接
            jedis.close();
            return exists;
        }

        /**
         * 删除key对应的记录，可以是多个记录
         *
         * @param keys
         * @return 删除的记录数
         */
        public long del(String... keys){
            // 获取jedis连接对象
            Jedis jedis = getJedis();
            // 调用jedis的del()方法将相关的Keys删除，并返回删除的记录数
            long count = jedis.del(keys);
            // 关闭jedis连接
            jedis.close();
            return count;
        }

        /**
         * 查找所有给定模式的key
         *
         * @param pattern  key表达式：*表示多个 ?表示一个 （粒如：shop*表示所有以shop开头的key）
         * @return
         */
        public Set<String> keys(String pattern){
            //获取jedis对象
            Jedis jedis = getJedis();
            //调用jedis的keys()方法，获取匹配的key，并保存到Set集合中
            Set<String> set = jedis.keys(pattern);
            //关闭jedis连接
            jedis.close();
            return set;
        }

        /**
         * 更改key
         *
         * @param oldKey
         * @param newKey
         * @return 状态码
         */
        public String rename(String oldKey,String newKey){
            Jedis jedis = getJedis();
            return jedis.rename(SafeEncoder.encode(oldKey),SafeEncoder.encode(newKey));
        }


        /**
         * 更改key，仅当新key不存在时才执行
         *
         * @param oldKey
         * @param newKey
         * @return 状态码
         */
        public Long renamenx(String oldKey,String newKey){
            Jedis jedis = getJedis();
            long status = jedis.renamenx(oldKey,newKey);
            jedis.close();
            return status;
        }

    }

    /*-------------------------Strings----------------------*/
    public class Strings{

        public Strings() {
        }

        public Strings(JedisUtil jedisUtil) {

        }

        /**
         * 根据key名，获取其存储数据
         * @param key
         * @return
         */
        public String get(String key){
            //获取Redis连接对象
            Jedis jedis = getJedis();
            //获取指定key的存储数据
            String value = jedis.get(key);
            //关闭jedis连接
            return value;
        }

        /**
         * 添加记录，如果记录已存在则覆盖原有的value
         * @param key
         * @param value
         * @return
         */
        public String set(String key,String value){
            //获取jedis连接对象
            Jedis jedis = getJedis();
            //设置指定key的存储数据
            String state =  jedis.set(key,value);
            return state;
        }
    }

}
