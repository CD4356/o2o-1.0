package com.cd.o2o.test;

import com.cd.o2o.dao.ProductDao;
import com.cd.o2o.dao.ProductImgDao;
import com.cd.o2o.entity.Product;
import com.cd.o2o.entity.ProductCategory;
import com.cd.o2o.entity.ProductImg;
import com.cd.o2o.entity.Shop;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)  //按照方法名称的字母顺序进行UT
public class ProductDaoTest extends BaseTest {

    @Autowired
    private ProductDao productDao;
    @Autowired
    private ProductImgDao productImgDao;

    @Test
    @Ignore
    public void testAInsertProduct() throws Exception {
        Shop shop = new Shop();
        shop.setShopId(16L);
        ProductCategory pc = new ProductCategory();
        pc.setProductCategoryId(23L);

        Product product = new Product();
        product.setProductName("测试1");
        product.setProductDesc("测试Desc1");
        product.setNormalPrice(20.00);
        product.setPromotionPrice(18.00);
        product.setPriority(1);
        product.setEnableStatus(1);
        product.setCreateTime(new Date());
        product.setLastEditTime(new Date());
        product.setShop(shop);
        product.setProductCategory(pc);
        product.setImgAddress("缩略图地址");

        // 判断添加是否成功
        int effectedNum1 = productDao.insertProduct(product);
        assertEquals(1, effectedNum1);

        List<ProductImg> productImgList = new ArrayList<ProductImg>();
        ProductImg productImg1 = new ProductImg();
        productImg1.setProductId(product.getProductId());
        productImg1.setImgDesc("详情图1描述");
        productImg1.setPriority(2);
        productImg1.setProductDetailImg("详情图1地址");
        productImgList.add(productImg1);
        ProductImg productImg2 = new ProductImg();
        productImg2.setProductId(product.getProductId());
        productImg2.setImgDesc("详情图2描述");
        productImg2.setPriority(1);
        productImg2.setProductDetailImg("详情图2地址");
        productImgList.add(productImg2);
        int effectNum = productImgDao.batchInsertProductImg(productImgList);
        assertEquals(2, effectNum);

        int effectedNum = productImgDao.deleteProductImgByProductId(product.getProductId());
        assertEquals(2, effectedNum);
    }


    @Test
    public void testQueryProductById(){
        long productId = 55L;
        Product product = productDao.queryProductById(productId);
        System.out.println("详情图数目为：" + product.getProductImgList().size());
    }


    @Test
    @Ignore
    public void testUpdateProduct(){
        ProductCategory productCategory = new ProductCategory();
        productCategory.setProductCategoryId(16L);

        Shop shop = new Shop();
        shop.setShopId(23L);

        Product product = new Product();
        product.setProductId(17L);
        product.setProductCategory(productCategory);
        product.setShop(shop);
        product.setProductDesc("美食美味，美味美食！");
        product.setPriority(92);
        int effectedNum = productDao.updateProduct(product);
        assertEquals(1, effectedNum);
    }

    @Test
    public void testQueryProductList(){
        Product productCondition = new Product();
        productCondition.setProductName("晨");
        List<Product> productList = productDao.queryProductList(productCondition,0,3);

        System.out.println("pageSize的大小："+productList.size());
        int count = productDao.queryProductCount(productCondition);
        System.out.println("商品总数："+count);
    }


}
