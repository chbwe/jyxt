package com.ald.finance.dal.entity;

import javax.persistence.*;

@Entity
@Table(name = "teacher_course_record_rel")
public class TeacherCourseRecordRel {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private Long teacherCourseRecordId;
    
    private Long studentCourseRecordId;
    
    private Integer status;
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Long getTeacherCourseRecordId() {
        return teacherCourseRecordId;
    }
    
    public void setTeacherCourseRecordId(Long teacherCourseRecordId) {
        this.teacherCourseRecordId = teacherCourseRecordId;
    }
    
    public Long getStudentCourseRecordId() {
        return studentCourseRecordId;
    }
    
    public void setStudentCourseRecordId(Long studentCourseRecordId) {
        this.studentCourseRecordId = studentCourseRecordId;
    }

  public Integer getStatus() {
    return status;
  }

  public void setStatus(Integer status) {
    this.status = status;
  }
}
