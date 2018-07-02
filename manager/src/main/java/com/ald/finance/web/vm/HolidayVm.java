package com.ald.finance.web.vm;

import javax.validation.constraints.NotNull;

/**
 * Created by liang3.zhang on 2018/5/3.
 */
public class HolidayVm {
    
    private String holidayName;

    @NotNull(message = "日期不能为空")
    private String holidayDay;
    
    public String getHolidayName() {
        return holidayName;
    }
    
    public void setHolidayName(String holidayName) {
        this.holidayName = holidayName;
    }
    
    public String getHolidayDay() {
        return holidayDay;
    }
    
    public void setHolidayDay(String holidayDay) {
        this.holidayDay = holidayDay;
    }
}
