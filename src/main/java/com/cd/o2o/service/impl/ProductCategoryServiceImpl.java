package com.cd.o2o.service.impl;

import com.cd.o2o.dao.ProductCategoryDao;
import com.cd.o2o.entity.ProductCategory;
import com.cd.o2o.service.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("productCategoryService")
public class ProductCategoryServiceImpl implements ProductCategoryService {

    @Autowired
    private ProductCategoryDao productCategoryDao;


    public List<ProductCategory> getProductCategoryList(Long shopId) {
        return productCategoryDao.queryProductCategoryList(shopId);
    }


    public int batchAddProductCategory(List<ProductCategory> productCategoryList) {
        int effectNum = 0;
        if(productCategoryList != null && productCategoryList.size() > 0){
            try{
                effectNum = productCategoryDao.batchInsertProductCategory(productCategoryList);
                if(effectNum <= 0){
                    throw new RuntimeException("商品类别添加失败！");
                }
            }catch (Exception e){
                throw new RuntimeException("batchAddProductCategory error: " + e.getMessage());
            }
        }
        return effectNum;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public int removeProductCategory(Long productCategoryId, Long shopId) {
        int effectNum = 0;
        try{
            effectNum = productCategoryDao.deleteProductCategory(productCategoryId,shopId);
            if(effectNum <= 0){
                throw new RuntimeException("删除商品类别失败!");
            }
        }catch (Exception e){
            throw new RuntimeException("removeProductCategory error: "+e.getMessage());
        }

        return effectNum;
    }


}
