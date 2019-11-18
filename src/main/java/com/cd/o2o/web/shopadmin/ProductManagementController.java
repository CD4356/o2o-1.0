package com.cd.o2o.web.shopadmin;

import com.cd.o2o.dto.ImageHolder;
import com.cd.o2o.dto.ProductExecution;
import com.cd.o2o.entity.Product;
import com.cd.o2o.entity.ProductCategory;
import com.cd.o2o.entity.Shop;
import com.cd.o2o.service.ProductCategoryService;
import com.cd.o2o.service.ProductService;
import com.cd.o2o.util.HttpServletRequestUtil;
import com.cd.o2o.util.VerifyCodeUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("shop_admin")
public class ProductManagementController {

    @Autowired
    private ProductService productService;
    @Autowired
    private ProductCategoryService productCategoryService;

    //支持商品详情图上传的最大数量
    private static final int MAX_IMAGE_COUNT = 6;


    /**
     * 获取店铺列表
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/get_product_list", method = RequestMethod.GET)
    @ResponseBody  //@ResponseBody注解会将这个方法的返回值转换为JSON形式的数据，返回到response中,可以抽象理解成response.getWriter.write(JSON.toJSONString(map));
    private Map<String,Object> getProductList(HttpServletRequest request){
        Map<String,Object> map = new HashMap<String, Object>();
        // 获取前台传过来的页码
        int pageIndex = HttpServletRequestUtil.getInt(request, "pageIndex");
        // 获取前台传过来的每页要求返回的商品数上限
        int pageSize = HttpServletRequestUtil.getInt(request, "pageSize");
        //从session中获取当前操作店铺的信息
        Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
        //控制判断
        if((pageIndex >-1) && (pageSize >-1) && (currentShop !=null) && (currentShop.getShopId() !=null)){
            //获取传入的需要检索的查询条件，组合查询条件筛选出店铺下的商品列表
            Long productCategoryId = HttpServletRequestUtil.getLong(request,"productCategoryId");
            String productName = HttpServletRequestUtil.getString(request,"productName");
            //组合查询条件
            Product productCondition = compactProductCondition(currentShop.getShopId(),productCategoryId,productName);
            //传入查绚条件和分页信息，返回相应的商品列表和总数
            ProductExecution productExecution = productService.getProductList(productCondition,pageIndex,pageSize);
            map.put("success",true);
            map.put("productList",productExecution.getProductList());
            map.put("count",productExecution.getCount());
        }else {
            map.put("success",false);
            map.put("errorMsg","empty pageIndex or pageSize or shopId");
        }
        return map;
    }


    /**
     * 组合查询条件
     *
     * @param shopId
     * @param productCategoryId
     * @param productName
     * @return
     */
    private Product compactProductCondition(Long shopId, Long productCategoryId, String productName) {
        Product productCondition = new Product();
        //店铺id是必须添加的
        Shop shop = new Shop();
        shop.setShopId(shopId);
        productCondition.setShop(shop);
        //若有商品类别查绚要求，则添加进去
        if(productCategoryId != -1L){
            ProductCategory productCategory = new ProductCategory();
            productCategory.setProductCategoryId(productCategoryId);
            productCondition.setProductCategory(productCategory);
        }
        //若有商品名模糊查绚，则添加进去
        if(productName !=null){
            productCondition.setProductName(productName);
        }

        return productCondition;
    }


    /**
     * 通过商品id获取商品信息和商品分类信息
     *
     * @param productId
     * @return
     */
    @RequestMapping(value = "/get_product_by_id", method = RequestMethod.GET)
    @ResponseBody  //@ResponseBody注解会将这个方法的返回值转换为JSON形式的数据，返回到response中,可以抽象理解成response.getWriter.write(JSON.toJSONString(map));
    private Map<String,Object> getProductById(@RequestParam Long productId){
        Map<String,Object> map = new HashMap<String, Object>();
        //非空判断
        if(productId > -1){
            //获取商品信息
            Product product = productService.getProductById(productId);
            //获取该商品所属店铺的商品分类列表
            List<ProductCategory> productCategoryList = productCategoryService.getProductCategoryList(product.getShop().getShopId());
            map.put("product",product);
            map.put("productCategoryList",productCategoryList);
            map.put("success",true);
        }else {
            map.put("errorMsg","传入的productId为空!");
            map.put("success",false);
        }
        return map;
    }


