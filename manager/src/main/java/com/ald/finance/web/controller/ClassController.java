package com.ald.finance.web.controller;

import com.ald.finance.common.enums.UserRoleEnum;
import com.ald.finance.common.web.ResponseModel;
import com.ald.finance.common.web.ResponseModels;
import com.ald.finance.dal.entity.Packages;
import com.ald.finance.service.PackagesService;
import com.ald.finance.web.vm.PackagesVm;
import com.ald.finance.web.vm.PageVm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

/**
 * Created by liang3.zhang on 2018/5/3.
 */
@Controller
@RequestMapping("/class")
public class ClassController extends BaseController {

    @Autowired
    PackagesService packagesService;

    /**
     * 套餐列表
     *
     * @param modelAndView
     * @param vm
     * @return
     */
    @RequestMapping(value = "/list")
    public ModelAndView wages(ModelAndView modelAndView, PageVm vm) {
        Page<Packages> records = packagesService.list(vm.getPageable());
        modelAndView.setViewName("class");
        modelAndView.addObject("isadd", true);
        if (getUser().getUserRole().equals(UserRoleEnum.Teacher.getRoleId())) {
            modelAndView.addObject("isadd", false);
        }
        return toPageView(modelAndView, records,vm.getPageable());
    }

    /**
     * 删除套餐
     *
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/delete")
    public ResponseModel<Boolean> delete(@RequestParam("id") Long id) {
        packagesService.delete(id);
        return ResponseModels.ok(true);
    }

    /**
     * 保存套餐
     *
     * @param vm
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/save")
    public ResponseModel<Packages> save(@RequestBody @Valid PackagesVm vm) {
        checkUserRole();
        Packages packages = new Packages();
        packages.setTimes(vm.getTimes());
        packages.setIndexs(vm.getIndexs());
        packages.setType(vm.getType());
        packages.setPrice(vm.getPrice());
        packages.setId(vm.getId());
        packages = packagesService.save(packages);
        return ResponseModels.ok(packages);
    }

    @ResponseBody
    @RequestMapping(value = "/load")
    public ResponseModel<Packages> load(@RequestParam("id") Long id) {
        checkUserRole();
        Packages packages = packagesService.load(id);
        return ResponseModels.ok(packages);
    }
}
