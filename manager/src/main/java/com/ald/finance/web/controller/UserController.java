package com.ald.finance.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by zhangliang on 2018/5/2.
 */
@Controller
@RequestMapping("/user")
public class UserController extends BaseController {

    /**
     * 获取菜单
     * 
     * @param modelAndView
     * @return
     */
    @RequestMapping(value = "/navigation")
    public String navigation(ModelAndView modelAndView) {
        return "navigation";
    }

    /**
     * 获取用户信息
     * @param modelAndView
     * @return
     */
    @RequestMapping(value = "/header")
    public ModelAndView header(ModelAndView modelAndView) {
        modelAndView.setViewName("header");
        modelAndView.addObject("user", getUser());
        return modelAndView;
    }
}
