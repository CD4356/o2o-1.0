package com.cd.o2o.web.frontend;

import com.cd.o2o.dto.ProductExecution;
import com.cd.o2o.entity.Product;
import com.cd.o2o.entity.ProductCategory;
import com.cd.o2o.entity.Shop;
import com.cd.o2o.service.ProductCategoryService;
import com.cd.o2o.service.ProductService;
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
public class ShopDetailController {

    @Autowired
    private ShopService shopService;
    @Autowired
    private ProductCategoryService productCategoryService;
    @Autowired
    private ProductService productService;


    @RequestMapping("/shop_detail_info")
    @ResponseBody
    private Map<String,Object> shopDetailInfo(HttpServletRequest request){
        Map<String,Object> map = new HashMap<String, Object>();
        // 获取前端传递过来的ShopId
        Long shopId = HttpServletRequestUtil.getLong(request,"shopId");
        Shop shop = null;
        List<ProductCategory> productCategoryList = null;
        if(shopId != -1){
            // 获取店铺id为shopId的店铺信息
            shop = shopService.getByShopId(shopId);
            // 获取店铺id为shopId的店铺下的商品类别列表
            productCategoryList = productCategoryService.getProductCategoryList(shopId);
            map.put("shop",shop);
            map.put("productCategoryList",productCategoryList);
            map.put("success",true);
        }else {
            map.put("success",false);
            map.put("errorMsg","empty shopId");
        }
        return map;
    }


    /**
     * 根据查询条件分页列出该店铺下的所有店铺信息
     *
     * @param request
     * @return
     */
    @RequestMapping("/product_list_by_shop")
    @ResponseBody
    private Map<String,Object> productListByShop(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<String, Object>();
        // 获取页码
        int pageIndex = HttpServletRequestUtil.getInt(request,"pageIndex");
        // 获取页长，即一页限制显示的条数
        int pageSize = HttpServletRequestUtil.getInt(request,"pageSize");
        // 获取shopId
        Long shopId = HttpServletRequestUtil.getLong(request,"shopId");
        if((pageIndex > -1) && (pageSize > -1) && (shopId != -1)){
            // 试着获取shopName
            String productName = HttpServletRequestUtil.getString(request,"productName");
            // 试着获取productCategoryId
            Long productCategoryId = HttpServletRequestUtil.getLong(request,"productCategoryId");
            // 组合查询条件
            Product productCondition = compactProductCondition(productName,productCategoryId,shopId);
            ProductExecution productExecution = productService.getProductList(productCondition,pageIndex,pageSize);
            map.put("productList",productExecution.getProductList());
            map.put("count",productExecution.getCount());
            map.put("success",true);
        }else {
            map.put("success",false);
            map.put("errorMsg","empty shopId or pageIndex or pageSize!");
        }
        return map;
    }


    /**
     * 组合查询条件，并封装到Product实体类对象中返回
     *
     * @param productName
     * @param productCategoryId
     * @param shopId
     * @return
     */
    private Product compactProductCondition(String productName, Long productCategoryId, Long shopId) {
        Product productCondition = new Product();
        Shop shop = new Shop();
        shop.setShopId(shopId);
        productCondition.setShop(shop);
        if(productName != null){
            // 根据商品名进行模糊查询
            productCondition.setProductName(productName);
        }
        if(productCategoryId != -1){
            // 查询指定商品类别下的商品列表
            ProductCategory productCategory = new ProductCategory();
            productCategory.setProductCategoryId(productCategoryId);
            productCondition.setProductCategory(productCategory);
        }
        return productCondition;
    }

}
