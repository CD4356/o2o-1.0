package com.cd.o2o.entity;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 本地账号
 */
public class LocalAccount {

    //本地账号
    private Long id;
    //用户名
    private String username;
    //密码
    private String password;
    //创建时间
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private Date createTime;
    //最近更新时间
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private Date lastEditTime;
    //关联的person对象
    private Person person;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }
}
