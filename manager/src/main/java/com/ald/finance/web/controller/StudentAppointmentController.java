package com.ald.finance.web.controller;

import com.ald.finance.common.enums.UserRoleEnum;
import com.ald.finance.common.web.BusinessException;
import com.ald.finance.common.web.ResponseModel;
import com.ald.finance.common.web.ResponseModels;
import com.ald.finance.dal.entity.User;
import com.ald.finance.service.StudentAppointmentService;
import com.ald.finance.service.dto.StudentAppointmentDTO;
import com.ald.finance.service.query.AppointmentQuery;
import com.ald.finance.web.vm.PageVm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by zhangliang on 2018/5/2.
 */
@Controller
@RequestMapping("/appointment")
public class StudentAppointmentController extends BaseController {
    
    @Autowired
    StudentAppointmentService appointmentService;
    
    /**
     * 总预约用户（管理员权限查看）
     * 
     * @param modelAndView
     * @return
     */
    @RequestMapping(value = "/list")
    public ModelAndView list(ModelAndView modelAndView, PageVm vm) {
        modelAndView.setViewName("appointment");
        modelAndView.addObject("title", "appointment.all");
        modelAndView.addObject("url","/appointment/list");
        return loadList(modelAndView, vm, new AppointmentQuery());
    }
    
    /**
     * 获取我的失约用户
     * 
     * @param modelAndView
     * @return
     */
    @RequestMapping(value = "/anone")
    public ModelAndView progress(ModelAndView modelAndView, PageVm vm) {
        modelAndView.setViewName("appointment");
        modelAndView.addObject("title", "appointment.anone");
        modelAndView.addObject("url","/appointment/anone");
        return loadList(modelAndView, vm, new AppointmentQuery().status(2));
    }
    
    /**
     * 获取我的预约
     * 
     * @param modelAndView
     * @return
     */
    @RequestMapping(value = "/my")
    public ModelAndView my(ModelAndView modelAndView, PageVm vm) {
        modelAndView.setViewName("appointment");
        modelAndView.addObject("title", "appointment.my");
        modelAndView.addObject("url","/appointment/my");
        return loadList(modelAndView, vm, new AppointmentQuery());
    }
    
    /**
     * 根据条件查询列表
     * 
     * @param modelAndView
     * @param vm
     * @param query
     * @return
     */
    private ModelAndView loadList(ModelAndView modelAndView, PageVm vm, AppointmentQuery query) {
        User user = getUser();
        if (user.getUserRole().equals(UserRoleEnum.Teacher.getRoleId())) {
            query.setTeacherId(user.getId());
        }
        Page<StudentAppointmentDTO> records = appointmentService.find(query, vm.getPageable());
        return toPageView(modelAndView, records,vm.getPageable());
    }
    
    @ResponseBody
    @RequestMapping(value = "/edit")
    public ResponseModel<Boolean> edit(@RequestParam("id") Long id, @RequestParam("status") Integer status) {
        if (status != 1 && status != 2) {
            throw new BusinessException("参数值错误");
        }
        appointmentService.editStatus(id, status);
        return ResponseModels.ok(true);
    }
}
