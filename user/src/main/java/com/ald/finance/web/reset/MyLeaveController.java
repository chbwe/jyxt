package com.ald.finance.web.reset;

import com.ald.finance.dal.entity.StudentLeaveRecord;
import com.ald.finance.service.StudentLeaveRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * Created by liang3.zhang on 2018/5/6.
 */
@Controller
@RequestMapping("/my")
public class MyLeaveController extends BaseController {

    @Autowired
    StudentLeaveRecordService studentLeaveRecordService;


    /**
     * 请假记录
     *
     * @param modelAndView
     * @return
     */
    @RequestMapping(value = "/leaveRe")
    public ModelAndView leaveRe(ModelAndView modelAndView) {
        modelAndView.setViewName("myLeaveRecord");
        if (getUser() == null) {
            modelAndView.addObject("message", "请登陆后再查看请假记录");
            return modelAndView;
        }
        List<StudentLeaveRecord> records = studentLeaveRecordService.listByStudentId(getUser().getId());
        if (CollectionUtils.isEmpty(records)) {
            modelAndView.addObject("message", "暂无历史请假记录");
            return modelAndView;
        }
        modelAndView.addObject("item", records);
        return modelAndView;
    }
}
