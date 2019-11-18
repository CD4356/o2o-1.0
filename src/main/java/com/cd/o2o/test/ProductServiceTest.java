package com.cd.o2o.test;

import com.cd.o2o.dto.ImageHolder;
import com.cd.o2o.entity.Product;
import com.cd.o2o.entity.ProductCategory;
import com.cd.o2o.entity.Shop;
import com.cd.o2o.service.ProductService;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ProductServiceTest extends BaseTest{

    @Autowired
    private ProductService productService;

    @Test
    @Ignore
    public void testAddProduct() throws FileNotFoundException {
        Shop shop = new Shop();
        shop.setShopId(29L);
        ProductCategory productCategory = new ProductCategory();
        productCategory.setProductCategoryId(28L);

        Product product = new Product();
        product.setProductName("奶绿");
        product.setProductDesc("~捧在手心里的 ~ 是你醇香的爱~");
        product.setNormalPrice(20.00);
        product.setPromotionPrice(18.00);
        product.setPriority(20);
        product.setEnableStatus(1);
        product.setCreateTime(new Date());
        product.setLastEditTime(new Date());
        product.setShop(shop);
        product.setProductCategory(productCategory);

        //创建商品缩略图文件流
        File thumbnailFile = new File("C:/Users/CD4356/Desktop/奶茶/5.jpg");
        InputStream inputStream = new FileInputStream(thumbnailFile);
        ImageHolder thumbnail = new ImageHolder(inputStream,thumbnailFile.getName());

        //创建两个商品详情图，并将它们添加到商品详情图列表中
        File productImg1 = new File("C:/Users/CD4356/Desktop/奶茶/1.jpg");
        InputStream inputStream1 = new FileInputStream(productImg1);
        ImageHolder imageHolder1 = new ImageHolder(inputStream1,productImg1.getName());

        File productImg2 = new File("C:/Users/CD4356/Desktop/奶茶/2.jpg");
        InputStream inputStream2 = new FileInputStream(productImg2);
        ImageHolder imageHolder2 = new ImageHolder(inputStream2,productImg2.getName());

        List<ImageHolder> productImgList = new ArrayList<ImageHolder>();
        productImgList.add(imageHolder1);
        productImgList.add(imageHolder2);

        // 判断是否添加成功
        int effectNum =  productService.addProduct(product,thumbnail,productImgList);
        assertEquals(1,effectNum);
    }

    @Test
    @Ignore
    public void testModifyProduct() throws FileNotFoundException {
        Shop shop = new Shop();
        shop.setShopId(29L);
        ProductCategory productCategory = new ProductCategory();
        productCategory.setProductCategoryId(29L);

        Product product = new Product();
        product.setProductId(20L);
        product.setProductDesc("~温柔的唤醒 ~ 这便是晨曦的魅力~");
        product.setLastEditTime(new Date());
        product.setShop(shop);
        product.setProductCategory(productCategory);

        //创建两个商品详情图，并将它们添加到商品详情图列表中
        File productImg1 = new File("C:/Users/CD4356/Pictures/奶茶/7.jpg");
        InputStream inputStream1 = new FileInputStream(productImg1);
        ImageHolder imageHolder1 = new ImageHolder(inputStream1,productImg1.getName());

        File productImg2 = new File("C:/Users/CD4356/Pictures/奶茶/11.jpg");
        InputStream inputStream2 = new FileInputStream(productImg2);
        ImageHolder imageHolder2 = new ImageHolder(inputStream2,productImg2.getName());

        List<ImageHolder> productImgHolderList = new ArrayList<ImageHolder>();
        productImgHolderList.add(imageHolder1);
        productImgHolderList.add(imageHolder2);

        // 判断是否添加成功
        int effectNum =  productService.modifyProduct(product,null,productImgHolderList);
        System.out.println("影响行数："+effectNum);
    }


    @Test
    public void test2ModifyProduct() throws FileNotFoundException {
        Product product = new Product();
        product.setProductId(21L);
        product.setEnableStatus(0);
        Shop shop = new Shop();
        shop.setShopId(29L);
        product.setShop(shop);
        // 判断是否添加成功
        int effectNum =  productService.modifyProduct(product,null,null);
        System.out.println("影响行数："+effectNum);
    }


}
