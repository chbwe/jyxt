package com.ald.finance.web.controller;

import com.ald.finance.dal.entity.User;
import com.ald.finance.common.enums.TeacherStatusEnum;
import com.ald.finance.common.enums.UserRoleEnum;
import com.ald.finance.service.query.UserQuery;
import com.ald.finance.common.web.ResponseModel;
import com.ald.finance.common.web.ResponseModels;
import com.ald.finance.service.UserService;
import com.ald.finance.web.vm.ChangePwdVm;
import com.ald.finance.web.vm.TeacherSearchVm;
import com.ald.finance.web.vm.TeacherVm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

/**
 * Created by liang3.zhang on 2018/5/3.
 */
@Controller
@RequestMapping("/teacher")
public class TeacherController extends BaseController {
    
    @Autowired
    UserService userService;
    
    /**
     * 添加老师或者修改老师信息
     *
     * @param vm
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseModel<Boolean> add(@RequestBody @Valid TeacherVm vm) {
        User user = new User();
        user.setUserSchool(vm.getUserSchool());
        user.setUserImg(vm.getUserImg());
        user.setUserNickname(vm.getUserNickname());
        user.setUserContent(vm.getUserContent());
        user.setUserPrice(vm.getUserPrice());
        user.setUserPrice2(vm.getUserPrice2());
        user.setUserRole(UserRoleEnum.Teacher.getRoleId());
        if (vm.getId() != null) {
            user.setId(vm.getId());
            userService.updateTeacher(user);
        } else {
            user.setStatus(TeacherStatusEnum.Deft.getCode());
            user.setUserMobile(vm.getUserMobile());
            userService.addTeacher(user);
        }
        return ResponseModels.ok(true);
    }
    
    /**
     * 重置密码
     *
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/password/reset", method = RequestMethod.GET)
    public ResponseModel<Boolean> resetPassword(@RequestParam("id") Long id) {
        User user = getUser();
        if (!user.getUserRole().equals(UserRoleEnum.Admin.getRoleId()) && !user.getId().equals(id)) {
            return ResponseModels.paramValidException("无权限重置密码");
        }
        userService.resetPassword(id);
        return ResponseModels.ok(true);
    }
    
    /**
     * 更改密码
     *
     * @param vm
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/password/change", method = RequestMethod.POST)
    public ResponseModel<Boolean> changePassword(@RequestBody @Valid ChangePwdVm vm) {
        userService.changePwd(getUser().getId(), vm.getPassword(), vm.getNewPassword());
        return ResponseModels.ok(true);
    }
    
    /**
     * 教师列表
     *
     * @param modelAndView
     * @param vm
     * @return
     */
    @RequestMapping(value = "/list")
    public ModelAndView all(ModelAndView modelAndView, TeacherSearchVm vm) {
        User user = getUser();
        modelAndView.addObject("isadd", true);
        if (user.getUserRole().equals(UserRoleEnum.Teacher.getRoleId())) {
            modelAndView.addObject("isadd", false);
        }
        Page<User> records = search(vm);
        modelAndView.addObject("vm", vm);
        modelAndView.setViewName("teacher");
        return toPageView(modelAndView, records,vm.getPageable());
    }
    
    private Page<User> search(TeacherSearchVm vm) {
        User user = getUser();
        UserQuery query = new UserQuery().userRole(UserRoleEnum.Teacher.getRoleId()).status(vm.getTeacherStatus())
            .name(vm.getTeacherName());
        if (user.getUserRole().equals(UserRoleEnum.Teacher.getRoleId())) {
            query.setUserId(user.getId());
        }
        return userService.findTeacherList(query, vm.getPageable());
    }
    
    @RequestMapping(value = "/select")
    public ModelAndView select(ModelAndView modelAndView, TeacherSearchVm vm) {
        Page<User> records = search(vm);
        modelAndView.setViewName("teacherSelect");
        modelAndView.addObject("item", records.getContent());
        return modelAndView;
    }
    
    /**
     * 获取某个老师的详细f信息
     *
     * @param teacherId
     * @return
     */
    @RequestMapping(value = "/load")
    @ResponseBody
    public ResponseModel<User> load(@RequestParam("teacherId") Long teacherId) {
        User user = getUser();
        if (user.getUserRole().equals(UserRoleEnum.Teacher.getRoleId())) {
            return ResponseModels.ok(user);
        }
        user = userService.findOne(teacherId);
        user.setUserPwd(null);
        return ResponseModels.ok(user);
    }
}
