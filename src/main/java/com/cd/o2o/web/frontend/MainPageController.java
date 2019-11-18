package com.cd.o2o.web.frontend;

import com.cd.o2o.entity.HeadLine;
import com.cd.o2o.entity.ShopCategory;
import com.cd.o2o.service.HeadLineService;
import com.cd.o2o.service.ShopCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/frontend")
public class MainPageController {

    @Autowired
    private HeadLineService headLineService;
    @Autowired
    private ShopCategoryService shopCategoryService;


    /**
     * 初始化前端展示系统的主页信息，包括获取一级ShopCategory列表、可用头条信息
     *
     * @return
     */
    @RequestMapping("/main_page_info_list")
    @ResponseBody //@ResponseBody注解会将这个方法的返回值转换为JSON形式的数据，返回到response中,可以抽象理解成response.getWriter.write(JSON.toJSONString(map));
    private Map<String, Object> mainPageInfoList(){
        Map<String,Object> map = new HashMap<String, Object>();
        try{
            //获取一级ShopCategory列表（即product_id为空的ShopCategory）
            List<ShopCategory> shopCategoryList = shopCategoryService.getShopCategoryList(null);
            map.put("shopCategoryList",shopCategoryList);
        }catch (Exception e){
            map.put("success",false);
            map.put("errorMsg",e.getMessage());
            return map;
        }
        try{
            //获取可用的头条信息（即enableStatus状态为可用(1)的头条信息）
            HeadLine headLine = new HeadLine();
            headLine.setEnableStatus(1);
            List<HeadLine> headLineList = headLineService.getHeadLineList(headLine);
            map.put("headLineList",headLineList);
        }catch (Exception e){
            map.put("success",false);
            map.put("errorMsg",e.getMessage());
            return map;
        }
        map.put("success",true);
        return map;
    }


}
