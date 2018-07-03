package com.ald.finance.web.vm;


import org.hibernate.validator.constraints.NotBlank;

/**
 * Created by liang3.zhang on 2018/5/3.
 */
public class ChangePwdVm {
    
    @NotBlank(message = "密码不能为空")
    private String password;
    
    @NotBlank(message = "新密码不能为空")
    private String newPassword;
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public String getNewPassword() {
        return newPassword;
    }
    
    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
