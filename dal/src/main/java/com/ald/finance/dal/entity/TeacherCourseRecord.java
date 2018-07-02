package com.ald.finance.dal.entity;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "Teacher_course_record")
public class TeacherCourseRecord {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private Long courseId;
    private LocalDate courseRecordDate;
    private Integer courseRecordIndex;
    private Integer courseRecordStatus;
    private Double courseRecordPrice;
    private Integer courseRecordPriceFlag;
    private LocalTime courseRecordStartTime;
    private LocalTime courseRecordEndTime;
    private Integer courseRecordTimes;
    private Integer max;
    private Integer current;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public LocalDate getCourseRecordDate() {
        return courseRecordDate;
    }

    public void setCourseRecordDate(LocalDate courseRecordDate) {
        this.courseRecordDate = courseRecordDate;
    }

    public Integer getCourseRecordIndex() {
        return courseRecordIndex;
    }

    public void setCourseRecordIndex(Integer courseRecordIndex) {
        this.courseRecordIndex = courseRecordIndex;
    }

    public Integer getCourseRecordStatus() {
        return courseRecordStatus;
    }

    public void setCourseRecordStatus(Integer courseRecordStatus) {
        this.courseRecordStatus = courseRecordStatus;
    }

    public Double getCourseRecordPrice() {
        return courseRecordPrice;
    }

    public void setCourseRecordPrice(Double courseRecordPrice) {
        this.courseRecordPrice = courseRecordPrice;
    }

    public Integer getCourseRecordPriceFlag() {
        return courseRecordPriceFlag;
    }

    public void setCourseRecordPriceFlag(Integer courseRecordPriceFlag) {
        this.courseRecordPriceFlag = courseRecordPriceFlag;
    }

    public LocalTime getCourseRecordStartTime() {
        return courseRecordStartTime;
    }

    public void setCourseRecordStartTime(LocalTime courseRecordStartTime) {
        this.courseRecordStartTime = courseRecordStartTime;
    }

    public LocalTime getCourseRecordEndTime() {
        return courseRecordEndTime;
    }

    public void setCourseRecordEndTime(LocalTime courseRecordEndTime) {
        this.courseRecordEndTime = courseRecordEndTime;
    }

    public Integer getCourseRecordTimes() {
        return courseRecordTimes;
    }

    public void setCourseRecordTimes(Integer courseRecordTimes) {
        this.courseRecordTimes = courseRecordTimes;
    }

    public Integer getMax() {
        return max;
    }

    public void setMax(Integer max) {
        this.max = max;
    }

    public Integer getCurrent() {
        return current;
    }

    public void setCurrent(Integer current) {
        this.current = current;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }
}
