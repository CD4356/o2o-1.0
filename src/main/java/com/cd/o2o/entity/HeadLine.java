package com.cd.o2o.entity;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 头条
 */
public class HeadLine {

    //头条id
    private Long lineId;
    //名称
    private String lineName;
    //链接
    private String lineLink;
    //图片
    private String lineImg;
    //权重
    private Integer priority;
    //启用状态  0:不可用 1:可用
    private Integer enableStatus;
    //创建时间
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private Date createTime;
    //最近修改时间
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private Date lastEditTime;

    public Long getLineId() {
        return lineId;
    }

    public void setLineId(Long lineId) {
        this.lineId = lineId;
    }

    public String getLineName() {
        return lineName;
    }

    public void setLineName(String lineName) {
        this.lineName = lineName;
    }

    public String getLineLink() {
        return lineLink;
    }

    public void setLineLink(String lineLink) {
        this.lineLink = lineLink;
    }

    public String getLineImg() {
        return lineImg;
    }

    public void setLineImg(String lineImg) {
        this.lineImg = lineImg;
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
}
