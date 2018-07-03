package com.ald.finance.web.vm;

import com.ald.finance.dal.entity.User;

/**
 * Created by hongzp1987 on 2018/6/25.
 */
public class TeacherVm1 extends User {
    private Boolean add = true;

    public Boolean getAdd() {
        return add;
    }

    public void setAdd(Boolean add) {
        this.add = add;
    }
}
