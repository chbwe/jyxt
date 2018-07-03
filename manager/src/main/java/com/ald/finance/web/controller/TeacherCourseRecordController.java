package com.ald.finance.web.controller;

import com.ald.finance.common.enums.UserRoleEnum;
import com.ald.finance.dal.entity.User;
import com.ald.finance.service.TeacherCourseRecordService;
import com.ald.finance.service.UserService;
import com.ald.finance.service.dto.TeacherCourseRecordDTO;
import com.ald.finance.service.query.TeacherCourseRecordQuery;
import com.ald.finance.service.utils.TeacherCourseUtils;
import com.ald.finance.web.vm.ClassRecordSearchVm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;

/**
 * Created by liang3.zhang on 2018/5/3.
 */
@Controller
@RequestMapping("/teacher")
public class TeacherCourseRecordController extends BaseController {
    
    @Autowired
    TeacherCourseRecordService teacherCourseRecordService;
    
    @Autowired
    UserService userService;
    
    /**
     * 上课记录
     *
     * @param modelAndView
     * @return
     */
    @RequestMapping(value = "/classRecord")
    public ModelAndView classRecord(ModelAndView modelAndView, ClassRecordSearchVm vm) {
        modelAndView.setViewName("teacherClassRe");
        User user = getUser();
        if (user.getUserRole().equals(UserRoleEnum.Teacher.getRoleId())) {
            vm.setTeacherId(user.getId());
        }
        if (vm.getTeacherId() == null) {
            return modelAndView;
        }
        modelAndView.addObject("teacherName", vm.getTeacherName());
        modelAndView.addObject("teacherId", vm.getTeacherId());
        modelAndView.addObject("vm", vm);
        // 时间初始化
        LocalDate start, end;
        if (StringUtils.isEmpty(vm.getStartDate())) {
            start = TeacherCourseUtils.getFirstWeekWithNow();
            vm.setStartDate(start.toString());
        } else {
            start = TeacherCourseUtils.getFirstWeekWithStartDate(vm.getStartDate());
        }
        modelAndView.addObject("startDate", vm.getStartDate());
        end = TeacherCourseUtils.getEndDate(start);

        TeacherCourseRecordQuery query = new TeacherCourseRecordQuery();
        query.setUserId(vm.getTeacherId());
        query.setStart(start);
        query.setEnd(end);

        Page<TeacherCourseRecordDTO> records = teacherCourseRecordService.find(query,vm.getPageable());
        modelAndView.addObject("item", TeacherCourseUtils.recordByTeacherId(records.getContent(),start));
        return modelAndView;
    }
}
