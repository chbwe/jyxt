package com.ald.finance.dal.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "User")
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String userNickname;
    private String userMobile;
    private String userPwd;
    private Integer userRole;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private String userImg;
    private String userContent;
    private Double userPrice;
    private Double userPrice2;
    private String userSchool;
    private Integer status;
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getUserNickname() {
        return userNickname;
    }
    
    public void setUserNickname(String userNickname) {
        this.userNickname = userNickname;
    }
    
    public String getUserMobile() {
        return userMobile;
    }
    
    public void setUserMobile(String userMobile) {
        this.userMobile = userMobile;
    }
    
    public String getUserPwd() {
        return userPwd;
    }
    
    public void setUserPwd(String userPwd) {
        this.userPwd = userPwd;
    }
    
    public Integer getUserRole() {
        return userRole;
    }
    
    public void setUserRole(Integer userRole) {
        this.userRole = userRole;
    }
    
    public LocalDateTime getCreateTime() {
        return createTime;
    }
    
    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }
    
    public LocalDateTime getUpdateTime() {
        return updateTime;
    }
    
    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }
    
    public String getUserImg() {
        return userImg;
    }
    
    public void setUserImg(String userImg) {
        this.userImg = userImg;
    }
    
    public String getUserContent() {
        return userContent;
    }
    
    public void setUserContent(String userContent) {
        this.userContent = userContent;
    }
    
    public Double getUserPrice() {
        return userPrice;
    }
    
    public void setUserPrice(Double userPrice) {
        this.userPrice = userPrice;
    }
    
    public String getUserSchool() {
        return userSchool;
    }
    
    public void setUserSchool(String userSchool) {
        this.userSchool = userSchool;
    }
    
    public Integer getStatus() {
        return status;
    }
    
    public void setStatus(Integer status) {
        this.status = status;
    }

    public Double getUserPrice2() {
        return userPrice2;
    }

    public void setUserPrice2(Double userPrice2) {
        this.userPrice2 = userPrice2;
    }
}
