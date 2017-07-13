package com.yisingle.webapp.data;

import java.util.List;

/**
 * Created by jikun on 17/7/11.
 */
public class DriverOrderRequest {


    private Integer[] orderState;

    private String driverId;


    public Integer[] getOrderState() {
        return orderState;
    }

    public void setOrderState(Integer[] orderState) {
        this.orderState = orderState;
    }

    public String getDriverId() {
        return driverId;
    }

    public void setDriverId(String driverId) {
        this.driverId = driverId;
    }
}
