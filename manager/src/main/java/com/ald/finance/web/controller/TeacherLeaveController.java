package com.ald.finance.web.controller;


import com.ald.finance.common.enums.UserRoleEnum;
import com.ald.finance.common.web.ResponseModel;
import com.ald.finance.common.web.ResponseModels;
import com.ald.finance.dal.entity.TeacherLeaveRecord;
import com.ald.finance.dal.entity.User;
import com.ald.finance.service.TeacherLeaveRecordService;
import com.ald.finance.service.dto.TeacherLeaveRecordDTO;
import com.ald.finance.service.query.TeacherLeaveRecordQuery;
import com.ald.finance.web.vm.ClassRecordSearchVm;
import com.ald.finance.web.vm.LeaveVm;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.time.LocalDate;

/**
 * Created by liang3.zhang on 2018/5/3.
 */
@Controller
@RequestMapping("/teacher")
public class TeacherLeaveController extends BaseController {
    
    @Autowired
    TeacherLeaveRecordService teacherLeaveRecordService;
    
    /**
     * 请假记录
     *
     * @param modelAndView
     * @return
     */
    @RequestMapping(value = "/leaveRecord")
    public ModelAndView leave(ModelAndView modelAndView, ClassRecordSearchVm vm) {
        User user = getUser();
        init(modelAndView);
        if (user.getUserRole().equals(UserRoleEnum.Teacher.getRoleId())) {
            vm.setTeacherId(user.getId());
        }
        modelAndView.addObject("vm", vm);
        modelAndView.addObject("teacherId", vm.getTeacherId());
        TeacherLeaveRecordQuery query = new TeacherLeaveRecordQuery();
        BeanUtils.copyProperties(vm, query);
        Page<TeacherLeaveRecordDTO> records = teacherLeaveRecordService.page(query, vm.getPageable());
        modelAndView.setViewName("teacherLeaveRe");
        return toPageView(modelAndView, records,vm.getPageable());
    }
    
    /**
     * 请假
     *
     * @param
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/leave")
    public ResponseModel<Boolean> leave(@RequestBody @Valid LeaveVm leaveVm) {
        User user = getUser();
        if (user.getUserRole().equals(UserRoleEnum.Teacher.getRoleId())) {
            leaveVm.setTeacherId(user.getId());
        }
        TeacherLeaveRecord record = new TeacherLeaveRecord();
        record.setUserId(leaveVm.getTeacherId());
        record.setIndex(leaveVm.getCourseIndex());
        record.setDate(LocalDate.parse(leaveVm.getLeaveDate()));
        teacherLeaveRecordService.leave(record);
        return ResponseModels.ok(true);
    }

    /**
     * 审批请假申请
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/leaveRecord/approval")
    public ResponseModel<Boolean> approval(@RequestParam("id") Long id) {
        checkUserRole();
        teacherLeaveRecordService.approval(id, 1);
        return ResponseModels.ok(true);
    }
    /**
     * 删除请假记录
     *
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/leaveRecord/delete")
    public ResponseModel<Boolean> deleteLeave(@RequestParam("id") Long id) {
        teacherLeaveRecordService.delete(id, getUser());
        return ResponseModels.ok(true);
    }
}
