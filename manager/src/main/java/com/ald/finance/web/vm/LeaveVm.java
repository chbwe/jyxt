package com.ald.finance.web.vm;

import javax.validation.constraints.NotNull;

/**
 * Created by liang3.zhang on 2018/5/4.
 */
public class LeaveVm  {

    private Long teacherId;

    @NotNull
    private Integer courseIndex;

    @NotNull
    private String leaveDate;


    public Integer getCourseIndex() {
        return courseIndex;
    }

    public void setCourseIndex(Integer courseIndex) {
        this.courseIndex = courseIndex;
    }

    public Long getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Long teacherId) {
        this.teacherId = teacherId;
    }

    public String getLeaveDate() {
        return leaveDate;
    }

    public void setLeaveDate(String leaveDate) {
        this.leaveDate = leaveDate;
    }
}
