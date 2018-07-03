package com.ald.finance.service.dto;

import com.ald.finance.dal.entity.TeacherCourseRecord;

/**
 * Created by zhangliang on 2018/6/2.
 */
public class TeacherCourseRecordDTO extends TeacherCourseRecord {

    private String teacherName;
    private String teacherMobile;

    private Integer weekIndex;

    public Integer getWeekIndex() {
        return weekIndex;
    }

    public void setWeekIndex(Integer weekIndex) {
        this.weekIndex = weekIndex;
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
