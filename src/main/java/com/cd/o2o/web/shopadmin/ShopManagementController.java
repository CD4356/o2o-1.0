package com.cd.o2o.web.shopadmin;

import com.cd.o2o.dto.ImageHolder;
import com.cd.o2o.dto.ShopExecution;
import com.cd.o2o.entity.Area;
import com.cd.o2o.entity.Person;
import com.cd.o2o.entity.Shop;
import com.cd.o2o.entity.ShopCategory;
import com.cd.o2o.enums.ShopStateEnum;
import com.cd.o2o.service.AreaService;
import com.cd.o2o.service.ShopCategoryService;
import com.cd.o2o.service.ShopService;
import com.cd.o2o.util.HttpServletRequestUtil;
import com.cd.o2o.util.VerifyCodeUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/shop_admin")
public class ShopManagementController {

    @Autowired
    private ShopService shopService;
    @Autowired
    private ShopCategoryService shopCategoryService;
    @Autowired
    private AreaService areaService;


    /**
     * 获取店铺列表
     * @param request
     * @return
     */
    @RequestMapping(value = "/get_shop_list",method = RequestMethod.GET)
    @ResponseBody  //@ResponseBody注解会将这个方法的返回值转换为JSON形式的数据，返回到response中,可以抽象理解成response.getWriter.write(JSON.toJSONString(map));
    private Map<String,Object> getShopList(HttpServletRequest request){
        Map<String,Object> map = new HashMap<String, Object>();
        //从session中获取person信息
        Person user = (Person) request.getSession().getAttribute("person");
        try{
            Shop shop = new Shop();
            shop.setOwner(user);
            ShopExecution shopExecution = shopService.getShopList(shop,0,100);
            map.put("shopList",shopExecution.getShopList());
            map.put("user",user);
            map.put("success",true);
        }catch (Exception e){
            map.put("success",false);
            map.put("errorMsg",e.getMessage());
        }
        return map;
    }


    /**
     * 获取店铺分类和区域信息,(然后前端通过Ajax获取这些信息,进行页面的局部刷新)
     * @return
     */
    @RequestMapping("/get_shop_info")
    @ResponseBody  //@ResponseBody注解会将这个方法的返回值转换为JSON形式的数据，返回到response中,可以抽象理解成response.getWriter.write(JSON.toJSONString(map));
    private Map<String,Object> getShopInfo(){
        Map<String,Object> map = new HashMap<String, Object>();
        try{
            List<ShopCategory> shopCategoryList = shopCategoryService.getShopCategoryList(new ShopCategory());
            List<Area> areaList = areaService.getAreaList();
            map.put("shopCategoryList",shopCategoryList);
            map.put("areaList",areaList);
            map.put("success",true);
        }catch (Exception e){
            map.put("success",false);
            map.put("error:",e.getMessage());
        }
        return map;
    }


