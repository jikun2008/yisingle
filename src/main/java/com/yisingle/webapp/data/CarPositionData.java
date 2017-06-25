package com.yisingle.webapp.data;

/**
 * Created by jikun on 17/6/14.
 */

public class CarPositionData {

    public CarPositionData(double latitude, double longitude,float bearing) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.bearing=bearing;
    }

    //纬度
    private double latitude;

    //经度
    private double longitude;


    //城市名称
    private String city;

    //城市code
    private String cityCode;

    //速度
    private float speed;

    //地址信息
    private String address;

    //获取方向角(单位：度） 默认值：0.0

    private float bearing;

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public float getBearing() {
        return bearing;
    }

    public void setBearing(float bearing) {
        this.bearing = bearing;
    }


}
