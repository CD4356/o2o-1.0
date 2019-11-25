package com.cd.o2o.test;

import com.cd.o2o.entity.Area;
import com.cd.o2o.service.AreaService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-service.xml"})
public class AreaServiceTest {

    @Resource(name = "areaService")
    private AreaService areaService;

    @Test
    public void demo1(){
        List<Area> areaList = areaService.getAreaList();
        System.out.println("区域数目："+areaList.size());
    }

}
