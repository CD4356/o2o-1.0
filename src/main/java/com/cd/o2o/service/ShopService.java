package com.cd.o2o.service;

import com.cd.o2o.dto.ImageHolder;
import com.cd.o2o.dto.ShopExecution;
import com.cd.o2o.entity.Shop;
import org.springframework.stereotype.Repository;

@Repository
public interface ShopService {

    /**
     * 添加店铺
     *
     * @param shop
     * @param thumbnail
     * @return
     */
    ShopExecution addShop(Shop shop, ImageHolder thumbnail);


    /**
     * 修改店铺信息
     *
     * @param shop
     * @param thumbnail
     * @return
     */
    ShopExecution modifyShop(Shop shop, ImageHolder thumbnail);


    /**
     * 通过店铺id获取店铺信息
     * @param shopId
     * @return
     */
    Shop getByShopId(Long shopId);


    /**
     * 根据查询条件和分页信息，获取店铺列表和店铺总数
     *
     * @param shopCondition
     * @param pageIndex
     * @param pageSize
     * @return
     */
    ShopExecution getShopList(Shop shopCondition, int pageIndex, int pageSize);

}
