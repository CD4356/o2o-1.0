package com.cd.o2o.service.impl;

import com.cd.o2o.cache.JedisUtil;
import com.cd.o2o.dao.ShopCategoryDao;
import com.cd.o2o.entity.ShopCategory;
import com.cd.o2o.service.ShopCategoryService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;

@Service("shopCategoryService")
public class ShopCategoryServiceImpl implements ShopCategoryService {

    @Autowired
    private ShopCategoryDao shopCategoryDao;
    @Autowired
    private JedisUtil.Keys jedisKeys;
    @Autowired
    private JedisUtil.Strings jedisStrings;

    private static final String SHOPCATEGORYLIST = "shopcategory";


    /**
     * 根据查询条件获取指定的店铺分类的列表
     *
     * @param shopCategoryCondition
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public List<ShopCategory> getShopCategoryList(ShopCategory shopCategoryCondition) {
        //定义Redis的key前缀
        String key = SHOPCATEGORYLIST;
        //定义List<ShopCategory>对象，接收店铺类别列表信息
        List<ShopCategory> shopCategoryList = null;
        //定义Jackson数据转换操作类
        ObjectMapper objectMapper = new ObjectMapper();

        //拼接出redis的key
        if(shopCategoryCondition == null){
            //如果查询条件为空，则列出所有首页大类，即店铺为空的店铺类别
            key = key + "_firstlevel";
        }else if(shopCategoryCondition != null && shopCategoryCondition.getParent() != null){
            //若parentId不为空，则列出parentId下的所有子类别
            key = key + "_parent" + shopCategoryCondition.getParent().getShopCategoryId();
        }else if(shopCategoryCondition != null){
            //列出所有的二级分类，不管它是属于哪个一级分类下的
            key = key + "_allsecondlevel";
        }
        //判断key是否存在
        if(!jedisKeys.exists(key)){
            //若不存在，则从数据库中取出相应数据
            shopCategoryList = shopCategoryDao.queryShopCategory(shopCategoryCondition);
            String jsonString = null;
            try {
                //将相关集合转化成string，以保存到redis指定的key中
                jsonString = objectMapper.writeValueAsString(shopCategoryList);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                //抛出RuntimeException才能回滚事务
                throw new RuntimeException(e.getMessage());
            }
            //将转化后的json字符串保存到redis指定的key里
            jedisStrings.set(key,jsonString);
        }else {
            //若key存在，则取出key对应的数据
            String jsonString = jedisStrings.get(key);
            //指定要将string转化成的复杂集合类型  List<ShopCategory>
            JavaType javaType = objectMapper.getTypeFactory().constructParametricType(List.class,ShopCategory.class);
            try {
                //将json字符串反序列化成复杂集合类型  List<ShopCategory>
                shopCategoryList = objectMapper.readValue(jsonString,javaType);
            } catch (IOException e) {
                e.printStackTrace();
                //抛出RuntimeException才能回滚事务，其它异常不可以
                throw new RuntimeException(e.getMessage());
            }
        }
        return shopCategoryList;
    }
}
