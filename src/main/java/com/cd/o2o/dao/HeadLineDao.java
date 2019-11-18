package com.cd.o2o.dao;

import com.cd.o2o.entity.HeadLine;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HeadLineDao {

    /**
     * 根据传入的查询条件查询头条    条件：头条名（模糊）
     *
     * @param headLineCondition
     * @return
     */
    List<HeadLine> queryHeadLine(@Param("headLineCondition") HeadLine headLineCondition);

}
