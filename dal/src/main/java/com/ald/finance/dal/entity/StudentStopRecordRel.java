package com.ald.finance.dal.entity;

import javax.persistence.*;

@Entity
@Table(name = "Student_stop_record_rel")
public class StudentStopRecordRel {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private Long stopRecordId;
  private Long courseRecordId;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getStopRecordId() {
    return stopRecordId;
  }

  public void setStopRecordId(Long stopRecordId) {
    this.stopRecordId = stopRecordId;
  }

  public Long getCourseRecordId() {
    return courseRecordId;
  }

  public void setCourseRecordId(Long courseRecordId) {
    this.courseRecordId = courseRecordId;
  }
}
