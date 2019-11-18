package com.cd.o2o.service;


import com.cd.o2o.entity.Area;

import java.util.List;

public interface AreaService {
    /**
     * 获取区域列表,优先从缓存中获取
     * @return areaList
     */
    List<Area> getAreaList();

}
