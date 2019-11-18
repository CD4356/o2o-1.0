package com.cd.o2o.service.impl;

import com.cd.o2o.dao.ShopCategoryDao;
import com.cd.o2o.entity.ShopCategory;
import com.cd.o2o.service.ShopCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("shopCategoryService")
public class ShopCategoryServiceImpl implements ShopCategoryService {

    @Autowired
    private ShopCategoryDao shopCategoryDao;


    /**
     * 根据查询条件获取指定的店铺分类的列表
     *
     * @param shopCategoryCondition
     * @return
     */
    public List<ShopCategory> getShopCategoryList(ShopCategory shopCategoryCondition) {
        return shopCategoryDao.queryShopCategory(shopCategoryCondition);
    }
}
