package com.yisingle.webapp.service;

import com.yisingle.webapp.data.HeartBeatData;

/**
 * Created by jikun on 17/7/28.
 */
public interface OrderCoordinateService {

    void saveOrderDriverLatLongToOrder(HeartBeatData data);



    void calculateCostMoney();
}
