package com.ald.finance.web.filter;


import com.ald.finance.common.constants.JYXTConstant;
import com.ald.finance.common.enums.UserRoleEnum;
import com.ald.finance.dal.entity.User;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by liang3.zhang on 2017/4/25.
 */
@WebFilter(filterName = "userAuthenticateFilter", urlPatterns = {"/user/*", "/student/*", "/teacher/*", "/holiday/*",
        "/class/*", "/appointment/*"})
public class UserAuthenticateFilter implements Filter {

    // 默认登陆页
    private final static String LOGIN_URL = "/logout";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        String url = httpServletRequest.getRequestURI();
        // 判断用户是否已登录
        HttpSession session = httpServletRequest.getSession();
        User user = (User) session.getAttribute(JYXTConstant.MANAGER_SESSION_KEY);
        if (user == null || user.getUserRole().equals(UserRoleEnum.Student.getRoleId())) {
            // 直接跳转到URL页面
            session.setAttribute(JYXTConstant.RELOAD_PAGE_SESSION_KEY, url);
            httpServletResponse.setDateHeader("expires", 0);
            httpServletResponse.sendRedirect(LOGIN_URL);
            return;
        }
        chain.doFilter(request, response);
        return;
    }
}
