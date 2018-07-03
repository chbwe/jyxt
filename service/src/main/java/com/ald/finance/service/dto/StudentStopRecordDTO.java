package com.ald.finance.service.dto;

import com.ald.finance.dal.entity.StudentStopRecord;

/**
 * Created by liang3.zhang on 2018/6/1.
 */
public class StudentStopRecordDTO extends StudentStopRecord {
    private String studentName;
    private String studentMobile;

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
}
