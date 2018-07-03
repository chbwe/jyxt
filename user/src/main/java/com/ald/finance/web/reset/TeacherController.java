package com.ald.finance.web.reset;

import com.ald.finance.common.enums.TeacherStatusEnum;
import com.ald.finance.common.enums.UserRoleEnum;
import com.ald.finance.dal.entity.User;
import com.ald.finance.service.TeacherCourseService;
import com.ald.finance.service.UserService;
import com.ald.finance.service.dto.CourseDTO;
import com.ald.finance.service.query.UserQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liang3.zhang on 2018/5/6.
 */
@Controller
@RequestMapping("/teacher")
public class TeacherController {

    @Autowired
    UserService userService;

    @Autowired
    TeacherCourseService teacherCourseService;


    /**
     * 显示老师详情
     *
     * @param modelAndView
     * @param teacherId
     * @return
     */
    @RequestMapping(value = "/detail")
    public ModelAndView detail(ModelAndView modelAndView, @RequestParam("teacherId") Long teacherId) {
        User teacher = userService.findOne(teacherId);
        modelAndView.addObject("teacher", teacher);
        modelAndView.setViewName("articleDetail");
        // 查询老师的排班
        List<CourseDTO> list = teacherCourseService.listByTeacherId(teacherId);
        modelAndView.addObject("item", list);
        return modelAndView;
    }

    // 显示能够预约的老师列表
    @RequestMapping(value = "/classified")
    public ModelAndView classified(ModelAndView modelAndView, @RequestParam("page") Integer page) {
        if (page == null || page <= 1)
            page = 1;
        Page<User> records = userService.findTeacherList(new UserQuery().userRole(UserRoleEnum.Teacher.getRoleId())
                .status(TeacherStatusEnum.Course.getCode()), new PageRequest(page - 1, 10));
        modelAndView.setViewName("classified_database");
        modelAndView.addObject("item", records.getContent());
        return toPage(modelAndView, page, records);
    }

    // 显示老师列表
    @RequestMapping(value = "/list")
    public ModelAndView list(ModelAndView modelAndView, @RequestParam("page") Integer page) {
        if (page == null || page <= 1)
            page = 1;
        Page<User> records = userService.findTeacherList(new UserQuery(), new PageRequest(page - 1, 5));
        modelAndView.setViewName("article");
        return toPage(modelAndView, page, records);
    }

    private ModelAndView toPage(ModelAndView modelAndView, Integer page, Page records) {
        modelAndView.addObject("item", records.getContent());
        modelAndView.addObject("currentPage", page);
        modelAndView.addObject("totalPage", records.getTotalPages());
        // 封装分页
        List<Integer> list = new ArrayList<Integer>();
        int i = page, j = records.getTotalPages();
        if (j <= 10) {
            i = 1;
        } else if (i == j) {
            i = j - 10;
        } else {
            j = page + 10;
            if (j >= records.getTotalPages())
                j = records.getTotalPages();
        }
        do {
            list.add(i++);
        }
        while (i <= j);
        modelAndView.addObject("page", list);
        return modelAndView;
    }
}