package com.ald.finance.web.controller;


import com.ald.finance.common.enums.UserRoleEnum;
import com.ald.finance.dal.entity.User;
import com.ald.finance.service.StudentBuyRecordService;
import com.ald.finance.service.dto.StudentBuyRecordDTO;
import com.ald.finance.web.vm.PageVm;
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
public class StudentBuyRecordController extends BaseController {

    @Autowired
    StudentBuyRecordService studentBuyRecordService;


    /**
     * 学生购买记录
     *
     * @param modelAndView
     * @param vm
     * @return
     */
    @RequestMapping(value = "/lmember")
    public ModelAndView lmember(ModelAndView modelAndView, PageVm vm) {
        User user = getUser();
        Long teacherId = null;
        if (user.getUserRole().equals(UserRoleEnum.Teacher.getRoleId())) {
            teacherId = user.getId();
        }
        Page<StudentBuyRecordDTO> records = studentBuyRecordService.find(teacherId, vm.getPageable());
        modelAndView.setViewName("studentLmember");
        return toPageView(modelAndView, records,vm.getPageable());
    }
}
