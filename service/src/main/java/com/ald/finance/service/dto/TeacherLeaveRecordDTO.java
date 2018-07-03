package com.ald.finance.service.dto;

import com.ald.finance.dal.entity.TeacherLeaveRecord;

/**
 * Created by liang3.zhang on 2018/6/1.
 */
public class TeacherLeaveRecordDTO extends TeacherLeaveRecord {
    private String teacherName;
    private String teacherMobile;

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getTeacherMobile() {
        return teacherMobile;
    }

    public void setTeacherMobile(String teacherMobile) {
        this.teacherMobile = teacherMobile;
    }
}
