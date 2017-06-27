package com.yisingle.webapp.data;

import org.hibernate.validator.constraints.NotEmpty;


/**
 * Created by jikun on 17/6/26.
 */
public class DriverLoginRequestData {

    @NotEmpty(message = "password不能为空")
    private String password;


    @NotEmpty(message = "phonenum不能为空")
    private String phonenum;//电话号码


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhonenum() {
        return phonenum;
    }

    public void setPhonenum(String phonenum) {
        this.phonenum = phonenum;
    }
}
