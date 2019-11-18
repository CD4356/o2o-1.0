package com.cd.o2o.test;

import com.cd.o2o.dto.ImageHolder;
import com.cd.o2o.dto.ShopExecution;
import com.cd.o2o.entity.Area;
import com.cd.o2o.entity.Person;
import com.cd.o2o.entity.Shop;
import com.cd.o2o.entity.ShopCategory;
import com.cd.o2o.enums.ShopStateEnum;
import com.cd.o2o.service.ShopService;
import org.junit.Ignore;
import org.junit.Test;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Date;

import static org.junit.Assert.assertEquals;

public class ShopServiceTest extends BaseTest {

    @Resource
    private ShopService shopService;

    @Test
    public void testGetShopList(){
        Shop shop = new Shop();
        ShopCategory shopCategory = new ShopCategory();
        shopCategory.setShopCategoryId(12L);
        shop.setShopCategory(shopCategory);
        ShopExecution shopExecution = shopService.getShopList(shop,1,3);
        System.out.println("xx店铺总数：" + shopExecution.getCount());
        System.out.println("xx 店铺列表的大小：" + shopExecution.getShopList().size());
    }

    @Test
    @Ignore
    public void testModifyShop() throws FileNotFoundException {
        Shop sh = shopService.getByShopId(22L);
        System.out.println("修改前的图片地址："+sh.getShopImg());
        Shop shop = new Shop();
        shop.setShopId(22L);
        shop.setShopName("暧昧咖啡点");

        File shopImg = new File("C:/Users/CD4356/Desktop/1.jpg");
        InputStream inputStream = new FileInputStream(shopImg);
        ImageHolder imageHolder = new ImageHolder(inputStream,"1.jpg");
        ShopExecution shopExecution = shopService.modifyShop(shop,imageHolder);

        System.out.println("修改后的图片地址："+shopExecution.getShop().getShopImg());
    }

    @Test
    @Ignore
    public void testAddShop() throws FileNotFoundException {
        Shop shop = new Shop();

        Person person = new Person();
        person.setUserId(1L);
        ShopCategory shopCategory = new ShopCategory();
        shopCategory.setShopCategoryId(7L);
        Area area = new Area();
        area.setAreaId(2);

        shop.setShopName("咖啡馆");
        shop.setShopDesc("test2");
        shop.setShopAddress("test2");
        shop.setShopPhone("test2");

        shop.setPriority(0);
        shop.setAdvice("审核中");
        shop.setEnableStatus(ShopStateEnum.CHECK.getState());
        shop.setCreateTime(new Date());
        shop.setLastEditTime(new Date());
        shop.setOwner(person);

        shop.setShopCategory(shopCategory);
        shop.setArea(area);

        File shopImg = new File("C:/Users/CD4356/Desktop/1.jpg");
        InputStream inputStream = new FileInputStream(shopImg);
        ImageHolder imageHolder = new ImageHolder(inputStream,shopImg.getName());
        ShopExecution shopExecution = shopService.addShop(shop,imageHolder);

        assertEquals(ShopStateEnum.CHECK.getState(),shopExecution.getState());
    }

}
