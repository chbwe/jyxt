package com.ald.finance.common.enums;

/**
 * Created by zhangliang on 2018/5/2.
 */
public enum UserRoleEnum {
    Admin(0, "管理员"), Teacher(1, "老师"), Student(2, "学生");
    private String roleName;
    private Integer roleId;

    UserRoleEnum(Integer roleId, String roleName) {
        this.roleName = roleName;
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public Integer getRoleId() {
        return roleId;
    }
}