    /**
     * 商品编辑
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/modify_product", method = RequestMethod.POST)
    @ResponseBody  //@ResponseBody注解会将这个方法的返回值转换为JSON形式的数据，返回到response中,可以抽象理解成response.getWriter.write(JSON.toJSONString(map));
    private Map<String,Object> modifyProduct(HttpServletRequest request){
        Map<String,Object> map = new HashMap<String, Object>();
        //首先需要判断商品状态（商品状态 0:商品下架 1:在前端展示界面展示）
        //如果商品状态为1，需要进行验证码校验；状态为0，不需要进行验证码校验
        boolean statusChange = HttpServletRequestUtil.getBoolean(request,"statusChange");
        //验证码校验
        if(!statusChange && !VerifyCodeUtil.checkVerifyCode(request)){
            map.put("success",false);
            map.put("errorMsg","验证码错误！");
            return map;
        }
        //接收前端提交过来的json字符串数据，包括（商品信息、商品缩略图、商品详情图），并转化成Product实体类
        String productStr = HttpServletRequestUtil.getString(request,"productStr");
        ObjectMapper objectMapper = new ObjectMapper();
        Product product = null;
        try{
            product = objectMapper.readValue(productStr,Product.class);
        }catch (Exception e){
            map.put("success",false);
            map.put("errorMsg",e.getMessage());
            return map;
        }

        ImageHolder thumbnail = null;
        List<ImageHolder> productImgHolderList = new ArrayList<ImageHolder>();

        //创建文件解析器（目的是调用该对象的isMultipart方法来检测request请求中是否有上传文件）
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        try{
            //判断request中是否有上传文件
            if(multipartResolver.isMultipart(request)){
                thumbnail = handlerImage((MultipartHttpServletRequest) request, thumbnail, productImgHolderList);
            }
        }catch (Exception e){
            map.put("success",false);
            map.put("errorMsg",e.getMessage());
            return map;

        }

        //如果商品信息不为空，则进行修改 （注意、商品缩略图和商品详情图是可以为空的）
        if(product !=null){
            //从session中获取当前操作店铺的信息，并设置进Product对象里
            Shop shop = (Shop) request.getSession().getAttribute("currentShop");
            product.setShop(shop);
            // 开始进行商品信息变更操作
            int effectedNum = productService.modifyProduct(product,thumbnail,productImgHolderList);
            if(effectedNum <= 0){
                map.put("success",false);
                map.put("errorMsg","商品信息更新失败!!");
            }else {
                map.put("success",true);
            }
        }else {
            map.put("success",false);
            map.put("errorMsg","商品信息为空，请重新输入商品信息!");
        }

        return map;
    }


    /**
     * 图片处理（对addProduct方法 和 modifyProduct方法中相同的代码块进行重构，减少代码）
     *
     * @param request
     * @param thumbnail
     * @param productImgHolderList
     * @return
     * @throws IOException
     */
    private ImageHolder handlerImage(MultipartHttpServletRequest request, ImageHolder thumbnail, List<ImageHolder> productImgHolderList) throws IOException {
        //将request强转成MultipartHttpServletRequest
        MultipartHttpServletRequest multipartRequest = request;

        //取出缩略图，并构建ImageHolder对象
        CommonsMultipartFile thumbnailFile = (CommonsMultipartFile) multipartRequest.getFile("thumbnail");
        if (thumbnailFile != null) {
            thumbnail = new ImageHolder(thumbnailFile.getInputStream(), thumbnailFile.getOriginalFilename());
        }

        //取出详情图列表，并构建List<ImageHolder>对象，（最多支持上传6张图）
        for (int i = 0; i < MAX_IMAGE_COUNT; i++) {
            //从详情图列表中获取单个详情图片
            CommonsMultipartFile productImgFile = (CommonsMultipartFile) multipartRequest.getFile("productImg" + i);
            //如果获取到的第i个详情图文件流不为空，则添加进详情图列表中
            if (productImgFile != null) {
                ImageHolder productImg = new ImageHolder(productImgFile.getInputStream(), productImgFile.getOriginalFilename());
                productImgHolderList.add(productImg);
            } else {
                //如果获取到的第i个详情图文件流为空，则退出循环
                break;
            }
        }

        return thumbnail;
    }



    /**
     * 添加商品
     * @param request
     * @return
     */
    @RequestMapping("/add_product")
    @ResponseBody  //@ResponseBody注解会将这个方法的返回值转换为JSON形式的数据，返回到response中,可以抽象理解成response.getWriter.write(JSON.toJSONString(map));
    private Map<String,Object> addProduct(HttpServletRequest request){
        Map<String,Object> map = new HashMap<String, Object>();
        //验证码校验
        if(!VerifyCodeUtil.checkVerifyCode(request)){
            map.put("success",false);
            map.put("errorMsg","验证码错误!");
            return map;
        }

        Product product = null;
        try{
            //接收前端提交过来的json字符串类型的商品信息数据，包括（商品信息、商品缩略图、商品详情图），并转化成Product实体类
            String productStr = HttpServletRequestUtil.getString(request,"productStr");
            ObjectMapper objectMapper = new ObjectMapper();
            product = objectMapper.readValue(productStr,Product.class);
        }catch (Exception e){
            map.put("success",false);
            map.put("errorMsg",e.getMessage());
            return map;
        }

        ImageHolder thumbnail = null;
        List<ImageHolder> productImgHolderList = new ArrayList<ImageHolder>();

        //创建文件解析器（目的是调用该对象的isMultipart方法来检测request请求中是否有上传文件）
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        try{
            //判断request中是否有上传文件流
            if(multipartResolver.isMultipart(request)){
                thumbnail = handlerImage((MultipartHttpServletRequest) request, thumbnail, productImgHolderList);
            }else {
                map.put("success",false);
                map.put("errorMsg","上传图片不能为空!");
                return map;
            }
        }catch (Exception e){
            map.put("success",false);
            map.put("errorMsg",e.getMessage());
            return map;
        }

        //如果Product信息、缩略图、商品详情图列表不为空，则进行商品添加操作
        if(product !=null && thumbnail !=null && productImgHolderList !=null){
            try{
                //从session中获取当前店铺的id，并赋值给product，减少对前端数据的依赖
                Shop shop = (Shop) request.getSession().getAttribute("currentShop");
                product.setShop(shop);
                //执行添加操作
                int effectNum = productService.addProduct(product,thumbnail,productImgHolderList);
                if(effectNum <= 0){
                    map.put("success",false);
                    map.put("errorMsg","商品添加失败!");
                    return map;
                }else {
                    map.put("success",true);
                }
            }catch (Exception e){
                map.put("success",false);
                map.put("errorMsg",e.getMessage());
                return map;
            }
        }
        return map;
    }


}
