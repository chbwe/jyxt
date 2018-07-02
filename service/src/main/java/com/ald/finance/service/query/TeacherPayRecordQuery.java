package com.ald.finance.service.query;

/**
 * Created by liang3.zhang on 2018/6/1.
 */
public class TeacherPayRecordQuery {

    private Long teacherId;

    private String date;

    private Integer status;

    public Long getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Long teacherId) {
        this.teacherId = teacherId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
