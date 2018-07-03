package com.ald.finance.web.vm;

/**
 * Created by liang3.zhang on 2018/5/3.
 */
public class TeacherSearchVm extends PageVm {

    private Long userId;
    
    private Integer teacherStatus;
    
    private String teacherName;

    public TeacherSearchVm size(int size) {
        super.setSize(size);
        return this;
    }


    public Integer getTeacherStatus() {
        return teacherStatus;
    }

    public void setTeacherStatus(Integer teacherStatus) {
        this.teacherStatus = teacherStatus;
    }

    public String getTeacherName() {
        return teacherName;
    }
    
    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public TeacherSearchVm userId(Long userId) {
        this.userId = userId;
        return this;
    }
}
