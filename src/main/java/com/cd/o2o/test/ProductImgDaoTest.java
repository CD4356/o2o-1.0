package com.cd.o2o.test;

import com.cd.o2o.dao.ProductImgDao;
import com.cd.o2o.entity.ProductImg;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ProductImgDaoTest extends BaseTest {

    @Autowired
    private ProductImgDao productImgDao;

    @Test
    public void testBInsertProductImg() {
        List<ProductImg> productImgList = new ArrayList<ProductImg>();

        ProductImg productImg1 = new ProductImg();
        productImg1.setProductId(2L);
        productImg1.setImgDesc("详情图1描述");
        productImg1.setPriority(2);
        productImg1.setProductDetailImg("详情图1地址");
        productImgList.add(productImg1);

        ProductImg productImg2 = new ProductImg();
        productImg2.setProductId(2L);
        productImg2.setImgDesc("详情图2描述");
        productImg2.setPriority(1);
        productImg2.setProductDetailImg("详情图2地址");
        productImgList.add(productImg2);

        int effectNum = productImgDao.batchInsertProductImg(productImgList);
        assertEquals(2, effectNum);
    }


    @Test
    public void testCDeleteProductImgByProductId(){
        long productId = 2L;
        int effectedNum = productImgDao.deleteProductImgByProductId(productId);
        assertEquals(2, effectedNum);
    }

}
