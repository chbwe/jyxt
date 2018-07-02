package com.ald.finance.web.reset.vm;

import java.util.List;

/**
 * Created by liang3.zhang on 2018/5/8.
 */
public class ClassifiedApplyVm extends ClassifiedStep3Vm {

    private Long packageId;

    private List<String> courseId;
    
    public List<String> getCourseId() {
        return courseId;
    }
    
    public void setCourseId(List<String> courseId) {
        this.courseId = courseId;
    }

    public Long getPackageId() {
        return packageId;
    }

    public void setPackageId(Long packageId) {
        this.packageId = packageId;
    }
}
