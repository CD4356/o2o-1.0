package com.cd.o2o.test;

import com.cd.o2o.dao.ShopDao;
import com.cd.o2o.entity.Area;
import com.cd.o2o.entity.Person;
import com.cd.o2o.entity.Shop;
import com.cd.o2o.entity.ShopCategory;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ShopDaoTest extends BaseTest {

    @Autowired
    private ShopDao shopDao;

    @Test
    public void testQueryShopList(){
        Shop shop = new Shop();

        // 查询指定一级ShopCategory下的所有二级ShopCategory里面的店铺列表
        ShopCategory parentCategory = new ShopCategory();
        parentCategory.setShopCategoryId(6L);
        ShopCategory childCategory = new ShopCategory();
        childCategory.setParent(parentCategory);
        shop.setShopCategory(childCategory);

        List<Shop> shopList = shopDao.queryShopList(shop,0,5);
        int count = shopDao.queryShopCount(shop);
        System.out.println("店铺总数：" + count);
        System.out.println("店铺列表的大小：" + shopList.size());


        //查询指定二级ShopCategory下的所有店铺列表
        ShopCategory shopCategory = new ShopCategory();
        shopCategory.setShopCategoryId(12L);
        shop.setShopCategory(shopCategory);
        shopList = shopDao.queryShopList(shop,0,3);
        count = shopDao.queryShopCount(shop);
        System.out.println("xx店铺总数：" + count);
        System.out.println("xx 店铺列表的大小：" + shopList.size());
    }

    @Test
    @Ignore
    public void queryByShopId(){
        long shopId = 22;
        Shop shop = shopDao.queryByShopId(shopId);
        System.out.println(shop.getShopId());
        System.out.println(shop.getShopName());
        System.out.println(shop.getArea().getAreaId());
        System.out.println(shop.getArea().getAreaName());
        System.out.println(shop.getShopCategory().getShopCategoryId());
        System.out.println(shop.getShopCategory().getShopCategoryName());
    }

    @Test
    @Ignore
    public void testInsertShop(){
        Shop shop = new Shop();
        shop.setShopName("暧昧咖啡馆");
        shop.setShopDesc("一起喝咖啡");
        shop.setShopAddress("爱联区");
        shop.setShopPhone("12335878232");
//        File file = new File("C:/Users/CD4356/Desktop/图标/1.jpg");
        shop.setShopImg("test");
        shop.setPriority(1);
        shop.setAdvice("审核中");
        shop.setEnableStatus(1);
        shop.setCreateTime(new Date());
        shop.setLastEditTime(new Date());

        Person person = new Person();
        person.setUserId(1L);
        shop.setOwner(person);
        ShopCategory shopCategory = new ShopCategory();
        shopCategory.setShopCategoryId(7L);
        shop.setShopCategory(shopCategory);
        Area area = new Area();
        area.setAreaId(2);
        shop.setArea(area);
        int effectedNum = shopDao.insertShop(shop);
//        assertEquals(1,effectedNum);
    }

    @Test
    @Ignore
    public void testUpdateShop(){
        Shop shop = new Shop();
        shop.setShopId(1L);
        shop.setShopName("更新店铺");
        shop.setAdvice("可用");
        shop.setLastEditTime(new Date());
        int effectedNum = shopDao.updateShop(shop);
        assertEquals(1,effectedNum);
    }

}
