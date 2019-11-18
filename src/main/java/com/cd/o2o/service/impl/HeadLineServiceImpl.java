package com.cd.o2o.service.impl;

import com.cd.o2o.dao.HeadLineDao;
import com.cd.o2o.entity.HeadLine;
import com.cd.o2o.service.HeadLineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("headLineService")
public class HeadLineServiceImpl implements HeadLineService {

    @Autowired
    private HeadLineDao headLineDao;


    public List<HeadLine> getHeadLineList(HeadLine headLineCondition) {
        return headLineDao.queryHeadLine(headLineCondition);
    }
}
