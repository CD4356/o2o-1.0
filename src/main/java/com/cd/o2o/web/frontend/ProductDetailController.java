package com.cd.o2o.web.frontend;

import com.cd.o2o.entity.Product;
import com.cd.o2o.service.ProductService;
import com.cd.o2o.util.HttpServletRequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/frontend")
public class ProductDetailController {

    @Autowired
    private ProductService productService;


    /**
     * 根据商品id获取商品信息
     *
     * @param request
     * @return
     */
    @RequestMapping("/product_detail_info")
    @ResponseBody
    private Map<String,Object> productDetailInfo(HttpServletRequest request){
        Map<String,Object> map = new HashMap<String, Object>();
        // 获取前端传递过来的productId
        long productId = HttpServletRequestUtil.getLong(request,"productId");
        Product product = null;
        // 空值判断
        if(productId != -1){
            // 根据productId获取商品信息，包括商品详情图列表
            product = productService.getProductById(productId);
            map.put("product",product);
            map.put("success",true);
        }else {
            map.put("success",false);
            map.put("errorMsg","empty productId!");
        }
        return map;
    }

}
