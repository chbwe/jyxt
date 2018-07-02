package com.ald.finance.web.controller;

import com.ald.finance.common.enums.UserRoleEnum;
import com.ald.finance.common.web.ResponseModel;
import com.ald.finance.common.web.ResponseModels;
import com.ald.finance.dal.entity.User;
import com.ald.finance.service.StudentLeaveRecordService;
import com.ald.finance.service.dto.StudentLeaveRecordDTO;
import com.ald.finance.web.vm.ClassRecordSearchVm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by liang3.zhang on 2018/5/3.
 */
@Controller
@RequestMapping("/student")
public class StudentLeaveRecordController extends BaseController {
    
    @Autowired
    StudentLeaveRecordService studentLeaveRecordService;
    
    /**
     * 学生请假记录
     *
     * @param modelAndView
     * @param vm
     * @return
     */
    @RequestMapping(value = "/leaveRe")
    public ModelAndView leaveRe(ModelAndView modelAndView, ClassRecordSearchVm vm) {
        User user = getUser();
        init(modelAndView);
        if (user.getUserRole().equals(UserRoleEnum.Teacher.getRoleId())) {
            vm.setTeacherId(user.getId());
        }
        Page<StudentLeaveRecordDTO> records = studentLeaveRecordService.page(vm.getTeacherId(), vm.getPageable());
        modelAndView.setViewName("studentLeaveRe");
        return toPageView(modelAndView, records,vm.getPageable());
    }
    
    /**
     * 删除学员请假记录
     *
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/leaveRe/delete")
    public ResponseModel<Boolean> delete(@RequestParam("id") Long id) {
        studentLeaveRecordService.delete(id, getUser());
        return ResponseModels.ok(true);
    }
    
    /**
     * 审批学员的请假记录
     * 
     * @param id
     * @param status
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/leaveRe/approval")
    public ResponseModel<Boolean> approval(@RequestParam("id") Long id, @RequestParam("status") Integer status) {
        checkUserRole();
        studentLeaveRecordService.approval(id, status);
        return ResponseModels.ok(true);
    }
}
