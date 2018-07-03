package com.ald.finance.service.query;

import java.time.LocalDate;

/**
 * Created by zhangliang on 2018/6/2.
 */
public class TeacherCourseRecordQuery {
    private Long courseId;

    private Long userId;
    private  LocalDate courseDate;


    private LocalDate start;

    private LocalDate end;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public LocalDate getStart() {
        return start;
    }

    public void setStart(LocalDate start) {
        this.start = start;
    }

    public LocalDate getEnd() {
        return end;
    }

    public void setEnd(LocalDate end) {
        this.end = end;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public LocalDate getCourseDate() {
        return courseDate;
    }

    public void setCourseDate(LocalDate courseDate) {
        this.courseDate = courseDate;
    }
}
