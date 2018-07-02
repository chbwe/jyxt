package com.ald.finance.web.vm;

import java.time.LocalDateTime;

/**
 * Created by liang3.zhang on 2018/5/4.
 */
public class ClassRecordSearchVm extends PageVm{

    private String startDate;

    private String endDate;

    private Integer status;

    private LocalDateTime courseTime;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public LocalDateTime getCourseTime() {
        return courseTime;
    }

    public void setCourseTime(LocalDateTime courseTime) {
        this.courseTime = courseTime;
    }
}
