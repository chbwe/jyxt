package com.ald.finance.web.reset;

import com.ald.finance.common.enums.SMSEnum;
import com.ald.finance.common.enums.UserRoleEnum;
import com.ald.finance.common.utils.RandomUtil;
import com.ald.finance.common.utils.SendMessageUtils;
import com.ald.finance.common.web.ResponseModel;
import com.ald.finance.common.web.ResponseModels;
import com.ald.finance.dal.entity.User;
import com.ald.finance.service.UserService;
import com.ald.finance.web.reset.vm.LoginVm;
import com.ald.finance.web.reset.vm.RegisterVm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by liang3.zhang on 2018/5/6.
 */
@Controller
@RequestMapping("/user")
public class IndexController extends BaseController {

    private static final Logger log = LoggerFactory.getLogger(IndexController.class);

    @Autowired
    UserService userService;


    /**
     * 获取head菜单栏
     *
     * @param modelAndView
     * @return
     */
    @RequestMapping(value = "/head")
    public ModelAndView head(ModelAndView modelAndView) {
        modelAndView.setViewName("head");
        User user = getUser();
        modelAndView.addObject("isLogin", user != null ? true : false);
        return modelAndView;
    }

    /**
     * 获取菜单栏
     *
     * @param modelAndView
     * @return
     */
    @RequestMapping(value = "/navbar")
    public ModelAndView navbar(ModelAndView modelAndView) {
        modelAndView.setViewName("navbar");
        return modelAndView;
    }

    /**
     * 登录
     *
     * @param
     * @return
     */
    @RequestMapping(value = "/login")
    @ResponseBody
    public ResponseModel login(@RequestBody LoginVm loginVm) {
        User user = userService.login(loginVm.getUsername(), loginVm.getPassword());
        if (user == null) {
            return ResponseModels.paramValidException("用户名或者密码错误");
        }
        if (!UserRoleEnum.Student.getRoleId().equals(user.getUserRole())) {
            return ResponseModels.paramValidException("只支持学生账号的登陆");
        }
        setUser(user);
        return ResponseModels.ok(true);
    }

    @RequestMapping(value = "/logout")
    @ResponseBody
    public ResponseModel logout() {
        remove();
        return ResponseModels.ok(true);
    }

    /**
     * 验证用户是否已登陆
     *
     * @return
     */
    @RequestMapping(value = "/valid")
    @ResponseBody
    public ResponseModel valid() {
        return ResponseModels.ok(getUser() != null);
    }

    /**
     * 获取注册验证码
     *
     * @param userMobile
     * @return
     */
    @RequestMapping(value = "/code")
    @ResponseBody
    public ResponseModel code(@RequestParam("userMobile") String userMobile) {
        User user = userService.findOneByUserMobile(userMobile);
        if (user != null) {
            return ResponseModels.paramValidException("此手机号已注册");
        }
        String code = RandomUtil.generateVCode();
        session.setAttribute("register_code_" + userMobile, code);
        Map<String, String> map = new HashMap<>();
        map.put("code", code);
        try {
            SendMessageUtils.sendSms(userMobile, SMSEnum.register, map);
        } catch (Exception e) {
            log.error("短信接口调用失败");
        }
        return ResponseModels.ok(true);
    }

    /**
     * 注册
     *
     * @param vm
     * @return
     */
    @RequestMapping(value = "/register")
    @ResponseBody
    public ResponseModel register(@Valid @RequestBody RegisterVm vm) {
        User user = userService.findOneByUserMobileOrUserNickname(vm.getUserMobile(), vm.getUserEmail());
        if (user != null) {
            return ResponseModels.paramValidException("此手机号已注册");
        }
        String code = (String) session.getAttribute("register_code_" + vm.getUserMobile());
        if (!vm.getVercode().equals(code)) {
            return ResponseModels.paramValidException("手机验证码错误");
        }
        // 保存到数据库
        user = new User();
        user.setUserNickname(vm.getUserEmail());
        user.setUserPwd(vm.getPassword());
        user.setUserMobile(vm.getUserMobile());
        user.setUserRole(UserRoleEnum.Student.getRoleId());
        user.setCreateTime(LocalDateTime.now());
        user.setUpdateTime(LocalDateTime.now());
        userService.save(user);
        return ResponseModels.ok(true);
    }
}