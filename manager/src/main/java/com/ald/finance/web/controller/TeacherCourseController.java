package com.ald.finance.web.controller;

import com.ald.finance.common.enums.UserRoleEnum;
import com.ald.finance.common.web.BusinessException;
import com.ald.finance.common.web.ResponseModel;
import com.ald.finance.common.web.ResponseModels;
import com.ald.finance.dal.entity.TeacherCourse;
import com.ald.finance.dal.entity.User;
import com.ald.finance.service.TeacherCourseService;
import com.ald.finance.service.UserService;
import com.ald.finance.service.dto.CourseDTO;
import com.ald.finance.web.vm.CourseVm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalTime;
import java.util.List;

/**
 * Created by liang3.zhang on 2018/5/3.
 */
@Controller
@RequestMapping("/teacher")
public class TeacherCourseController extends BaseController {
    
    @Autowired
    TeacherCourseService teacherCourseService;
    
    @Autowired
    UserService userService;
    
    /**
     * 我的课程
     *
     * @param modelAndView
     * @param
     * @return
     */
    @RequestMapping(value = "/course")
    public ModelAndView course(ModelAndView modelAndView, Long teacherId) {
        User user = getUser();
        modelAndView.setViewName("teacherCourse");
        if (user.getUserRole().equals(UserRoleEnum.Teacher.getRoleId())) {
            teacherId = user.getId();
        }
        if (teacherId == null) {
            return modelAndView;
        }
        User teacher = userService.findOne(teacherId);
        if (teacher == null) {
            return modelAndView;
        }
        // 设置默认选择的教师列表
        httpSession.setAttribute(user.getId() + "-select-teacherId", teacherId);
        
        modelAndView.addObject("teacherName", teacher.getUserNickname());
        modelAndView.addObject("class", "basicInline");
        List<CourseDTO> list = teacherCourseService.listByTeacherId(teacherId);
        modelAndView.addObject("item", list);
        return modelAndView;
    }
    
    /**
     * 修改课程
     * 
     * @param courseVm
     * @return
     */
    @RequestMapping(value = "/course/edit")
    @ResponseBody
    public ResponseModel<Long> saveCourse(CourseVm courseVm) {
        String[] value = courseVm.getValue().split("-");
        if (value.length != 2) {
            throw new BusinessException("时间格式错误，时间格式为:08:00-09:00");
        }
        for(String v:value){
            String[] _v = v.split(":");
            if(_v.length !=2){
                throw new BusinessException("时间格式错误，时间格式为:08:00-09:00");
            }
            if(_v[0].length()!=2 || _v[1].length() !=2){
                throw new BusinessException("时间格式错误，时间格式为:08:00-09:00");
            }
        }
        String[] pk = courseVm.getPk().split("_");
        Integer weekIndex = Integer.parseInt(pk[0]);
        Integer courseIndex = Integer.parseInt(pk[1]);
        Long courseId = Long.parseLong(pk[2]);
        TeacherCourse teacherCourse = new TeacherCourse();
        teacherCourse.setWeekIndex(weekIndex);
        teacherCourse.setCourseIndex(courseIndex);
        if ( courseId != 0) {
            teacherCourse.setId(courseId);
        }
        teacherCourse.setStartTime(LocalTime.parse(value[0]));
        teacherCourse.setEndTime(LocalTime.parse(value[1]));
        boolean isTeacher = true;
        User user = getUser();
        if (user.getUserRole().equals(UserRoleEnum.Teacher.getRoleId())) {
            teacherCourse.setUserId(user.getId());
        } else if (user.getUserRole().equals(UserRoleEnum.Admin.getRoleId())) {
            // 设置默认选择的教师列表
            isTeacher = false;
            Object obj = httpSession.getAttribute(user.getId() + "-select-teacherId");
            if (obj == null) {
                throw new BusinessException("请选择教师");
            }
            teacherCourse.setUserId(Long.valueOf(obj.toString()));
        }
        teacherCourseService.save(teacherCourse, isTeacher);
        return ResponseModels.ok(teacherCourse.getId());
    }
}
