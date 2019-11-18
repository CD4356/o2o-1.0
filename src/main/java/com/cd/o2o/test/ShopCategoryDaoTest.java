package com.cd.o2o.test;

import com.cd.o2o.dao.ShopCategoryDao;
import com.cd.o2o.entity.ShopCategory;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ShopCategoryDaoTest extends BaseTest{

    @Autowired
    private ShopCategoryDao shopCategoryDao;


    @Test
    public void testAQueryShopCategory() {
        List<ShopCategory> shopCategoryList = shopCategoryDao.queryShopCategory(null);
        System.out.println("所有一级店铺分类数目：" + shopCategoryList.size());
    }


    @Test
    public void testBQueryShopCategory() {
        List<ShopCategory> shopCategoryList = shopCategoryDao.queryShopCategory(new ShopCategory());
        System.out.println("所以二级店铺分类数目：" + shopCategoryList.size());
    }

    @Test
    public void testCQueryShopCategory() {
        ShopCategory shopCategoryCondition = new ShopCategory();
        ShopCategory parentShopCategory = new ShopCategory();
        parentShopCategory.setShopCategoryId(6L);
        shopCategoryCondition.setParent(parentShopCategory);
        List<ShopCategory> shopCategoryList = shopCategoryDao.queryShopCategory(shopCategoryCondition);
        System.out.println("美容美发一级店铺分类下的所有二级店铺分类数目：" + shopCategoryList.size());
    }


}
