package com.cd.o2o.web.shopadmin;

import com.cd.o2o.entity.ProductCategory;
import com.cd.o2o.entity.Shop;
import com.cd.o2o.service.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/shop_admin")
public class ProductCategoryManagementController {

    @Autowired
    private ProductCategoryService productCategoryService;


    /**
     * 获取商品分类列表，提交到request作用域中，
     * 然后前端通过ajax获取到提交数据，并渲染到前端页面中，方便添加商品信息
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/get_product_category_list",method = RequestMethod.GET)
    @ResponseBody  //@ResponseBody注解会将这个方法的返回值转换为JSON形式的数据，返回到response中,可以抽象理解成response.getWriter.write(JSON.toJSONString(map));
    private Map<String,Object> getProductCategoryList(HttpServletRequest request){
        Map<String,Object> map = new HashMap<String, Object>();
        //从session中获取当前shop对象
        Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
        List<ProductCategory> productCategoryList = null;
        if(currentShop != null && currentShop.getShopId() != null){
            productCategoryList = productCategoryService.getProductCategoryList(currentShop.getShopId());
            map.put("success",true);
            map.put("productCategoryList",productCategoryList);
        }else {
            map.put("success",false);
            map.put("errorMsg","店铺id为空 或 找不到店铺id！");
        }
        return map;
    }


    /**
     * 批量添加商品分类信息
     *
     * @param productCategoryList
     * @param request
     * @return
     */
    @RequestMapping(value = "/batch_add_product_category", method = RequestMethod.POST)
    @ResponseBody  //@ResponseBody注解会将这个方法的返回值转换为JSON形式的数据，返回到response中,可以抽象理解成response.getWriter.write(JSON.toJSONString(map));
    private Map<String,Object> batchAddProductCategory(@RequestBody List<ProductCategory> productCategoryList,
                                                       HttpServletRequest request){
        Map<String,Object> map = new HashMap<String, Object>();
        //从session中获取当前shop对象
        Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");

        //给商品分类设置一些基本属性
        for (ProductCategory productCategory : productCategoryList) {
            productCategory.setCreateTime(new Date());
            productCategory.setShopId(currentShop.getShopId());
        }

        // 空值判断
        if(productCategoryList != null && productCategoryList.size() >0){
            try{
                int effectNum = productCategoryService.batchAddProductCategory(productCategoryList);
                if(effectNum <= 0){
                    map.put("success",false);
                    map.put("errorMsg","商品类别添加失败!");
                }else {
                    map.put("success",true);
                }
            }catch (RuntimeException e){
                map.put("success",false);
                map.put("errorMsg",e.getMessage());
                return map;
            }
        }else {
            map.put("success",false);
            map.put("errorMsg","至少输入一个商品类别!");
        }
        return map;
    }


    /**
     * 移除指定商品分类
     *
     * @param productCategoryId
     * @param request
     * @return
     */
    @RequestMapping(value = "/remove_product_category", method = RequestMethod.POST)
    @ResponseBody  //@ResponseBody注解会将这个方法的返回值转换为JSON形式的数据，返回到response中,可以抽象理解成response.getWriter.write(JSON.toJSONString(map));
    private Map<String,Object> removeProductCategory(Long productCategoryId, HttpServletRequest request){
        Map<String,Object> map = new HashMap<String, Object>();
        if(productCategoryId != null && productCategoryId >= 0){
            try{
                Shop shop = (Shop) request.getSession().getAttribute("currentShop");
                int effectNum = productCategoryService.removeProductCategory(productCategoryId,shop.getShopId());
                if(effectNum <= 0){
                    map.put("success",false);
                    map.put("errorMsg","商品类别删除失败!");
                }else {
                    map.put("success",true);
                }
            }catch (RuntimeException e){
                map.put("success",false);
                map.put("errorMsg",e.getMessage());
                return map;
            }
        }else {
            map.put("success",false);
            map.put("errorMsg","至少选择一个商品类别!");
        }
        return map;
    }


}
