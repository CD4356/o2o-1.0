package com.cd.o2o.dao;

import com.cd.o2o.entity.ShopCategory;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShopCategoryDao {
    /**
     * 根据传入的查询条件返回店铺类别列表
     *
     * @return
     */
    List<ShopCategory> queryShopCategory(@Param("shopCategoryCondition") ShopCategory shopCategoryCondition);
}

