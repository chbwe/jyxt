package com.ald.finance.web.controller;

import com.ald.finance.dal.entity.User;
import com.ald.finance.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by zhangliang on 2018/5/2.
 */
@Controller
@RequestMapping("")
public class IndexController extends BaseController {
    
    @Autowired
    UserService userService;
    
    /**
     * 首页
     *
     * @param
     * @return
     */
    @RequestMapping(value = "/")
    public String index() {
        return checkUserLogin();
    }
    
    /**
     * 登录
     *
     * @param modelAndView
     * @param username
     * @param password
     * @return
     */
    @RequestMapping(value = "/login")
    public ModelAndView login(ModelAndView modelAndView, @RequestParam("username") String username,
        @Param("password") String password) {
        modelAndView.setViewName("login");
        if (StringUtils.isEmpty(username)) {
            modelAndView.addObject("message", "用户名不能为空");
            return modelAndView;
        }
        if (StringUtils.isEmpty(password)) {
            modelAndView.addObject("message", "密码不能为空");
            return modelAndView;
        }
        try {
            User user = userService.login(username, password);
            return setUser(user, modelAndView);
        } catch (Exception e) {
            modelAndView.addObject("message", e.getMessage());
            return modelAndView;
        }
    }
    
    /**
     * 退出登录
     *
     * @param
     * @return
     */
    @RequestMapping(value = "/logout")
    public String logout() {
        return removeUser();
    }
}
