package com.ald.finance.web.controller;


import com.ald.finance.common.constants.JYXTConstant;
import com.ald.finance.dal.entity.User;
import com.ald.finance.common.enums.UserRoleEnum;
import com.ald.finance.common.web.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

/**
 * Created by zhangliang on 2018/5/2.
 */
@Controller
public class BaseController {

    @Autowired
    HttpSession httpSession;

    public String removeUser() {
        httpSession.removeAttribute(JYXTConstant.MANAGER_SESSION_KEY);
        return "login";
    }

    public User getUser() {
        return (User) httpSession.getAttribute(JYXTConstant.MANAGER_SESSION_KEY);
    }

    public ModelAndView setUser(User user, ModelAndView modelAndView) {
        if (user != null && !UserRoleEnum.Student.getRoleId().equals(user.getUserRole())) {
            user.setUserPwd("");
            httpSession.setAttribute(JYXTConstant.MANAGER_SESSION_KEY, user);
        }
        String url = checkUserLoginBySession();
        modelAndView.setViewName(url);
        return modelAndView;
    }

    private String checkUserLoginBySession() {
        User user = getUser();
        if (user == null)
            return "login";
        String url = (String) httpSession.getAttribute(JYXTConstant.RELOAD_PAGE_SESSION_KEY);
        if (url == null || "".equals(url))
            return "index";
        else
            return "redirect:" + url;
    }

    public String checkUserLogin() {
        return checkUserLoginBySession();
    }

    //转换分页信息
    public ModelAndView toPageView(ModelAndView modelAndView, Page page, Pageable pageable) {
        modelAndView.addObject("totalPage", page.getTotalPages() > 0 ? page.getTotalPages() : 1);
        modelAndView.addObject("size", pageable.getPageSize());
        modelAndView.addObject("page", page.getNumber() + 1);
        modelAndView.addObject("total", page.getTotalElements());
        modelAndView.addObject("item", page.getContent());
        return modelAndView;
    }

    public void checkUserRole(){
        if (getUser().getUserRole().equals(UserRoleEnum.Teacher.getRoleId())) {
            throw new BusinessException("无权限");
        }
    }

    public void init(ModelAndView modelAndView){
        modelAndView.addObject("isApproval",true);
        if (getUser().getUserRole().equals(UserRoleEnum.Teacher.getRoleId())) {
            modelAndView.addObject("isApproval",false);
        }
    }
}
