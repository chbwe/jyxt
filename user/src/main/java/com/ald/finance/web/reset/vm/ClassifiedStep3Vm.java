package com.ald.finance.web.reset.vm;

/**
 * Created by liang3.zhang on 2018/5/8.
 */
public class ClassifiedStep3Vm {
    
    private Long teacherId;
    private String courseDate;
    private String reservationType;

    private Long packageId;
    
    public Long getTeacherId() {
        return teacherId;
    }
    
    public void setTeacherId(Long teacherId) {
        this.teacherId = teacherId;
    }
    
    public String getCourseDate() {
        return courseDate;
    }
    
    public void setCourseDate(String courseDate) {
        this.courseDate = courseDate;
    }
    
    public String getReservationType() {
        return reservationType;
    }
    
    public void setReservationType(String reservationType) {
        this.reservationType = reservationType;
    }

    public Long getPackageId() {
        return packageId;
    }

    public void setPackageId(Long packageId) {
        this.packageId = packageId;
    }
}
