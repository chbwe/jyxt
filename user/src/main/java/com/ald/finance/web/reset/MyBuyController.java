package com.ald.finance.web.reset;

import com.ald.finance.service.StudentBuyRecordService;
import com.ald.finance.service.dto.StudentBuyRecordDTO;
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
public class MyBuyController extends BaseController {

    @Autowired
    StudentBuyRecordService studentBuyRecordService;



    /**
     * 我的购买记录
     *
     * @param modelAndView
     * @return
     */
    @RequestMapping(value = "/buy")
    public ModelAndView buy(ModelAndView modelAndView) {
        modelAndView.setViewName("myBuy");
        if (getUser() == null) {
            modelAndView.addObject("message", "请登陆后再查看购买记录");
            return modelAndView;
        }
        List<StudentBuyRecordDTO> records = studentBuyRecordService.find(getUser().getId());
        if (CollectionUtils.isEmpty(records)) {
            modelAndView.addObject("message", "暂无历史购买记录");
            return modelAndView;
        }
        modelAndView.addObject("item", records);
        return modelAndView;
    }
}
