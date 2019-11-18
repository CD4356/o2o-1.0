package com.cd.o2o.service.impl;

import com.cd.o2o.dao.ShopDao;
import com.cd.o2o.dto.ImageHolder;
import com.cd.o2o.dto.ShopExecution;
import com.cd.o2o.entity.Shop;
import com.cd.o2o.enums.ShopStateEnum;
import com.cd.o2o.service.ShopService;
import com.cd.o2o.util.ImageUtil;
import com.cd.o2o.util.PageCalculator;
import com.cd.o2o.util.PathUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service("shopService")
public class ShopServiceImpl implements ShopService {

    @Autowired
    private ShopDao shopDao;

    @Transactional(propagation = Propagation.REQUIRED)
    public ShopExecution addShop(Shop shop, ImageHolder thumbnail) {
        if(shop==null){
            return new ShopExecution(ShopStateEnum.NULL_SHOP);
        }
        try {
            //给店铺信息赋初始值
            shop.setPriority(0);
            shop.setAdvice("审核中");
            shop.setEnableStatus(ShopStateEnum.CHECK.getState());
            shop.setCreateTime(new Date());
            shop.setLastEditTime(new Date());

            //1、添加店铺信息
            int effectNum = shopDao.insertShop(shop);
            if(effectNum <= 0){
                //抛出RunTimeException异常(或继承了RunTimeException的子类异常),事务会进行回滚,
                //(如果是抛出Exception异常,事务不会进行回滚)
                throw new RuntimeException("店铺创建失败！");
            }else{
                //图片不为空，则添加图片
                if(thumbnail!= null){
                    try{
                        //添加图片
                        addShopImg(shop, thumbnail);
                    }catch (Exception e){
                        throw new RuntimeException("addShopImg error: " + e.getMessage());
                    }
                    //3、更新店铺的图片地址
                    effectNum = shopDao.updateShop(shop);
                    if(effectNum<=0){
                        //抛出RunTimeException异常(或继承了RunTimeException的子类异常),事务会进行回滚,
                        //(如果是抛出Exception异常,事务不会进行回滚)
                        throw new RuntimeException("更新店铺的图片地址失败！");
                    }
                }
            }
        }catch (Exception e){
            throw new RuntimeException("addShop error: " + e.getMessage());
        }
        return new ShopExecution(ShopStateEnum.CHECK,shop);
    }


    /**
     * 生成店铺缩略图，并将存储路径设置到Shop实体类属性中
     *
     * @param shop
     * @param thumbnail
     */
    private void addShopImg(Shop shop, ImageHolder thumbnail) {
        //获取shop图片目录的相对路径
        String dest = PathUtil.getShopImagePath(shop.getShopId());
        //生成缩略图,并返回新生产图片的相对路径
        String shopImgAddress = ImageUtil.generateThumbnail(thumbnail,dest);
        //设置shop图片地址
        shop.setShopImg(shopImgAddress);
    }


    /**
     * 修改店铺信息
     *
     * @param shop
     * @param thumbnail
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public ShopExecution modifyShop(Shop shop, ImageHolder thumbnail) {
        //1、判断shop信息是否为空
        if(shop==null || shop.getShopId()==null){
            return new ShopExecution(ShopStateEnum.NULL_SHOP);
        }else{
            //2、判断是否需要处理图片
            try{
                if(thumbnail !=null){
                    if(thumbnail.getImage()!=null && thumbnail.getImageName()!=null && !"".equals(thumbnail.getImageName())){
                        Shop tempShop = shopDao.queryByShopId(shop.getShopId());
                        //判断原先是否有图片存在
                        if(tempShop.getShopImg()!=null){
                            //删除原有的图片
                            ImageUtil.deleteFileOrPath(tempShop.getShopImg());
                        }
                        //添加新图片
                        addShopImg(shop,thumbnail);
                    }
                }

                //3、更新店铺信息
                shop.setLastEditTime(new Date());
                int effectNum = shopDao.updateShop(shop);
                if(effectNum <= 0){
                    return new ShopExecution(ShopStateEnum.INNER_ERROR);
                }else{
                    shop = shopDao.queryByShopId(shop.getShopId());
                    return new ShopExecution(ShopStateEnum.SUCCESS,shop);
                }
            }catch (Exception e){
                throw new RuntimeException("modify error: "+e.getMessage());
            }
        }
    }


    /**
     * 通过店铺id获取店铺信息
     * @param shopId
     * @return
     */
    public Shop getByShopId(Long shopId) {
        return shopDao.queryByShopId(shopId);
    }


    /**
     * 根据查询条件，获取指定店铺列表
     *
     * @param shopCondition
     * @param pageIndex
     * @param pageSize
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public ShopExecution getShopList(Shop shopCondition, int pageIndex, int pageSize) {
        int rowIndex = PageCalculator.calculatorRowIndex(pageIndex,pageSize);
        List<Shop> shopList = shopDao.queryShopList(shopCondition,rowIndex,pageSize);
        int count = shopDao.queryShopCount(shopCondition);
        ShopExecution shopExecution = new ShopExecution();
        if(shopList != null){
            shopExecution.setShopList(shopList);
            shopExecution.setCount(count);
        }else {
            shopExecution.setState(ShopStateEnum.INNER_ERROR.getState());
        }

        return shopExecution;
    }


}
