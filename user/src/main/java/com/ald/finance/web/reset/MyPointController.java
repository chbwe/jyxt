package com.ald.finance.web.reset;

import com.ald.finance.service.StudentAppointmentService;
import com.ald.finance.service.dto.StudentAppointmentDTO;
import com.ald.finance.service.query.AppointmentQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by liang3.zhang on 2018/5/6.
 */
@Controller
@RequestMapping("/my")
public class MyPointController extends BaseController {

    @Autowired
    StudentAppointmentService studentAppointmentService;

    /**
     * 我的预约记录
     *
     * @param modelAndView
     * @return
     */
    @RequestMapping(value = "/point")
    public ModelAndView point(ModelAndView modelAndView) {
        modelAndView.setViewName("myApoint");
        if (getUser() == null) {
            modelAndView.addObject("message", "请登陆后再查看预约记录");
            return modelAndView;
        }
        Page<StudentAppointmentDTO> records = studentAppointmentService.find(new AppointmentQuery().userId(getUser().getId()),
                new PageRequest(0, 100));
        if (CollectionUtils.isEmpty(records.getContent())) {
            modelAndView.addObject("message", "暂无历史预约记录");
            return modelAndView;
        }
        modelAndView.addObject("item", records.getContent());
        return modelAndView;
    }

}
