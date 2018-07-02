package com.ald.finance.dal.entity;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "Student_leave_record")
public class StudentLeaveRecord {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    /**
     * 学生ID
     */
    private Long userId;
    
    /**
     * 请假日期
     */
    @Column(name = "leave_date")
    private LocalDate date;
    
    /**
     * 请假节次
     */
    @Column(name = "leave_index")
    private Integer index;
    /**
     * 请假对应的课程ID
     */
    private Long courseRecordId;
    /**
     * 请假状态 0待审批 1已同意
     */
    private Integer status;

    /**
     * 教师ID
     */
    private Long teacherId;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    
    private LocalTime courseStartTime;
    private LocalTime courseEndTime;
    
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
    
    public LocalDate getDate() {
        return date;
    }
    
    public void setDate(LocalDate date) {
        this.date = date;
    }
    
    public Integer getIndex() {
        return index;
    }
    
    public void setIndex(Integer index) {
        this.index = index;
    }
    
    public Long getCourseRecordId() {
        return courseRecordId;
    }
    
    public void setCourseRecordId(Long courseRecordId) {
        this.courseRecordId = courseRecordId;
    }
    
    public Integer getStatus() {
        return status;
    }
    
    public void setStatus(Integer status) {
        this.status = status;
    }
    
    public Long getTeacherId() {
        return teacherId;
    }
    
    public void setTeacherId(Long teacherId) {
        this.teacherId = teacherId;
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
    
    public LocalTime getCourseStartTime() {
        return courseStartTime;
    }
    
    public void setCourseStartTime(LocalTime courseStartTime) {
        this.courseStartTime = courseStartTime;
    }
    
    public LocalTime getCourseEndTime() {
        return courseEndTime;
    }
    
    public void setCourseEndTime(LocalTime courseEndTime) {
        this.courseEndTime = courseEndTime;
    }
}
