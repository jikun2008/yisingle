package com.yisingle.webapp.service;

import com.yisingle.webapp.data.OrderDetailData;
import com.yisingle.webapp.data.OrderRequestData;
import com.yisingle.webapp.data.ResponseData;
import com.yisingle.webapp.entity.OrderEntity;
import com.yisingle.webapp.entity.UserEntity;

/**
 * Created by jikun on 17/6/25.
 */
public interface OrderService {


    ResponseData<OrderDetailData> generateWaitOrder(OrderRequestData requestData, UserEntity userEntity);


    void changeOrderWaitNewStateToWatiOldState();


    ResponseData<OrderDetailData> changOrderState(int orderId, int orderState);


    ResponseData<OrderDetailData> acceptDriverOrder(int orderId);

    ResponseData<OrderDetailData>  finishDriverOrder(int orderId);


    ResponseData<OrderDetailData> findOrderByDriverIdAndState(Integer[] states, String driverId);

}
