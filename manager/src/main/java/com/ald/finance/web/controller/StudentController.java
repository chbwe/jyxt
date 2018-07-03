package com.ald.finance.web.controller;


import com.ald.finance.dal.entity.User;
import com.ald.finance.service.*;
import com.ald.finance.web.vm.StudentSearchVm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by liang3.zhang on 2018/5/3.
 */
@Controller
@RequestMapping("/student")
public class StudentController extends BaseController {

    @Autowired
    UserService userService;

    /**
     * 查询学生列表
     *
     * @param modelAndView
     * @param vm
     * @return
     */
    @RequestMapping(value = "/list")
    public ModelAndView list(ModelAndView modelAndView, StudentSearchVm vm) {
        modelAndView.addObject("vm", vm);
        Page<User> records = userService.findStudentList(vm.getName(),vm.getPageable());
        modelAndView.setViewName("student");
        return toPageView(modelAndView, records,vm.getPageable());
    }
}
