package com.ald.finance.service.dto;

import com.ald.finance.dal.entity.StudentCourseRecord;

/**
 * Created by zhangliang on 2018/6/3.
 */
public class StudentCourseRecordDTO extends StudentCourseRecord {
    private Integer weekIndex;

    public Integer getWeekIndex() {
        return weekIndex;
    }

    public void setWeekIndex(Integer weekIndex) {
        this.weekIndex = weekIndex;
    }
}
