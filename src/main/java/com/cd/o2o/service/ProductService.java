package com.cd.o2o.service;

import com.cd.o2o.dto.ImageHolder;
import com.cd.o2o.dto.ProductExecution;
import com.cd.o2o.entity.Product;

import java.util.List;

public interface ProductService {

    /**
     * 查询商品列表并分页，可输入的条件：商品名(模糊)，商品状态，店铺id，商品类别
     *
     * @param productCondition
     * @param pageIndex
     * @param pageSize
     * @return
     */
    ProductExecution getProductList(Product productCondition, int pageIndex, int pageSize);


    /**
     * 添加商品信息以及图片处理
     *
     * @param product 商品信息
     * @param thumbnail 商品缩略图（包含图片流和图片名）
     * @param productImgHolderList 商品详情图集合 （包含图片流和图片名）
     * @return
     */
    int addProduct(Product product, ImageHolder thumbnail, List<ImageHolder> productImgHolderList);


    /**
     * 修改商品信息以及图片修改（缩略图、商品详情图）
     *
     * @param product
     * @param thumbnail
     * @param productImgHolderList
     * @return
     */
    int modifyProduct(Product product, ImageHolder thumbnail, List<ImageHolder> productImgHolderList);


    /**
     * 根据productId获取指定商品信息
     *
     * @param productId
     * @return
     */
    Product getProductById(long productId);
}
