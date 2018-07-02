package com.ald.finance.web.controller;

import com.ald.finance.common.enums.UserRoleEnum;
import com.ald.finance.common.web.ResponseModel;
import com.ald.finance.common.web.ResponseModels;
import com.ald.finance.dal.entity.Holiday;
import com.ald.finance.service.HolidayService;
import com.ald.finance.web.vm.HolidayVm;
import com.ald.finance.web.vm.PageVm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.time.LocalDate;

/**
 * Created by liang3.zhang on 2018/5/3.
 */
@Controller
@RequestMapping("/holiday")
public class HolidayController extends BaseController {
    
    @Autowired
    HolidayService holidayService;
    
    /**
     * 节假日记录
     * 
     * @param modelAndView
     * @param vm
     * @return
     */
    @RequestMapping(value = "/list")
    public ModelAndView wages(ModelAndView modelAndView, PageVm vm) {
        Page<Holiday> records = holidayService.list(vm.getPageable());
        modelAndView.setViewName("holiday");
        modelAndView.addObject("isadd", true);
        if (getUser().getUserRole().equals(UserRoleEnum.Teacher.getRoleId())) {
            modelAndView.addObject("isadd", false);
        }
        return toPageView(modelAndView, records,vm.getPageable());
    }
    
    /**
     * 添加历史记录
     *
     * @param vm
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseModel<Boolean> add(@RequestBody @Valid HolidayVm vm) {
        checkUserRole();
        Holiday holiday = new Holiday();
        holiday.setUserId(getUser().getId());
        holiday.setHolidayDay(LocalDate.parse(vm.getHolidayDay()));
        holiday.setHolidayName(vm.getHolidayName());
        holidayService.add(holiday);
        return ResponseModels.ok(true);
    }
    
    /**
     * 删除节假日
     * 
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/delete")
    public ResponseModel<Boolean> delete(@RequestParam("id") Long id) {
        checkUserRole();
        holidayService.delete(id);
        return ResponseModels.ok(true);
    }
}
