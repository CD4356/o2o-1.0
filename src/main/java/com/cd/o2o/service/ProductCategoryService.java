package com.cd.o2o.service;

import com.cd.o2o.entity.ProductCategory;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProductCategoryService {

    /**
     * shopId查询店铺商品类别列表
     *
     * @param shopId
     * @return 商品类别列表
     */
    List<ProductCategory> getProductCategoryList(Long shopId);


    /**
     * 批量添加商品类别
     *
     * @param productCategoryList
     * @return
     */
    int batchAddProductCategory(List<ProductCategory> productCategoryList);


    /**
     * 删除指定商品类别
     *
     * @param productCategoryId
     * @param shopId
     * @return
     */
    int removeProductCategory(@Param("productCategoryId") Long productCategoryId, @Param("shopId") Long shopId);

}
