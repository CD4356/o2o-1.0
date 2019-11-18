package com.cd.o2o.test;

import com.cd.o2o.entity.ProductCategory;
import com.cd.o2o.service.ProductCategoryService;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ProductCategoryServiceTest extends BaseTest {

    @Autowired
    private ProductCategoryService productCategoryService;

    @Test
    @Ignore
    public void testABatchInsertProductCategory(){

        ProductCategory productCategory = new ProductCategory();
        productCategory.setProductCategoryName("商品类别1");
        productCategory.setPriority(1);
        productCategory.setCreateTime(new Date());
        productCategory.setShopId(23L);

        ProductCategory productCategory2 = new ProductCategory();
        productCategory2.setProductCategoryName("商品类别2");
        productCategory2.setPriority(2);
        productCategory2.setCreateTime(new Date());
        productCategory2.setShopId(23L);

        List<ProductCategory> productCategoryList = new ArrayList<ProductCategory>();
        productCategoryList.add(productCategory);
        productCategoryList.add(productCategory2);

        int effectedNum = productCategoryService.batchAddProductCategory(productCategoryList);
        assertEquals(2, effectedNum);
        System.out.println("插入了"+effectedNum+"条数据！");
    }
    
}
