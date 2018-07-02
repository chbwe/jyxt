package com.ald.finance.web.controller;


import com.ald.finance.common.enums.UserRoleEnum;
import com.ald.finance.common.web.ResponseModel;
import com.ald.finance.common.web.ResponseModels;
import com.ald.finance.dal.entity.User;
import com.ald.finance.service.TeacherPayRecordService;
import com.ald.finance.service.dto.TeacherPayRecordDTO;
import com.ald.finance.service.query.TeacherPayRecordQuery;
import com.ald.finance.web.vm.WageSearchVm;
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
@RequestMapping("/teacher")
public class TeacherPayController extends BaseController {

    @Autowired
    TeacherPayRecordService teacherPayRecordService;

    /**
     * 工资结算记录
     *
     * @param modelAndView
     * @param vm
     * @return
     */
    @RequestMapping(value = "/wages")
    public ModelAndView wages(ModelAndView modelAndView, WageSearchVm vm) {
        User user = getUser();
        // 老师只能查询自己的工资结算记录
        TeacherPayRecordQuery query= new TeacherPayRecordQuery();
        if (user.getUserRole().equals(UserRoleEnum.Teacher.getRoleId())) {
            query.setTeacherId(user.getId());
        }
        query.setDate(vm.getDate());
        query.setStatus(vm.getStatus());

        modelAndView.addObject("teacherId", query.getTeacherId());
        modelAndView.addObject("vm", vm);

        Page<TeacherPayRecordDTO> records = teacherPayRecordService.find(query, vm.getPageable());
        modelAndView.setViewName("teacherWages");
        return toPageView(modelAndView, records,vm.getPageable());
    }

    /**
     * 结算工资、只能由老师自己申请、且当月的不能在线申请
     *
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/pay")
    public ResponseModel<Boolean> pay(@RequestParam("id") Long id) {
        teacherPayRecordService.pay(id, getUser());
        return ResponseModels.ok(true);
    }
}
