package com.cd.o2o.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

/**
 * 商品
 */
public class Product {

    //商品id
    private Long productId;
    //商品名
    private String productName;
    //商品描述
    private String productDesc;
    //商品缩略图
    private String imgAddress;
    //原价
    private Double normalPrice;
    //折扣价
    private Double promotionPrice;
    //权重
    private Integer priority;
    //状态 0:下架 1:在前端展示系统展示
    private Integer enableStatus;
    //创建时间
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private Date createTime;
    //修改时间
    /* 使用@JsonFormat注解格式化日期，才能按指定格式在前端页面，具体参考：https://www.jb51.net/article/144235.htm */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone="GMT+8")
    private Date lastEditTime;

    // 店铺实体类，标明商品属于哪个店铺
    private Shop shop;
    // 商品类别，一件商品仅属于一个商品类别
    private ProductCategory productCategory;

    // 图片详情图列表，跟商品是多对一的关系
    private List<ProductImg> productImgList;



    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductDesc() {
        return productDesc;
    }

    public void setProductDesc(String productDesc) {
        this.productDesc = productDesc;
    }

    public String getImgAddress() {
        return imgAddress;
    }

    public void setImgAddress(String imgAddress) {
        this.imgAddress = imgAddress;
    }

    public Double getNormalPrice() {
        return normalPrice;
    }

    public void setNormalPrice(Double normalPrice) {
        this.normalPrice = normalPrice;
    }

    public Double getPromotionPrice() {
        return promotionPrice;
    }

    public void setPromotionPrice(Double promotionPrice) {
        this.promotionPrice = promotionPrice;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public Integer getEnableStatus() {
        return enableStatus;
    }

    public void setEnableStatus(Integer enableStatus) {
        this.enableStatus = enableStatus;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getLastEditTime() {
        return lastEditTime;
    }

    public void setLastEditTime(Date lastEditTime) {
        this.lastEditTime = lastEditTime;
    }

    public List<ProductImg> getProductImgList() {
        return productImgList;
    }

    public void setProductImgList(List<ProductImg> productImgList) {
        this.productImgList = productImgList;
    }

    public ProductCategory getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(ProductCategory productCategory) {
        this.productCategory = productCategory;
    }

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }
}
