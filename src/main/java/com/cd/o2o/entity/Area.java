package com.cd.o2o.entity;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 区域
 */
public class Area {
    /**
     * 为什么属性使用应用类型而不是基本类型？
     * 基本类型为空时,会赋一个默认值,而引用类型不会,为空就是null,不会赋默认值
     */

    //区域id(自增)
    private Integer areaId;
    //名称
    private String areaName;
    //权重
    private Integer priority;
    //创建时间
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private Date createTime;
    //更新时间
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private Date lastEditTime;

    public Integer getAreaId() {
        return areaId;
    }

    public void setAreaId(Integer areaId) {
        this.areaId = areaId;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
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
