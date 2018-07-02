package com.ald.finance.web.controller;

import com.ald.finance.common.enums.UserRoleEnum;
import com.ald.finance.common.web.ResponseModel;
import com.ald.finance.common.web.ResponseModels;
import com.ald.finance.dal.entity.TeacherStopRecord;
import com.ald.finance.dal.entity.User;
import com.ald.finance.service.TeacherStopRecordService;
import com.ald.finance.service.dto.TeacherStopRecordDTO;
import com.ald.finance.web.vm.StopCourseVm;
import com.ald.finance.web.vm.StopSearchVm;
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
public class TeacherStopController extends BaseController {
    
    @Autowired
    TeacherStopRecordService teacherStopRecordService;
    
    /**
     * 停课记录
     *
     * @param modelAndView
     * @return
     */
    @RequestMapping(value = "/stopRecord")
    public ModelAndView leave(ModelAndView modelAndView, StopSearchVm vm) {
        modelAndView.addObject("vm", vm);
        init(modelAndView);
        User user = getUser();
        if (user.getUserRole().equals(UserRoleEnum.Teacher.getRoleId())) {
            vm.setTeacherId(user.getId());
        }
        modelAndView.addObject("teacherId", vm.getTeacherId());
        Page<TeacherStopRecordDTO> records = teacherStopRecordService.find(vm.getTeacherId(), vm.getPageable());
        modelAndView.setViewName("teacherStopRe");
        return toPageView(modelAndView, records,vm.getPageable());
    }
    
    /**
     * 删除停课记录
     *
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/stopRecord/delete")
    public ResponseModel<Boolean> deleteStop(@RequestParam("id") Long id) {
        teacherStopRecordService.delete(id, getUser());
        return ResponseModels.ok(true);
    }

    @ResponseBody
    @RequestMapping(value = "/stopRecord/approval")
    public ResponseModel<Boolean> approval(@RequestParam("id") Long id) {
        checkUserRole();
        teacherStopRecordService.approval(id, 1);
        return ResponseModels.ok(true);
    }
    /**
     * 停课
     *
     * @param vm
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/stop")
    public ResponseModel<Boolean> stop(@RequestBody @Valid StopCourseVm vm) {
        User user = getUser();
        if (user.getUserRole().equals(UserRoleEnum.Teacher.getRoleId())) {
            vm.setTeacherId(user.getId());
        }
        TeacherStopRecord record = new TeacherStopRecord();
        record.setUserId(vm.getTeacherId());
        record.setStartTime(LocalDate.parse(vm.getStartDate()));
        record.setEndTime(LocalDate.parse(vm.getEndDate()));
        teacherStopRecordService.stop(record);
        return ResponseModels.ok(true);
    }
}
