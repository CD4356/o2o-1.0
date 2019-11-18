package com.cd.o2o.dao;

import com.cd.o2o.entity.Shop;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShopDao {
    /**
     * 新增店铺
     * @param shop
     * @return
     */
    int insertShop(Shop shop);


    /**
     * 更新店铺
     * @param shop
     * @return
     */
    int updateShop(Shop shop);


    /**
     * 删除店铺
     * @param shopId
     * @return
     */
    int deleteShop(Long shopId);


    /**
     * 通过店铺id获取店铺信息
     * @param shopId
     * @return
     */
    Shop queryByShopId(Long shopId);


    /**
     * 分页查询店铺列表， 可输入的查询条件：店铺名(模糊查询)、店铺状态、店铺类别、区域id、用户id
     * @param shopCondition
     * @param rowIndex 从第几行开始取数据
     * @param pageSize 限制一页的条数
     * @return
     */
    List<Shop> queryShopList(@Param("shopCondition") Shop shopCondition,
                             @Param("rowIndex") int rowIndex,
                             @Param("pageSize") int pageSize);


    /**
     * 返回queryShopList总数
     * @param shopCondition
     * @return
     */
    int queryShopCount(@Param("shopCondition") Shop shopCondition);

}
