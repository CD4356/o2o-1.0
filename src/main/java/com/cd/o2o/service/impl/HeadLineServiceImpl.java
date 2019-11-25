package com.cd.o2o.service.impl;

import com.cd.o2o.cache.JedisUtil;
import com.cd.o2o.dao.HeadLineDao;
import com.cd.o2o.entity.HeadLine;
import com.cd.o2o.service.HeadLineService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;

@Service("headLineService")
public class HeadLineServiceImpl implements HeadLineService {

    @Autowired
    private HeadLineDao headLineDao;
    @Autowired
    private JedisUtil.Keys jedisKeys;
    @Autowired
    private JedisUtil.Strings jedisStrings;

    private static final String HEADLINEKEY = "headline";

    /**
     * 获取头条列表信息
     *
     * @param headLineCondition
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public List<HeadLine> getHeadLineList(HeadLine headLineCondition) {
        //定义Redis的key前缀
        String key = HEADLINEKEY;
        //定义接收对象
        List<HeadLine> headLineList = null;
        //定义Jackson数据转换操作类
        ObjectMapper objectMapper = new ObjectMapper();
        //拼接出redis所需的key
        if(headLineCondition != null && headLineCondition.getEnableStatus() != null){
            key = key + "_" + headLineCondition.getEnableStatus();
        }
        //判断key是否存在
        if(!jedisKeys.exists(key)){
            //若key不存在，则从数据库中取出相应数据，并添加进redis里
            headLineList = headLineDao.queryHeadLine(headLineCondition);
            String jsonString = null;
            try {
                //将相关实体类集合转化成json字符串
                jsonString = objectMapper.writeValueAsString(headLineList);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                //抛出RuntimeException才能回滚事务
                throw new RuntimeException(e.getMessage());
            }
            //将转化的json字符串设置进指定的key中
            jedisStrings.set(key,jsonString);
        }else {
            //若key存在，则从redis中取出该key对应的数据
            String jsonString = jedisStrings.get(key);
            //指定要将string转化成的复杂集合类型  List<HeadLine>
            JavaType javaType = objectMapper.getTypeFactory().constructParametricType(List.class,HeadLine.class);
            try {
                //将字符串反序列化成List<HeadList>对象
                headLineList = objectMapper.readValue(jsonString,javaType);
            } catch (IOException e) {
                e.printStackTrace();
                //抛出RuntimeException才能回滚事务
                throw new RuntimeException(e.getMessage());
            }
        }
        return headLineList;
    }
}
