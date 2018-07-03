package com.ald.finance.service.query;

/**
 * Created by liang3.zhang on 2018/6/1.
 */
public class UserQuery {

    private Integer userRole;

    private String name;

    private Integer status;

    private Long userId;

    public Integer getUserRole() {
        return userRole;
    }

    public void setUserRole(Integer userRole) {
        this.userRole = userRole;
    }

    public UserQuery userRole(Integer userRole) {
        this.userRole = userRole;
        return this;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UserQuery name(String name) {
        this.name = name;
        return this;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public UserQuery status(Integer status) {
        this.status = status;
        return this;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public UserQuery userId(Long userId) {
        this.userId = userId;
        return this;
    }
}
