package com.ald.finance.service.dto;

import com.ald.finance.dal.entity.StudentLeaveRecord;

/**
 * Created by liang3.zhang on 2018/6/1.
 */
public class StudentLeaveRecordDTO extends StudentLeaveRecord {
    private String studentName;
    private String studentMobile;

    private String teacherName;
    private String teacherMobile;

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getStudentMobile() {
        return studentMobile;
    }

    public void setStudentMobile(String studentMobile) {
        this.studentMobile = studentMobile;
    }

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
