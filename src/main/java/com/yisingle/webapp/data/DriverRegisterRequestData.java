package com.yisingle.webapp.data;

import org.hibernate.validator.constraints.NotEmpty;


/**
 * Created by jikun on 17/6/26.
 */
public class DriverRegisterRequestData {

    @NotEmpty(message = "driverName不能为空")
    private String driverName;


    @NotEmpty(message = "phonenum不能为空")
    private String phonenum;//电话号码

    @NotEmpty(message = "password不能为空")
    private String password;//密码

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getPhonenum() {
        return phonenum;
    }

    public void setPhonenum(String phonenum) {
        this.phonenum = phonenum;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
