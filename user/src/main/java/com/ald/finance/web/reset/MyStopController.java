package com.ald.finance.web.reset;

import com.ald.finance.service.StudentStopRecordService;
import com.ald.finance.service.dto.StudentStopRecordDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
public class MyStopController extends BaseController {

    @Autowired
    StudentStopRecordService studentStopRecordService;


    /**
     * 停课记录
     *
     * @param modelAndView
     * @return
     */
    @RequestMapping(value = "/stopRecord")
    public ModelAndView stop(ModelAndView modelAndView) {
        modelAndView.setViewName("myStopRecord");
        if (getUser() == null) {
            modelAndView.addObject("message", "请登陆后再查看停课记录");
            return modelAndView;
        }
        Page<StudentStopRecordDTO> page = studentStopRecordService.find(getUser().getId(), new PageRequest(0, 100));
        List<StudentStopRecordDTO> records = page.getContent();
        if (CollectionUtils.isEmpty(records)) {
            modelAndView.addObject("message", "暂无历史停课记录");
            return modelAndView;
        }
        modelAndView.addObject("item", records);
        return modelAndView;
    }
}
