package com.cd.o2o.dao;

import com.cd.o2o.entity.ProductCategory;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductCategoryDao {

    /**
     * 通过shopId查询店铺商品类别列表
     *
     * @param shopId
     * @return 商品类别列表
     */
    List<ProductCategory> queryProductCategoryList(Long shopId);


    /**
     * 批量插入商品类别
     *
     * @param productCategoryList
     * @return
     */
    int batchInsertProductCategory(List<ProductCategory> productCategoryList);


    /**
     * 删除指定商品类别
     *
     * @param productCategoryId
     * @param shopId
     * @return
     */
    int deleteProductCategory(@Param("productCategoryId") Long productCategoryId, @Param("shopId") Long shopId);

}
