package com.yisingle.webapp.data;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * Created by jikun on 17/5/2.
 */
public class PositionRequestData {


    @NotEmpty(message = "latitude不能为空")
    private String latitude;//纬度
    @NotEmpty(message = "longitude不能为空")
    private String longitude;//经度
    @NotEmpty(message = "deviceId不能为空")
    private String deviceId;//设备id
    @NotEmpty(message = "phonenum不能为空")
    private String phonenum;//电话号码


    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getPhonenum() {
        return phonenum;
    }

    public void setPhonenum(String phonenum) {
        this.phonenum = phonenum;
    }
}
