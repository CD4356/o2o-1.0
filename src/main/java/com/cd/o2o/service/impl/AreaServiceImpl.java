package com.cd.o2o.service.impl;

import com.cd.o2o.cache.JedisUtil;
import com.cd.o2o.dao.AreaDao;
import com.cd.o2o.entity.Area;
import com.cd.o2o.service.AreaService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;

@Service("areaService")
public class AreaServiceImpl implements AreaService {

    @Autowired
    private AreaDao areaDao;
    @Autowired
    private JedisUtil.Keys jedisKeys;
    @Autowired
    private JedisUtil.Strings jedisStrings;

    private static final String AREALISTkey = "arealist";


    /**
     * 获取区域列表信息
     *
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public List<Area> getAreaList() {
        //定义Redis的key名
        String key = AREALISTkey;
        //定义接收对象
        List<Area> areaList = null;
        //定义Jackson数据转换操作类
        ObjectMapper objectMapper = new ObjectMapper();
        //判断key是否存在
        if(!jedisKeys.exists(key)){
            //若key不存在，则从数据库中取出相应数据，并添加进redis里
            areaList = areaDao.queryArea();
            String jsonString = null;
            try {
                //将相关实体类集合转化成json字符串
                jsonString = objectMapper.writeValueAsString(areaList);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                //抛出RuntimeException才能回滚事务
                throw new RuntimeException(e.getMessage());
            }
            //将转化后的区域信息设置进指定的key中
            jedisStrings.set(key,jsonString);
        }else {
            //若key存在，则从redis中取出该key对应的数据
            String jsonString = jedisStrings.get(key);
            //指定要将string转化成的复杂集合类型  List<Area>
            JavaType javaType = objectMapper.getTypeFactory().constructParametricType(List.class,Area.class);
            try {
                //将json字符串转化成List<Area>对象
                areaList = objectMapper.readValue(jsonString,javaType);
            } catch (IOException e) {
                e.printStackTrace();
                //抛出RuntimeException才能回滚事务
                throw new RuntimeException(e.getMessage());
            }
        }
        return areaList;
    }
}
