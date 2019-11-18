package com.cd.o2o.entity;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 用户信息
 */
public class Person {

    //用户id
    private Long userId;
    //用户名
    private String name;
    //密码
    private String pwd;
    //头像地址
    private String profileImg;
    //邮箱
    private String email;
    //性别
    private String gender;
    //启用状态
    private Integer enableStatus;
    //身份标识  1.顾客 2.店家 3.超级管理员
    private Integer personType;
    //创建时间
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private Date createTime;
    //修改时间
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private Date lastEditTime;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getProfileImg() {
        return profileImg;
    }

    public void setProfileImg(String profileImg) {
        this.profileImg = profileImg;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Integer getEnableStatus() {
        return enableStatus;
    }

    public void setEnableStatus(Integer enableStatus) {
        this.enableStatus = enableStatus;
    }

    public Integer getPersonType() {
        return personType;
    }

    public void setPersonType(Integer personType) {
        this.personType = personType;
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
