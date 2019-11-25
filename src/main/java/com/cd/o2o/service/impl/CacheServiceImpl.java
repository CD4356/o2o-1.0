package com.cd.o2o.service.impl;

import com.cd.o2o.cache.JedisUtil;
import com.cd.o2o.service.CacheService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;

public class CacheServiceImpl implements CacheService {

    @Autowired
    private JedisUtil.Keys jedisKeys;

    /**
     * 根据key前缀删除匹配模式下的所有key-value，
     * 比如传入shopcategory,则shopcategory_firstlevel、shopcategory_allsecondlevel等以shopcategory开头的key-value都会被清空
     */
    public void removeFromCache(String keyprefix) {
        //调用jedis的keys()方法，获取匹配的key，并保存到Set集合中
        Set<String> set = jedisKeys.keys(keyprefix + "*");
        //遍历set集合，清空key的value值
        for(String key:set){
            jedisKeys.del(key);
        }
    }
}