    /**
     * 注册店铺
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/register_shop",method = RequestMethod.POST)
    @ResponseBody  //@ResponseBody注解会将这个方法的返回值转换为JSON形式的数据，返回到response中,可以抽象理解成response.getWriter.write(JSON.toJSONString(map));
    private Map<String,Object> registerShop(HttpServletRequest request){
        Map<String,Object> map = new HashMap<String, Object>();
        if(!VerifyCodeUtil.checkVerifyCode(request)){
            map.put("success",false);
            map.put("errorMsg","验证码错误!");
            return map;
        }
        //1、接收前端上传的店铺信息
        String shopStr = HttpServletRequestUtil.getString(request,"shopStr");

        ObjectMapper mapper = new ObjectMapper();
        Shop shop = null;
        try{
            //通过ObjectMapper对象将前台上传的字符串对象转化成Shop实体类对象
            shop = mapper.readValue(shopStr,Shop.class);
        }catch (Exception e){
            map.put("success",false);
            map.put("errorMsg",e.getMessage());
            return map;
        }

        CommonsMultipartFile shopImg = null;
        //创建一个多部份的文件解析器
        CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        //判断request中是否有文件上传
        if (commonsMultipartResolver.isMultipart(request)) {
            //转换成多部份request
            MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
            //根据名为shopImg的key来获取上传的文件
            shopImg = (CommonsMultipartFile) multipartHttpServletRequest.getFile("shopImg");
        }

        //2、获取到的店铺信息不为空，则注册店铺
        if(shop != null){
            //从session中获取用户信息，并设置进属性中进行关联
            Person user = (Person) request.getSession().getAttribute("person");
            shop.setOwner(user);
            ShopExecution shopExecution = null;
            try {
                //判断img文件流是否为空
                if(shopImg !=null){
                    //创建ImageHolder对象，接收图片信息（图片流和图片名）
                    ImageHolder imageHolder = new ImageHolder(shopImg.getInputStream(),shopImg.getOriginalFilename());
                    shopExecution = shopService.addShop(shop,imageHolder);
                }else {
                    shopExecution = shopService.addShop(shop,null);
                }
                //如果注册后的状位0，则注册成功
                if(shopExecution.getState()== ShopStateEnum.CHECK.getState()){
                    map.put("success",true);
                }else{
                    map.put("success",false);
                    map.put("errorMsg",shopExecution.getStateInfo());
                }
            } catch (IOException e) {
                map.put("success",false);
                map.put("errorMsg",e.getMessage());
            }
            return map;
        }else{
            map.put("success",false);
            map.put("errorMsg","店铺信息不能为空！");
            return map;
        }

    }


    /**
     * 进入店铺管理界面
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/get_shop_management_info",method = RequestMethod.GET)
    @ResponseBody  //@ResponseBody注解会将这个方法的返回值转换为JSON形式的数据，返回到response中,可以抽象理解成response.getWriter.write(JSON.toJSONString(map));
    private Map<String,Object> getShopManagementInfo(HttpServletRequest request){
        Map<String,Object> map = new HashMap<String, Object>();
        long shopId = HttpServletRequestUtil.getLong(request,"shopId");
        if(shopId <= 0){
            Object currentShopObj = request.getSession().getAttribute("currentShop");
            if(currentShopObj == null){
                map.put("redirect",true);
                map.put("url","/o2o/shop_admin/shop_list");
            }else {
                Shop currentShop = (Shop) currentShopObj;
                map.put("redirect", false);
                map.put("shopId", currentShop.getShopId());
            }
        }else {
            //将shop添加到session中
            Shop currentShop = new Shop();
            currentShop.setShopId(shopId);
            request.getSession().setAttribute("currentShop", currentShop);
            map.put("redirect", false);
        }
        return map;
    }

    /**
     * 根据shopId获取店铺信息，提交
     * @param request
     * @return
     */
    @RequestMapping(value = "/get_shop_by_id", method = RequestMethod.GET)
    @ResponseBody  //@ResponseBody注解会将这个方法的返回值转换为JSON形式的数据，返回到response中,可以抽象理解成response.getWriter.write(JSON.toJSONString(map));
    public Map<String,Object> getShopById(HttpServletRequest request){
        Map<String,Object> map = new HashMap<String, Object>();
        Long shopId = HttpServletRequestUtil.getLong(request,"shopId");
        if(shopId > -1){
            try{
                Shop shop = shopService.getByShopId(shopId);
                List<Area> areaList = areaService.getAreaList();
                map.put("shop",shop);
                map.put("areaList",areaList);
                map.put("success",true);
            }catch(Exception e){
                map.put("success",false);
                map.put("errorMsg",e.getMessage());
            }
        }else{
            map.put("success",false);
            map.put("errorMsg","empty shopId");
        }
        return map;
    }


    /**
     * 注册店铺
     * @param request
     * @return
     */
    @RequestMapping(value = "/modify_shop",method = RequestMethod.POST)
    @ResponseBody  //@ResponseBody注解会将这个方法的返回值转换为JSON形式的数据，返回到response中,可以抽象理解成response.getWriter.write(JSON.toJSONString(map));
    private Map<String,Object> modifyShop(HttpServletRequest request){
        Map<String,Object> map = new HashMap<String, Object>();
        if(!VerifyCodeUtil.checkVerifyCode(request)){
            map.put("success",false);
            map.put("errorMsg","验证码错误!");
            return map;
        }
        //1、接收前台上传的店铺信息
        String shopStr = HttpServletRequestUtil.getString(request,"shopStr");
        //
        ObjectMapper mapper = new ObjectMapper();
        Shop shop = null;
        try{
            //通过ObjectMapper对象将前台上传的字符串对象转化成Shop实体类对象
            shop = mapper.readValue(shopStr,Shop.class);
        }catch (Exception e){
            map.put("success",false);
            map.put("errorMsg",e.getMessage());
            return map;
        }

        CommonsMultipartFile shopImg = null;
        //创建一个多部份的文件解析器
        CommonsMultipartResolver commonsMultipartResolver =
                new CommonsMultipartResolver(request.getSession().getServletContext());
        //判断request中是否有文件上传
        if(commonsMultipartResolver.isMultipart(request)){
            //将request转成MultipartHttpServletRequest
            MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
            //根据名为shopImg的key来获取上传的文件
            shopImg = (CommonsMultipartFile) multipartHttpServletRequest.getFile("shopImg");
        }

        //2、修改店铺信息
        if(shop != null && shop.getShopId() !=null){
            ShopExecution shopExecution = null;
            try {
                //判断img文件流是否为空
                if(shopImg == null){
                    shopExecution = shopService.modifyShop(shop,null);
                }else {
                    ImageHolder imageHolder = new ImageHolder(shopImg.getInputStream(),shopImg.getOriginalFilename());
                    shopExecution = shopService.modifyShop(shop,imageHolder);
                }

                if(shopExecution.getState()== ShopStateEnum.SUCCESS.getState()){
                    map.put("success",true);
                }else {
                    map.put("success",false);
                    map.put("errorMsg",shopExecution.getStateInfo());
                }
            } catch (IOException e) {
                map.put("success",false);
                map.put("errorMsg",e.getMessage());
            }
            return map;
        }else{
            map.put("success",false);
            map.put("errorMsg","请输入店铺id ！");
            return map;
        }

    }



}
