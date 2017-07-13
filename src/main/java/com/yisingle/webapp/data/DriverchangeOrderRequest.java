package com.yisingle.webapp.data;

/**
 * Created by jikun on 17/7/12.
 */
public class DriverchangeOrderRequest {

    private int orderId;

    private int oderState;

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getOderState() {
        return oderState;
    }

    public void setOderState(int oderState) {
        this.oderState = oderState;
    }
}
