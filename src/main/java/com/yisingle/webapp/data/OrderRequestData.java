package com.yisingle.webapp.data;

import org.hibernate.validator.constraints.NotEmpty;

import java.io.Serializable;
import java.lang.annotation.*;

/**
 * Created by jikun on 17/6/25.
 */
public class OrderRequestData implements Serializable {


    @NotEmpty(message = "phoneNum不能为空")
    private String phoneNum;

    @NotEmpty(message = "起点纬度不能空")
    private String startLatitude;


    @NotEmpty(message = "起点经度不能空")
    private String startLongitude;


    @NotEmpty(message = "起点名称不能为空")
    private String startPlaceName;


    @NotEmpty(message = "终点纬度不能空")
    private String endLatitude;

    @NotEmpty(message = "终点经度不能空")
    private String endLongitude;


    @NotEmpty(message = "终点名称不能为空")
    private String endPlaceName;


    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getStartLatitude() {
        return startLatitude;
    }

    public void setStartLatitude(String startLatitude) {
        this.startLatitude = startLatitude;
    }

    public String getStartLongitude() {
        return startLongitude;
    }

    public void setStartLongitude(String startLongitude) {
        this.startLongitude = startLongitude;
    }

    public String getEndLatitude() {
        return endLatitude;
    }

    public void setEndLatitude(String endLatitude) {
        this.endLatitude = endLatitude;
    }

    public String getEndLongitude() {
        return endLongitude;
    }

    public void setEndLongitude(String endLongitude) {
        this.endLongitude = endLongitude;
    }

    public String getStartPlaceName() {
        return startPlaceName;
    }

    public void setStartPlaceName(String startPlaceName) {
        this.startPlaceName = startPlaceName;
    }

    public String getEndPlaceName() {
        return endPlaceName;
    }

    public void setEndPlaceName(String endPlaceName) {
        this.endPlaceName = endPlaceName;
    }


}
