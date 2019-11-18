package com.cd.o2o.web.shopadmin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/shop_admin")
public class ShopAdminController {

    @RequestMapping(value = "/shop_operation")  //路由
    public String shopOperation(){
        return "shop/shop_operation";
    }

    @RequestMapping("/shop_list")  //路由
    public String shopList(){
        return "shop/shop_list";
    }

    @RequestMapping(value = "/shop_management")  //路由
    public String shopManagement(){
        return "shop/shop_management";
    }

    @RequestMapping(value = "/product_category_management")  //路由
    public String productCategoryManagement(){
        return "shop/product_category_management";
    }

    @RequestMapping(value = "/product_management")  //路由
    public String productManagement(){
        return "shop/product_management";
    }

    @RequestMapping(value = "/product_operation")  //路由
    public String productOperation(){
        return "shop/product_operation";
    }

}
