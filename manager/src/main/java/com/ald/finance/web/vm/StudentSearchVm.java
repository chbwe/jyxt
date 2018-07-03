package com.ald.finance.web.vm;

import com.ald.finance.common.enums.UserRoleEnum;

/**
 * Created by zhangliang on 2018/5/10.
 */
public class StudentSearchVm extends PageVm {

    private String name;

    private UserRoleEnum roleEnum;

    public UserRoleEnum getRoleEnum() {
        return roleEnum;
    }

    public void setRoleEnum(UserRoleEnum roleEnum) {
        this.roleEnum = roleEnum;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
