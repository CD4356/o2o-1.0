package com.cd.o2o.web.frontend;

import com.cd.o2o.dto.ShopExecution;
import com.cd.o2o.entity.Area;
import com.cd.o2o.entity.Shop;
import com.cd.o2o.entity.ShopCategory;
import com.cd.o2o.service.AreaService;
import com.cd.o2o.service.ShopCategoryService;
import com.cd.o2o.service.ShopService;
import com.cd.o2o.util.HttpServletRequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/frontend")
public class ShopListController {

    @Autowired
    private AreaService areaService;
    @Autowired
    private ShopService shopService;
    @Autowired
    private ShopCategoryService shopCategoryService;


    /**
     * 返回商品列表页里的shopCategory列表(二级店铺分类列表，即parentId不为null的店铺分类)，以及区域分类列表
     *
     * @param request
     * @return
     */
    @RequestMapping("/shop_page_info_list")
    @ResponseBody //@ResponseBody注解会将这个方法的返回值转换为JSON形式的数据，返回到response中,可以抽象理解成response.getWriter.write(JSON.toJSONString(map));
    private Map<String,Object> shopPageInfoList(HttpServletRequest request){
        Map<String,Object> map = new HashMap<String, Object>();

        List<ShopCategory> shopCategoryList = null;
        //试着从前端请求中获取parentId
        long parentId = HttpServletRequestUtil.getLong(request,"parentId");
        if(parentId != -1){
            try{
                //如果parentId存在，则取出该一级ShopCategory下的二级ShopCategory列表
                ShopCategory shopCategoryCondition = new ShopCategory();
                ShopCategory parentShopCategory = new ShopCategory();
                parentShopCategory.setShopCategoryId(parentId);
                shopCategoryCondition.setParent(parentShopCategory);
                shopCategoryList = shopCategoryService.getShopCategoryList(shopCategoryCondition);
            }catch (Exception e){
                map.put("success",false);
                map.put("errorMsg",e.getMessage());
                return map;
            }
        }else{
            try{
                //如果parentId为null，则取出所有一级ShopCategory列表
                shopCategoryList = shopCategoryService.getShopCategoryList(null);
            }catch (Exception e){
                map.put("success",false);
                map.put("errorMsg",e.getMessage());
                return map;
            }
        }
        map.put("shopCategoryList",shopCategoryList);

        List<Area> areaList = null;
        try{
            //获取区域列表信息
            areaList = areaService.getAreaList();
            map.put("areaList", areaList);
        }catch (Exception e){
            map.put("success",false);
            map.put("errorMsg",e.getMessage());
            return map;
        }

        map.put("success",true);
        return map;
    }


    /**
     * 获取指定查绚条件下的店铺列表
     *
     * @param request
     * @return
     */
    @RequestMapping("/shop_list")
    @ResponseBody //@ResponseBody注解会将这个方法的返回值转换为JSON形式的数据，返回到response中,可以抽象理解成response.getWriter.write(JSON.toJSONString(map));
    private Map<String,Object> shopList(HttpServletRequest request){
        Map<String,Object> map = new HashMap<String, Object>();

        //获取前端传递过来的页码
        int pageIndex = HttpServletRequestUtil.getInt(request,"pageIndex");
        //获取一页需要显示的数据条数
        int pageSize = HttpServletRequestUtil.getInt(request,"pageSize");

        if((pageIndex >-1) && (pageSize >-1)){
            //试着获取parentId，即一级类别的id
            long parentId = HttpServletRequestUtil.getLong(request,"parentId");
            //试着获取指定的二级类别的id
            long shopCategoryId = HttpServletRequestUtil.getLong(request,"shopCategoryId");
            //试着获取区域id
            int areaId = HttpServletRequestUtil.getInt(request,"areaId");
            //试着获取模糊查询的名字
            String shopName = HttpServletRequestUtil.getString(request,"shopName");
            //组合查询条件，并将条件封装到ShopCondition对象里返回
            Shop shopCondition = compactShopCondition(parentId,shopCategoryId,areaId,shopName);
            ShopExecution shopExecution = shopService.getShopList(shopCondition,pageIndex,pageSize);
            map.put("shopList",shopExecution.getShopList());
            map.put("count",shopExecution.getCount());
            map.put("success",true);
        } else {
            map.put("success", false);
            map.put("errMsg", "empty pageSize or pageIndex");
        }

        return map;
    }


    /**
     * 组合查询条件，并将条件封装到ShopCondition对象里返回
     *
     * @param parentId
     * @param shopCategoryId
     * @param areaId
     * @param shopName
     * @return
     */
    private Shop compactShopCondition(long parentId, long shopCategoryId, int areaId, String shopName) {
        Shop shopCondition = new Shop();
        if(parentId != -1L){
            // 查询指定一级ShopCategory下的所有二级ShopCategory里面的店铺列表
            ShopCategory childShopCategory = new ShopCategory();
            ShopCategory parentShopCategory = new ShopCategory();
            parentShopCategory.setShopCategoryId(parentId);
            childShopCategory.setParent(parentShopCategory);
            shopCondition.setShopCategory(childShopCategory);
        }
        if(shopCategoryId != -1L){
            //查询指定二级ShopCategory下的所有店铺列表
            ShopCategory shopCategory = new ShopCategory();
            shopCategory.setShopCategoryId(shopCategoryId);
            shopCondition.setShopCategory(shopCategory);
        }
        if(areaId != -1){
            //查询位于指定区域下的所有店铺列表
            Area area  = new Area();
            area.setAreaId(areaId);
            shopCondition.setArea(area);
        }
        if(shopName != null){
            // 查询名字里包含shopName的店铺列表，这是模糊查询
            shopCondition.setShopName(shopName);
        }
        //能在前端展示的店铺都是审核成功的店铺
        shopCondition.setEnableStatus(1);
        return shopCondition;
    }


}
