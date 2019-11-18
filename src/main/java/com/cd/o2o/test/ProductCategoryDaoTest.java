package com.cd.o2o.test;

import com.cd.o2o.dao.ProductCategoryDao;
import com.cd.o2o.entity.ProductCategory;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ProductCategoryDaoTest extends BaseTest {

    @Autowired
    private ProductCategoryDao productCategoryDao;

    @Test
    @Ignore
    public void testQueryProductCategoryList(){
        Long shopId = 23L;
        List<ProductCategory> productCategoryList = productCategoryDao.queryProductCategoryList(shopId);
        System.out.println("店铺下的商品类型数目："+productCategoryList.size());
    }

//    @Test
//    public void testABatchInsertProductCategory(){
//
//        ProductCategory productCategory = new ProductCategory();
//        productCategory.setProductCategoryName("商品类别1");
//        productCategory.setPriority(1);
//        productCategory.setCreateTime(new Date());
//        productCategory.setShopId(23L);
//
//        ProductCategory productCategory2 = new ProductCategory();
//        productCategory2.setProductCategoryName("商品类别2");
//        productCategory2.setPriority(2);
//        productCategory2.setCreateTime(new Date());
//        productCategory2.setShopId(23L);
//
//        List<ProductCategory> productCategoryList = new ArrayList<ProductCategory>();
//        productCategoryList.add(productCategory);
//        productCategoryList.add(productCategory2);
//
//        int effectedNum = productCategoryDao.batchInsertProductCategory(productCategoryList);
//        assertEquals(2, effectedNum);
//    }

}
