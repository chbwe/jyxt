package com.ald.finance.service.query;

/**
 * Created by liang3.zhang on 2018/5/4.
 */
public class AppointmentQuery {

    private Long userId;

    private Long teacherId;

    private Integer status;

    public Long getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Long teacherId) {
        this.teacherId = teacherId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public AppointmentQuery status(Integer status) {
        this.status = status;
        return this;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public AppointmentQuery userId(Long userId) {
        this.userId = userId;
        return this;
    }
}
