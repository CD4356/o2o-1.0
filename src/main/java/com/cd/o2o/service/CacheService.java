package com.cd.o2o.service;

public interface CacheService {


    /**
     * 根据key前缀删除匹配模式下的所有key-value，
     * 比如传入shopcategory,则shopcategory_firstlevel、shopcategory_allsecondlevel等以shopcategory开头的key-value都会被清空
     * @param keyprefix
     */
    void removeFromCache(String keyprefix);

}
