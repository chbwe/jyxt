package com.ald.finance.dal.entity;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "Student_course_record")
public class StudentCourseRecord {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private Long userId;
  private Long courseId;
  private LocalDate courseDate;
  private Integer courseIndex;
  private LocalTime courseStartTime;
  private LocalTime courseEndTime;
  private Integer status;
  private LocalDateTime createTime;
  private LocalDateTime updateTime;
  private Long buyRecordId;

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

  public LocalDate getCourseDate() {
    return courseDate;
  }

  public void setCourseDate(LocalDate courseDate) {
    this.courseDate = courseDate;
  }

  public Integer getCourseIndex() {
    return courseIndex;
  }

  public void setCourseIndex(Integer courseIndex) {
    this.courseIndex = courseIndex;
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

  public Integer getStatus() {
    return status;
  }

  public void setStatus(Integer status) {
    this.status = status;
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

  public Long getBuyRecordId() {
    return buyRecordId;
  }

  public void setBuyRecordId(Long buyRecordId) {
    this.buyRecordId = buyRecordId;
  }
}
