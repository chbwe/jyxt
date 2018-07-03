package com.ald.finance.service.query;

/**
 * Created by liang3.zhang on 2018/6/1.
 */
public class TeacherLeaveRecordQuery {
    private Long teacherId;

    private String start;

    private String end;

    private Integer status;

    public Long getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Long teacherId) {
        this.teacherId = teacherId;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
