package com.ald.finance.web.reset;


import com.ald.finance.common.web.ResponseModel;
import com.ald.finance.common.web.ResponseModels;
import com.ald.finance.dal.entity.Packages;
import com.ald.finance.dal.entity.StudentAppointment;
import com.ald.finance.dal.entity.StudentBuyRecord;
import com.ald.finance.service.ClassifiedService;
import com.ald.finance.service.PackagesService;
import com.ald.finance.service.StudentAppointmentService;
import com.ald.finance.service.TeacherCourseService;
import com.ald.finance.service.dto.CourseDTO;
import com.ald.finance.web.reset.vm.ClassifiedApplyVm;
import com.ald.finance.web.reset.vm.ClassifiedStep3Vm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

/**
 * Created by liang3.zhang on 2018/5/8.
 */

@Controller
@RequestMapping("/classified")
public class ClassifiedController extends BaseController {
    @Autowired
    StudentAppointmentService studentAppointmentService;

    @Autowired
    PackagesService packagesService;

    @Autowired
    TeacherCourseService teacherCourseService;

    @Autowired
    ClassifiedService classifiedService;

    /**
     * 获取套餐列表
     *
     * @param modelAndView
     * @param
     * @return
     */
    @RequestMapping(value = "/step1")
    public ModelAndView step1(ModelAndView modelAndView) {
        Page<Packages> packages = packagesService.list(new PageRequest(0, 100));
        modelAndView.setViewName("content_step1");
        modelAndView.addObject("item", packages.getContent());
        return modelAndView;
    }

    /**
     * 获取老师课程
     *
     * @param modelAndView
     * @return
     */
    @RequestMapping(value = "/step3")
    public ModelAndView step3(ModelAndView modelAndView, ClassifiedStep3Vm vm) {
        modelAndView.setViewName("content_step3");
        modelAndView.addObject("inputType", "buy".equals(vm.getReservationType()) ? "checkbox" : "radio");
        List<CourseDTO> list = teacherCourseService.findAllByTeacherId(vm.getTeacherId(), vm.getPackageId());
//        List<CourseDTO> list = teacherCourseService.findAllByTeacherId(vm.getTeacherId(), null);
        modelAndView.addObject("packageType",vm.getPackageId());
        modelAndView.addObject("packageType",vm.getPackageId());
        modelAndView.addObject("item", list);
        return modelAndView;
    }

    /**
     * 提交申请
     *
     * @param vm
     * @return
     */
    @RequestMapping(value = "/apply")
    @ResponseBody
    public ResponseModel<String> apply(@Valid @RequestBody ClassifiedApplyVm vm) {
        if ("reservation".equals(vm.getReservationType()) && vm.getPackageId() != null) {
            return ResponseModels.paramValidException("参数错误");
        }
        if ("buy".equals(vm.getReservationType()) && vm.getPackageId() == null) {
            return ResponseModels.paramValidException("请选择套餐");
        }
        //提交预约申请
        if (!"buy".equals(vm.getReservationType())) {
            StudentAppointment studentAppointment = new StudentAppointment();
            studentAppointment.setStudentId(getUser().getId());
            studentAppointment.setTeacherId(vm.getTeacherId());
            studentAppointment.setStartDate(LocalDate.parse(vm.getCourseDate()));
            studentAppointment = studentAppointmentService.save(studentAppointment, vm.getCourseId().get(0));
            return ResponseModels.ok(vm.getReservationType() + "/" + studentAppointment.getId());
        }
        StudentBuyRecord record = classifiedService.buy(getUser(), vm);
        return ResponseModels.ok(vm.getReservationType() + "/" + record.getId());
    }
}
