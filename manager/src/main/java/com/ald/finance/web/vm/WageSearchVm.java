package com.ald.finance.web.vm;

/**
 * Created by liang3.zhang on 2018/5/3.
 */
public class WageSearchVm extends PageVm {
    
    private String date;
    
    private Integer status;
    
    public WageSearchVm teacherId(Long teacherId) {
        super.setTeacherId(teacherId);
        return this;
    }
    
    public Integer getStatus() {
        return status;
    }
    
    public void setStatus(Integer status) {
        this.status = status;
    }
    
    public String getDate() {
        return date;
    }
    
    public void setDate(String date) {
        this.date = date;
    }
}
