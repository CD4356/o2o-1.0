package com.cd.o2o.dto;

import com.cd.o2o.entity.Product;

import java.util.List;

public class ProductExecution {

    //操作的商品（增删改商品的时候用）
    private Product product;

    //获取商品列表（查询商品列表的时候用）
    private List<Product> productList;

    //商品数量
    private int count;

    //状态
    private int status;

    //状态标识
    private String statusInfo;

    public ProductExecution() {
    }

    //商品操作失败时使用的构造器
    public ProductExecution(int status, String statusInfo) {
        this.status = status;
        this.statusInfo = statusInfo;
    }

    //商品操作成功时使用的构造器
    public ProductExecution(Product product, int status, String statusInfo) {
        this.product = product;
        this.status = status;
        this.statusInfo = statusInfo;
    }

    //商品列表操作成功时使用的构造器
    public ProductExecution(List<Product> productList, int status, String statusInfo) {
        this.productList = productList;
        this.status = status;
        this.statusInfo = statusInfo;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getStatusInfo() {
        return statusInfo;
    }

    public void setStatusInfo(String statusInfo) {
        this.statusInfo = statusInfo;
    }
}
