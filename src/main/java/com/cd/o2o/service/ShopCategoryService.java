package com.cd.o2o.service;

import com.cd.o2o.entity.ShopCategory;

import java.util.List;

public interface ShopCategoryService {

    /**
     * 根据查询条件获取对应的ShopCategory列表
     *
     * @return
     */
    List<ShopCategory> getShopCategoryList(ShopCategory shopCategoryCondition);

}
