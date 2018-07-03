package com.ald.finance.web.reset;


import com.ald.finance.service.StudentCourseRecordService;
import com.ald.finance.service.dto.CourseDTO;
import com.ald.finance.service.dto.StudentCourseRecordDTO;
import com.ald.finance.service.query.TeacherCourseRecordQuery;
import com.ald.finance.service.utils.TeacherCourseUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by liang3.zhang on 2018/5/6.
 */
@Controller
@RequestMapping("/my")
public class MyCourseController extends BaseController {

    @Autowired
    StudentCourseRecordService studentCourseRecordService;


    /**
     * 我的课程
     *
     * @param modelAndView
     * @return
     */
    @RequestMapping(value = "/course")
    public ModelAndView course(ModelAndView modelAndView, @RequestParam(value = "start", required = false) String start) {
        modelAndView.setViewName("myClass");
        if (getUser() == null) {
            modelAndView.addObject("message", "请登陆后再查看课程");
            return modelAndView;
        }
        TeacherCourseRecordQuery query = new TeacherCourseRecordQuery();
        query.setUserId(getUser().getId());
        LocalDate startDate, endDate;
        if (StringUtils.isBlank(start)) {
            startDate = TeacherCourseUtils.getFirstWeekWithNow();
        } else {
            startDate = TeacherCourseUtils.getFirstWeekWithStartDate(start);
        }
        endDate = TeacherCourseUtils.getEndDate(startDate);
        query.setStart(startDate);
        query.setEnd(endDate);
        Page<StudentCourseRecordDTO> page = studentCourseRecordService.find(query, new PageRequest(0, 100));

        if (CollectionUtils.isEmpty(page.getContent())) {
            modelAndView.addObject("message", "暂无课程记录");
            return modelAndView;
        }
        List<CourseDTO> list = TeacherCourseUtils.recordByStudent(page.getContent(), startDate);
        if (!CollectionUtils.isEmpty(list)) {
            LocalDate localDate = LocalDate.now().plusDays(1);//24小时内不能请假
            list = list.stream().map(m -> {
                LocalDate date = LocalDate.parse(m.getWeekDay());
                if (date.isBefore(localDate)) {
                    m.setKey1(m.getKey1().replace("未上课", "待上课"));
                }
                return m;
            }).collect(Collectors.toList());
        }
        modelAndView.addObject("item", list);
        return modelAndView;
    }


}
