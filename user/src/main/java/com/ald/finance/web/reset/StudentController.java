//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ald.finance.web.reset;

import com.ald.finance.common.web.ResponseModel;
import com.ald.finance.common.web.ResponseModels;
import com.ald.finance.dal.entity.StudentStopRecord;
import com.ald.finance.service.StudentLeaveRecordService;
import com.ald.finance.service.StudentStopRecordService;
import com.ald.finance.service.UserService;
import com.ald.finance.web.reset.vm.ChangePwdVm;
import com.ald.finance.web.reset.vm.StopVm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;

@Controller
@RequestMapping({"/user"})
public class StudentController extends BaseController {

    @Autowired
    UserService userService;

    @Autowired
    StudentLeaveRecordService studentLeaveRecordService;

    @Autowired
    StudentStopRecordService studentStopRecordService;

    @ResponseBody
    @RequestMapping(value = {"/password/change"}, method = {RequestMethod.POST})
    public ResponseModel<Boolean> changePassword(@RequestBody @Valid ChangePwdVm vm) {
        this.userService.changePwd(this.getUser().getId(), vm.getPassword(), vm.getNewPassword());
        return ResponseModels.ok(Boolean.TRUE);
    }

    @ResponseBody
    @RequestMapping({"/stop"})
    public ResponseModel<Boolean> stop(@RequestBody StopVm stopVm) {
        StudentStopRecord studentStopRecord = new StudentStopRecord();
        studentStopRecord.setUserId(getUser().getId());
        studentStopRecord.setStartTime(LocalDate.parse(stopVm.getStart()));
        studentStopRecord.setEndTime(LocalDate.parse(stopVm.getEnd()));
        studentStopRecordService.stop(studentStopRecord);
        return ResponseModels.ok(Boolean.TRUE);
    }

    @ResponseBody
    @RequestMapping({"/leave"})
    public ResponseModel<Boolean> leave(@RequestParam Long id) {
        studentLeaveRecordService.leave(id, getUser());
        return ResponseModels.ok(Boolean.TRUE);
    }
}