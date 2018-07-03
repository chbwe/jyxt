package com.ald.finance.service.dto;

/**
 * Created by liang3.zhang on 2018/5/4.
 */
public class CourseItemDTO {
    
    private Long courseId;
    private String time;
    private Integer courseIndex;

    
    public Long getCourseId() {
        return courseId;
    }
    
    public void setCourseId(Long courseId) {
        this.courseId = courseId;

    }

    public CourseItemDTO courseId(Long courseId) {
        this.courseId = courseId;
        return this;
    }
    
    public String getTime() {
        return time;
    }
    
    public void setTime(String time) {
        this.time = time;
    }

    public CourseItemDTO time(String time) {
        this.time = time;
        return this;
        
    }
    
    public Integer getCourseIndex() {
        return courseIndex;
    }
    
    public void setCourseIndex(Integer courseIndex) {
        this.courseIndex = courseIndex;
    }

    public CourseItemDTO courseIndex(Integer courseIndex) {
        this.courseIndex = courseIndex;
        return this;
    }
}
