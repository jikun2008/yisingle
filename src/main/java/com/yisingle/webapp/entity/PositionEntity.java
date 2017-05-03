package com.yisingle.webapp.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by jikun on 17/5/2.
 */
@Entity
@Table(name = "t_position")
public class PositionEntity implements Serializable {

    @Id
    @GeneratedValue
    private int id;

    @Column(nullable = false)
    private String latitude;//纬度
    @Column(nullable = false)
    private String longitude;//经度
    @Column(nullable = false)
    private String deviceId;//设备id
    @Column(nullable = false)
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
