package com.ald.finance.web.reset;


import com.ald.finance.dal.entity.User;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpSession;

/**
 * Created by liang3.zhang on 2018/5/6.
 */
public class BaseController {

    @Autowired
    HttpSession session;

    User getUser() {
        return (User) session.getAttribute("user.session.key");
    }

    void remove() {
        session.removeAttribute("user.session.key");
    }

    void setUser(User user) {
        user.setUserPwd("");
        session.setAttribute("user.session.key", user);
    }
}
