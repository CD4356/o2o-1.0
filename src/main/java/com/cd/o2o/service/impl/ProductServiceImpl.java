package com.cd.o2o.service.impl;

import com.cd.o2o.dao.ProductDao;
import com.cd.o2o.dao.ProductImgDao;
import com.cd.o2o.dto.ImageHolder;
import com.cd.o2o.dto.ProductExecution;
import com.cd.o2o.entity.Product;
import com.cd.o2o.entity.ProductImg;
import com.cd.o2o.service.ProductService;
import com.cd.o2o.util.ImageUtil;
import com.cd.o2o.util.PageCalculator;
import com.cd.o2o.util.PathUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service("productService")
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductDao productDao;
    @Autowired
    private ProductImgDao productImgDao;


    /**
     * 根据查询条件获取商品列表
     *
     * @param productCondition
     * @param pageIndex
     * @param pageSize
     * @return
     */
    public ProductExecution getProductList(Product productCondition, int pageIndex, int pageSize) {
        //将页码转换成数据库的行码，
        int rowIndex = PageCalculator.calculatorRowIndex(pageIndex,pageSize);
        List<Product> productList = productDao.queryProductList(productCondition,rowIndex,pageSize);
        //基于同样的查询条件下，返回该查询条件下商品的总数
        int count = productDao.queryProductCount(productCondition);
        ProductExecution productExecution = new ProductExecution();
        productExecution.setProductList(productList);
        productExecution.setCount(count);
        return productExecution;
    }


    /**
     * 获取指定商品信息
     *
     * @param productId
     * @return
     */
    public Product getProductById(long productId) {
        return productDao.queryProductById(productId);
    }


    /**
     * 更新商品
     *
     * @param product
     * @param thumbnail
     * @param productImgHolderList
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED)
    /*
     1.若缩略图参数有值，则处理缩略图，若原先存在缩略图,则先删除再添加新图，之后获取缩略图相对路径并赋值给product
	 2.若商品详情图列表参数有值，对商品详情图片列表进行同样的操作
	 3.将product_img下面的该商品原先的商品详情图记录全部清除
	 4.更新product_img以及product的信息
     */
    public int modifyProduct(Product product, ImageHolder thumbnail, List<ImageHolder> productImgHolderList) {
        int effectedNum = 0;
        //控制判断
        if(product !=null && product.getShop() != null && product.getShop().getShopId() != null){
            //给商品设置上默认属性
            product.setLastEditTime(new Date());
            //如果传入的商品缩略图不为空，且原有缩略图不为空，则删除原有缩略图，再添加
            if(thumbnail !=null){
                //根据商品id获取商品信息
                Product tempProduct = productDao.queryProductById(product.getProductId());
                //判断原有商品缩略图是否为空
                if(tempProduct.getImgAddress() !=null){
                    //删除原有的图片
                    ImageUtil.deleteFileOrPath(tempProduct.getImgAddress());
                }
                //添加新的商品缩略图
                addThumbnail(product,thumbnail);
            }
            //如果有新存入的商品详情图，则将原有的所有商品详情图删除，然后添加传入的详情图
            if(productImgHolderList !=null && productImgHolderList.size()>0){
                deleteProductImgList(product.getProductId());
                batchAddImageList(product,productImgHolderList);
            }
            try{
                effectedNum = productDao.updateProduct(product);
                if(effectedNum <=0){
                    throw new RuntimeException("更新商品信息失败!");
                }
            }catch (Exception e){
                throw new RuntimeException("更新商品信息失败!" + e.getMessage());
            }
        }
        return effectedNum;
    }


    /**
     * 删除某个商品下的所有详情图（磁盘中存储的物理图片 和 数据库中的图片信息）
     *
     * @param productId
     */
    private void deleteProductImgList(long productId){
        //根据商品id获取所有商品详情图
        List<ProductImg> productImgList = productImgDao.queryProductImgListByProductId(productId);
        //删除物理详情图（即存储在磁盘中的商品详情图）
        for(ProductImg productImg : productImgList){
            ImageUtil.deleteFileOrPath(productImg.getProductDetailImg());
        }
        //删除数据库里原有详情图的信息
        productImgDao.deleteProductImgByProductId(productId);

    }


    /**
     * 添加商品
     *
     * @param product 商品信息
     * @param thumbnail 商品缩略图（包含图片流和图片名）
     * @param productImgHolderList 商品详情图集合 （包含图片流和图片名）
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED)
    /**
     * 1、生成缩略图，获取缩略图相对路径并赋值给product
     * 2、向product表中写入商品信息，获取productId
     * 3、结合productId批量处理商品详情图
     * 4、将商品详情图列表批量插入product_img表中
     */
    public int addProduct(Product product, ImageHolder thumbnail, List<ImageHolder> productImgHolderList) {
        int effectNum = 0;
        if(product !=null && product.getShop() !=null && product.getShop().getShopId() !=null){
            //设置商品默认属性值
            product.setCreateTime(new Date());
            product.setLastEditTime(new Date());
            //默认为上架状态
            product.setEnableStatus(1);
            if(thumbnail !=null){
                //1、将生成的缩略图存储到指定目录中，并将缩略图相对路径并赋值给product
                addThumbnail(product,thumbnail);
            }
            try{
                //2、添加商品信息
                effectNum = productDao.insertProduct(product);
                if(effectNum <= 0){
                    throw new RuntimeException("添加商品失败!");
                }
            }catch (Exception e){
                throw new RuntimeException("添加商品失败："+e.getMessage());
            }
            //如果商品详情图不为空，则添加
            if(productImgHolderList !=null && productImgHolderList.size() >0){
                //批量添加商品详情图缩略图
                batchAddImageList(product,productImgHolderList);
            }
        }else {
            throw new RuntimeException("商品信息不能为空!");
        }
        return effectNum;
    }


    /**
     * 添加商品缩略图
     *
     * @param product
     * @param thumbnail
     */
    private void addThumbnail(Product product, ImageHolder thumbnail) {
        //获取图片存储相对路径/目标路径
        String dest = PathUtil.getShopImagePath(product.getShop().getShopId());
        //生成缩略图,并返回图片存储的相对路径地址
        String thumbnailAddress = ImageUtil.generateThumbnail(thumbnail,dest);
        //设置商品图片存储地址
        product.setImgAddress(thumbnailAddress);
    }


    /**
     * 批量添加商品详情图缩略图
     *
     * @param product 商品信息
     * @param productImgHolderList 商品详情图
     */
    private void batchAddImageList(Product product, List<ImageHolder> productImgHolderList) {
        //获取图片存储相对路径/目标路径，这里直接存放到相应店铺文件夹底下
        String dest = PathUtil.getShopImagePath(product.getShop().getShopId());
        List<ProductImg> productImgList = new ArrayList<ProductImg>();
        //遍历图片集合依次进行处理，并保存到ProductImg实体类中
        for(ImageHolder productImgHolder : productImgHolderList){
            //生成商品详情图的缩略图，并返回图片存储的相对路径地址
            String imgAddr = ImageUtil.generateNormalThumbnail(productImgHolder,dest);
            //设置商品详情图的属性
            ProductImg productImg = new ProductImg();
            productImg.setProductDetailImg(imgAddr);
            productImg.setCreateTime(new Date());
            productImg.setProductId(product.getProductId());
            productImgList.add(productImg);
        }
        //如果确实有图片需要添加，就执行批量添加操作
        if(productImgList.size() >0){
            try{
                int effectNum = productImgDao.batchInsertProductImg(productImgList);
                if(effectNum <=0){
                    throw new RuntimeException("添加图片详情图失败:");
                }
            }catch (Exception e){
                throw new RuntimeException("添加图片详情图失败: " + e.getMessage());
            }
        }
    }

}
