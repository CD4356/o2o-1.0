package com.cd.o2o.service;

import com.cd.o2o.entity.HeadLine;

import java.util.List;

public interface HeadLineService {

    /**
     * 根据传入的条件返回对应的头条列表
     *
     * @param headLineCondition
     * @return
     */
    List<HeadLine> getHeadLineList(HeadLine headLineCondition);

}
