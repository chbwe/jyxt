package com.ald.finance.web.reset.vm;

import org.hibernate.validator.constraints.NotBlank;

/**
 * Created by liang3.zhang on 2018/5/8.
 */
public class RegisterVm {
    @NotBlank(message = "手机号不能为空")
    private String userMobile ;

    @NotBlank(message = "邮箱不能为空")
    private String userEmail;

    @NotBlank(message = "密码不能为空")
    private String password ;

    @NotBlank(message = "验证码不能为空")
    private String vercode ;

    public String getUserMobile() {
        return userMobile;
    }

    public void setUserMobile(String userMobile) {
        this.userMobile = userMobile;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getVercode() {
        return vercode;
    }

    public void setVercode(String vercode) {
        this.vercode = vercode;
    }
}
