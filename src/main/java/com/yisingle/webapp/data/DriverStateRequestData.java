package com.yisingle.webapp.data;

import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;

/**
 * Created by jikun on 17/7/11.
 */
public class DriverStateRequestData {


    private int state;


    @NotEmpty(message = "phonenum不能为空")
    private String phonenum;//电话号码


    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getPhonenum() {
        return phonenum;
    }

    public void setPhonenum(String phonenum) {
        this.phonenum = phonenum;
    }
}
