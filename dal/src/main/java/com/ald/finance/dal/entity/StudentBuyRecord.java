package com.ald.finance.dal.entity;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "Student_buy_record")
public class StudentBuyRecord {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private LocalDate startDate;
  private String courseIds;
  private Long teacherId;
  private Double packagePrice;
  private Integer status;
  private Long packageId;
  private Integer packageType;
  private Integer packageTimes;
  private Integer packageIndex;
  private Long studentIndex;
  private LocalDateTime createTime;
  private LocalDateTime updateTime;
  private Long userId;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public LocalDate getStartDate() {
    return startDate;
  }

  public void setStartDate(LocalDate startDate) {
    this.startDate = startDate;
  }

  public String getCourseIds() {
    return courseIds;
  }

  public void setCourseIds(String courseIds) {
    this.courseIds = courseIds;
  }

  public Long getTeacherId() {
    return teacherId;
  }

  public void setTeacherId(Long teacherId) {
    this.teacherId = teacherId;
  }

  public Double getPackagePrice() {
    return packagePrice;
  }

  public void setPackagePrice(Double packagePrice) {
    this.packagePrice = packagePrice;
  }

  public Integer getStatus() {
    return status;
  }

  public void setStatus(Integer status) {
    this.status = status;
  }

  public Long getPackageId() {
    return packageId;
  }

  public void setPackageId(Long packageId) {
    this.packageId = packageId;
  }

  public Integer getPackageType() {
    return packageType;
  }

  public void setPackageType(Integer packageType) {
    this.packageType = packageType;
  }

  public Integer getPackageTimes() {
    return packageTimes;
  }

  public void setPackageTimes(Integer packageTimes) {
    this.packageTimes = packageTimes;
  }

  public Integer getPackageIndex() {
    return packageIndex;
  }

  public void setPackageIndex(Integer packageIndex) {
    this.packageIndex = packageIndex;
  }

  public Long getStudentIndex() {
    return studentIndex;
  }

  public void setStudentIndex(Long studentIndex) {
    this.studentIndex = studentIndex;
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

  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }
}
