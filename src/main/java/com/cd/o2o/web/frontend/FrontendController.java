package com.cd.o2o.web.frontend;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/frontend")
public class FrontendController {


    /**
     * 首页路由
     *
     * @return
     */
    @RequestMapping("/index")
    private String index(){
        return "frontend/index";
    }

    /**
     * 店铺列表路由
     *
     * @return
     */
    @RequestMapping("/to_shop_list")
    private String toShopList(){
        return "frontend/shop_list";
    }


    /**
     * 店铺列表路由
     *
     * @return
     */
    @RequestMapping("/shop_detail")
    private String shopDetail(){
        return "frontend/shop_detail";
    }

    /**
     * 商品列表路由
     *
     * @return
     */
    @RequestMapping(value = "/product_detail", method = RequestMethod.GET)
    private String productDetail(){
        return "frontend/product_detail";
    }


}
