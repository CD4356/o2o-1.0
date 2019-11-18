package com.cd.o2o.test;

import com.cd.o2o.dao.HeadLineDao;
import com.cd.o2o.entity.HeadLine;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class HeadLineTest extends BaseTest {

    @Autowired
    private HeadLineDao headLineDao;

    @Test
    public void testQueryHeadLine(){
        List<HeadLine> headLineList = headLineDao.queryHeadLine(new HeadLine());
        System.out.println("所有头条数目："+headLineList.size());
    }


}
