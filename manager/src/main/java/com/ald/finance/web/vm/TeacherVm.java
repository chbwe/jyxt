package com.ald.finance.web.vm;

import org.hibernate.validator.constraints.NotBlank;

/**
 * Created by liang3.zhang on 2018/5/3.
 */
public class TeacherVm {
    
    private Long id;
    
    @NotBlank(message = "教师姓名不能为空")
    private String userNickname;
    
    private String userSchool;
    
    @NotBlank(message = "用户手机号码不能为空")
    private String userMobile;
    private String userContent;
    private Double userPrice;
    private Double userPrice2;
    private String userImg;
    
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
    
    public String getUserSchool() {
        return userSchool;
    }
    
    public void setUserSchool(String userSchool) {
        this.userSchool = userSchool;
    }
    
    public String getUserMobile() {
        return userMobile;
    }
    
    public void setUserMobile(String userMobile) {
        this.userMobile = userMobile;
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
    
    public Double getUserPrice2() {
        return userPrice2;
    }
    
    public void setUserPrice2(Double userPrice2) {
        this.userPrice2 = userPrice2;
    }
    
    public String getUserImg() {
        return userImg;
    }
    
    public void setUserImg(String userImg) {
        this.userImg = userImg;
    }
}
