package com.ald.finance.web.controller;

import com.ald.finance.common.web.ResponseModel;
import com.ald.finance.common.web.ResponseModels;
import com.ald.finance.service.StudentStopRecordService;
import com.ald.finance.service.dto.StudentStopRecordDTO;
import com.ald.finance.web.vm.StopSearchVm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by liang3.zhang on 2018/5/3.
 */
@Controller
@RequestMapping("/student")
public class StudentStopRecordController extends BaseController {
    
    @Autowired
    StudentStopRecordService studentStopRecordService;
    
    /**
     * 查询学员停课记录
     * 
     * @param modelAndView
     * @param vm
     * @return
     */
    @RequestMapping(value = "/stopRecord")
    public ModelAndView leave(ModelAndView modelAndView, StopSearchVm vm) {
        modelAndView.addObject("vm", vm);
        init(modelAndView);
        Page<StudentStopRecordDTO> records = studentStopRecordService.find(null, vm.getPageable());
        modelAndView.setViewName("studentStopRe");
        return toPageView(modelAndView, records,vm.getPageable());
    }
    
    /**
     * 删除学员停课记录
     *
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/stopRecord/delete")
    public ResponseModel<Boolean> delete(@RequestParam("id") Long id) {
        studentStopRecordService.delete(id, getUser());
        return ResponseModels.ok(true);
    }

    /**
     * 审批学员的请假记录
     *
     * @param id
     * @param status
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/stopRecord/approval")
    public ResponseModel<Boolean> approval(@RequestParam("id") Long id, @RequestParam("status") Integer status) {
        checkUserRole();
        studentStopRecordService.approval(id, status);
        return ResponseModels.ok(true);
    }
}
