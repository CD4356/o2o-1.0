package com.cd.o2o.dao;

import com.cd.o2o.entity.Product;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductDao {

    /**
     * 查询商品列表并分页，可输入的条件：商品名(模糊)，商品状态，店铺id，商品类别
     *
     * @param productCondition
     * @param rowIndex
     * @param pageSize
     * @return
     */
    List<Product> queryProductList(@Param("productCondition") Product productCondition,
                                   @Param("rowIndex") int rowIndex,
                                   @Param("pageSize") int pageSize);


    /**
     * 根据查询条件查询对应的商品总数
     *
     * @param productCondition
     * @return
     */
    int queryProductCount(@Param("productCondition") Product productCondition);


    /**
     * 根据productId查询指定商品信息
     *
     * @param productId
     * @return
     */
    Product queryProductById(long productId);


    /**
     * 更新商品信息
     *
     * @param product
     * @return
     */
    int updateProduct(Product product);


    /**
     * 插入商品
     *
     * @param product
     * @return
     */
    int insertProduct(Product product);

}
